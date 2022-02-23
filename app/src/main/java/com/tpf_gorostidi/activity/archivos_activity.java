package com.tpf_gorostidi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.tpf_gorostidi.adaptadores.Archivo_adapter;
import com.tpf_gorostidi.clases.Archivo;
import com.tpf_gorostidi.clases.Comentario;

import java.util.ArrayList;

public class archivos_activity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String nombreCurso;
    private String email = "";
    private String usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos_activity);


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
        ArrayList<Archivo> archivos = new ArrayList<Archivo>();
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
                .collection("Files")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String usuario = document.get("author").toString();
                                String descripcion = document.get("description").toString();
                                String titulo = document.get("title").toString();
                                String link = document.get("link").toString();
                                if(!link.startsWith("http://") && !link.startsWith("https://"))
                                    link = "http://" + link;
                                archivos.add(new Archivo(usuario, titulo, descripcion, link));
                            }

                            Archivo_adapter adapter = new Archivo_adapter(getApplication(), archivos);

                            ListView listView = (ListView) findViewById(R.id.lvlArchivos);

                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(archivos.get(i).getLink()));
                                    startActivity(browserIntent);
                                }
                            });
                        } else {
                            errorFirebase();
                        }
                    }
                });
    }

    public void errorFirebase(){
        Toast.makeText(this, getString(R.string.problemaBD), Toast.LENGTH_SHORT).show();
    }

    public void onClickAgregar(View v){
        Intent intent = new Intent(getApplicationContext(), archivo_nuevo_activity.class);
        intent.putExtra("nombreCurso", nombreCurso);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }

}