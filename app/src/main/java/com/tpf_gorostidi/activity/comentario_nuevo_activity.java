package com.tpf_gorostidi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tpf_gorostidi.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class comentario_nuevo_activity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView tvComentarioCurso;
    private Spinner spinnerTipos;
    private EditText etComentarioCuerpo;
    private String usuario;
    private String nombreCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario_nuevo_activity);

        tvComentarioCurso = findViewById(R.id.tvComentarioCurso);
        etComentarioCuerpo = findViewById(R.id.etComentarioCuerpo);
        spinnerTipos = findViewById(R.id.spinnerTipos);

        List<String> tipos = new ArrayList<String>();
        tipos.add("Informativo"); tipos.add("Positivo"); tipos.add("Negativo");
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipos.setAdapter(adapterSpinner);

        Bundle extras = getIntent().getExtras();
        nombreCurso = extras.getString("nombreCurso");
        usuario = extras.getString("usuario");
        tvComentarioCurso.setText(nombreCurso);

    }

    public void onClickAgregar(View v){
        if(!controlarCampos()){
            Toast.makeText(this, getString(R.string.ingreseDatos), Toast.LENGTH_SHORT).show();
            return;
        }

        String tipo = "0";
        if(spinnerTipos.getSelectedItem().toString() == "Informativo"){
            tipo = "0";
        }else if (spinnerTipos.getSelectedItem().toString() == "Positivo"){
            tipo = "1";
        }else{
            tipo = "2";
        }

        Map<String, String> data = new HashMap<>();
        data.put("author", usuario);
        data.put("body", etComentarioCuerpo.getText().toString());
        data.put("type", tipo);

        db.collection("Courses")
                .document(nombreCurso)
                .collection("Reviews")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        finish();
                    }
                });
    }

    public boolean controlarCampos(){

        if(etComentarioCuerpo.getText().toString().isEmpty() || spinnerTipos.getSelectedItem().toString().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

}