package com.tpf_gorostidi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tpf_gorostidi.R;
import com.tpf_gorostidi.adaptadores.Curso_adapter;
import com.tpf_gorostidi.clases.Curso;

import java.util.ArrayList;

public class TodosCursos_activity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_cursos_activity);
        //crearListado();
    }

    @Override
    protected void onResume() {
        super.onResume();
        crearListado();
    }

    public void crearListado(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        ArrayList<Curso> cursos = new ArrayList<Curso>();
        ArrayList<String> cursosSuscripto = new ArrayList<String>();
        ArrayList<Integer> suscriptos = new ArrayList<Integer>();

        db.collection("Users")
                .document(email)
                .collection("MyCourses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                cursosSuscripto.add(document.getId());
                            }
                        }
                    }
                });
        int a = 0;
        for (int i = 0; i < 5000; i++) {
            a = 1+i;
        }
        db.collection("Courses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String nombre = document.getId();
                                String facultad = document.get("faculty").toString();
                                double puntaje = document.getDouble("score");
                                int nPuntajes = document.getLong("n_scores").intValue();
                                cursos.add(new Curso(nombre, facultad, puntaje, nPuntajes));
                                if(cursosSuscripto.indexOf(document.getId())==-1) {
                                    suscriptos.add(0);
                                }else{
                                    suscriptos.add(1);
                                }

                               /* if(cursosSuscripto.indexOf(document.getId())==-1) {
                                    String nombre = document.getId();
                                    String facultad = document.get("faculty").toString();
                                    double puntaje = document.getDouble("score");
                                    int nPuntajes = document.getLong("n_scores").intValue();
                                    cursos.add(new Curso(nombre, facultad, puntaje, nPuntajes));
                                    suscriptos.add(0);
                                }else{
                                    suscriptos.add(1);
                                }*/

                            }
                        }

                        Curso_adapter adapter = new Curso_adapter(getApplication(), cursos, suscriptos);

                        ListView listView = (ListView) findViewById(R.id.lvlArchivos);

                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(getApplicationContext(), Curso_activity.class);
                                intent.putExtra("nombre", cursos.get(i).getNombre());
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

}