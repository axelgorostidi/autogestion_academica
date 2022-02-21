package com.tpf_gorostidi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tpf_gorostidi.R;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Curso_activity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String email;
    private String nombre;
    private String facultad;
    private Double puntaje;
    private int nPuntajes;
    private int nAlumnos;
    private boolean ratingBarOnCreate=true;
    private boolean estaSuscripto=false;

    private TextView tvCurso;
    private TextView tvFacultad;
    private TextView tvProfesores;
    private TextView tvPuntaje;

    public Button btnSuscribirDesuscribir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_activity);
        Bundle datos = getIntent().getExtras();
        nombre = datos.getString("nombre");

        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        email = prefs.getString("email", null);

        tvCurso = findViewById(R.id.tvCurso);
        tvCurso.setText(nombre);

        tvFacultad = findViewById(R.id.tvFacultad);
        tvProfesores = findViewById(R.id.tvProfesores);
        tvPuntaje = findViewById(R.id.tvPuntaje);
        btnSuscribirDesuscribir = findViewById(R.id.btnSuscribirDesuscribir);

        db.collection("Courses")
                .document(nombre)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        facultad = documentSnapshot.getString("faculty");
                        puntaje = documentSnapshot.getDouble("score");
                        nPuntajes = documentSnapshot.getLong("n_scores").intValue();
                        nAlumnos = documentSnapshot.getLong("n_students").intValue();

                        tvFacultad.setText(facultad);
                        DecimalFormat df = new DecimalFormat("0.00");
                        tvPuntaje.setText(df.format(puntaje));


                        List<String> profesores = (List<String>) documentSnapshot.get("professors");
                        String stringProfes = "";
                        if(profesores!=null) {
                            for (String prof : profesores) {
                                stringProfes = stringProfes + prof +", ";
                            }
                            tvProfesores.setText(stringProfes);
                        }
                    }
                });

        db.collection("Users")
                .document(email)
                .collection("MyCourses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document : task.getResult()){
                            if(document.getId().equals(nombre))
                            {
                                estaSuscripto = true;
                                btnSuscribirDesuscribir.setText("Desuscribir");
                                btnSuscribirDesuscribir.setBackground(getDrawable(R.drawable.presionarboton2));
                                @SuppressLint("WrongViewCast") AppCompatRatingBar ratingBar = findViewById(R.id.estrellasPuntaje);
                                ratingBar.setIsIndicator((boolean) document.get("is_rated"));
                                ratingBar.setRating(document.getDouble("score").floatValue());
                                ratingBarOnCreate = false;
                                break;
                            }
                        }
                        if(estaSuscripto==false) {
                            btnSuscribirDesuscribir.setText("Suscribir");
                            btnSuscribirDesuscribir.setBackground(getDrawable(R.drawable.presionarboton1));
                            @SuppressLint("WrongViewCast") AppCompatRatingBar ratingBar = findViewById(R.id.estrellasPuntaje);
                            ratingBar.setIsIndicator(true);
                        }
                    }
                });

        ((RatingBar) findViewById(R.id.estrellasPuntaje)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(!ratingBarOnCreate) {
                    ratingBar.setIsIndicator(true);
                    db.collection("Users")
                            .document(email)
                            .collection("MyCourses")
                            .document(nombre)
                            .update("score", v, "is_rated", true);

                    db.collection("Courses")
                            .document(nombre)
                            .update("score", (puntaje + v) / (nPuntajes + 1), "n_scores", nPuntajes + 1);

                    db.collection("Courses")
                            .document(nombre)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    puntaje = documentSnapshot.getDouble("score");
                                    nPuntajes = documentSnapshot.getLong("n_scores").intValue();

                                    tvFacultad.setText(facultad);
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    tvPuntaje.setText(df.format(puntaje));
                                }
                            });
                }
            }
        });
    }

    public void onClickSuscribirDesuscribir(View v){
        String nombreNormalizado = normalizarNombreCurso(nombre);
        if(estaSuscripto == false){
            Map<String, Object> data = new HashMap<>();
            data.put("faculty", facultad);
            data.put("score", 0);
            data.put("is_rated", false);

            db.collection("Courses")
                    .document(nombre)
                    .update("n_students", nAlumnos+1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            db.collection("Users")
                                    .document(email)
                                    .collection("MyCourses")
                                    .document(nombre)
                                    .set(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            finish();
                                        }
                                    });
                        }
                    });
            FirebaseMessaging.getInstance().subscribeToTopic(nombreNormalizado);
        }else{
            db.collection("Courses")
                    .document(nombre)
                    .update("n_students", nAlumnos-1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            db.collection("Users")
                                    .document(email)
                                    .collection("MyCourses")
                                    .document(nombre)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            finish();
                                        }
                                    });
                        }
                    });
            FirebaseMessaging.getInstance().unsubscribeFromTopic(nombreNormalizado);
        }

    }

    private String normalizarNombreCurso(String nombre){
        String nombreNorm = Normalizer.normalize(nombre, Normalizer.Form.NFD);
        nombreNorm = nombreNorm.replaceAll("[^\\p{ASCII}]", "");
        nombreNorm = nombreNorm.replace(" ","");
        return nombreNorm;
    }

    public void onClickComentarios(View v){
        Intent intent = new Intent(getApplicationContext(), comentarios_activity.class);
        intent.putExtra("cursoNombre", nombre);
        startActivity(intent);
    }
    public void onClickDocumentos(View v){
        Intent intent = new Intent(getApplicationContext(), archivos_activity.class);
        intent.putExtra("cursoNombre", nombre);
        startActivity(intent);
    }
}