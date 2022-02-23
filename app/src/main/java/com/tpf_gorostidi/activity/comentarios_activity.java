package com.tpf_gorostidi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tpf_gorostidi.R;
import com.tpf_gorostidi.adaptadores.Comentario_adapter;
import com.tpf_gorostidi.clases.Comentario;

import java.util.ArrayList;

public class comentarios_activity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String nombreCurso;
    private String email = "";
    private String usuario = "";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios_activity);

        listView = findViewById(R.id.lvlArchivos);
        Bundle extras = getIntent().getExtras();
        nombreCurso = extras.getString("cursoNombre");
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        email = prefs.getString("email", null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        crearLista();
    }

    public void crearLista(){
        ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
        db.collection("Users")
                .document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        usuario = documentSnapshot.get("firstName") + " - " + email;
                    }
                });

        db.collection("Courses")
                .document(nombreCurso)
                .collection("Reviews")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String autor = document.get("author").toString();
                                String cuerpo = document.get("body").toString();
                                Object tipoComentario = document.get("type");
                                String tipoComentarioStr = "0";
                                if(tipoComentario != null){
                                    tipoComentarioStr = tipoComentario.toString();
                                }
                                comentarios.add(new Comentario(autor, cuerpo,tipoComentarioStr));
                            }

                            Comentario_adapter adapter = new Comentario_adapter(getApplication(), comentarios);

                            listView.setAdapter(adapter);
                        } else {
                            errorFirebase();
                        }
                    }
                });
    }

    public void onClickAgregar(View v){
        Intent intent = new Intent(getApplicationContext(), comentario_nuevo_activity.class);
        intent.putExtra("nombreCurso", nombreCurso);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }

    public void errorFirebase(){
        Toast.makeText(this, getString(R.string.problemaBD), Toast.LENGTH_SHORT).show();
    }

}