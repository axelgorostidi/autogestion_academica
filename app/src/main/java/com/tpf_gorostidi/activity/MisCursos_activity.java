package com.tpf_gorostidi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class MisCursos_activity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_cursos_activity);
        //crearListado();
    }

    public void crearListado(){
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        ArrayList<Integer> suscriptos = new ArrayList<Integer>();

        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        db.collection("Users")
                .document(email)
                .collection("MyCourses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String c_name = document.getId();
                                String c_faculty = document.get("faculty").toString();
                                double c_score = document.getDouble("score");
                                cursos.add(new Curso(c_name, c_faculty, c_score, 0));
                                suscriptos.add(0); //para que no ponga en label de suscripto en  mis cursos
                            }

                            Curso_adapter adapter = new Curso_adapter(getApplication(), cursos, suscriptos);

                            ListView listView = findViewById(R.id.lvlArchivos);

                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(getApplicationContext(), Curso_activity.class);
                                    intent.putExtra("nombre", cursos.get(i).getNombre());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Log.w("Error", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        crearListado();
    }

    public void onClickAgregar(View v){
        Intent intent = new Intent(getApplicationContext(), TodosCursos_activity.class);
        startActivity(intent);
    }

}