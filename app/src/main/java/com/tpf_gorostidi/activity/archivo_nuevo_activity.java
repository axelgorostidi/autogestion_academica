package com.tpf_gorostidi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tpf_gorostidi.R;

import java.util.HashMap;
import java.util.Map;

public class archivo_nuevo_activity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView tvNuevoArchivoCurso;
    private EditText etNuevoArchivoTitulo;
    private EditText etNuevoArchivoDescripcion;
    private EditText etNuevoArchivoLink;
    private String nombreCurso;
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivo_nuevo_activity);

        tvNuevoArchivoCurso = findViewById(R.id.tvNuevoArchivoCurso);
        etNuevoArchivoTitulo = findViewById(R.id.etNuevoArchivoTitulo);
        etNuevoArchivoDescripcion = findViewById(R.id.etNuevoArchivoDescripcion);
        etNuevoArchivoLink = findViewById(R.id.etNuevoArchivoLink);

        Bundle extras = getIntent().getExtras();
        nombreCurso = extras.getString("nombreCurso");
        usuario = extras.getString("usuario");
        String user = extras.getString("usuario");
        tvNuevoArchivoCurso.setText(nombreCurso);
        //Bundle[{usuario=axel - axelgorostidi@gmail.com, nombreCurso=Inteligencia computacional}]
    }

    public void onClickAgregar(View v){
        String titulo = etNuevoArchivoTitulo.getText().toString();
        String descripcion = etNuevoArchivoDescripcion.getText().toString();
        String link = etNuevoArchivoLink.getText().toString();

        if (controlarCampos()) {
            Map<String, String> data = new HashMap<>();
            data.put("author", usuario);
            data.put("title", titulo);
            data.put("description", descripcion);
            data.put("link", link);

            db.collection("Courses")
                    .document(nombreCurso)
                    .collection("Files")
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            finish();
                        }
                    });
        } else {
            Toast.makeText(this, getString(R.string.ingreseDatos), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean controlarCampos(){
        String titulo = etNuevoArchivoTitulo.getText().toString();
        String descripcion = etNuevoArchivoDescripcion.getText().toString();
        String link = etNuevoArchivoLink.getText().toString();

        if(titulo.isEmpty() || descripcion.isEmpty() || link.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}