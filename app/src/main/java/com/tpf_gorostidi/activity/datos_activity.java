package com.tpf_gorostidi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tpf_gorostidi.R;
import com.tpf_gorostidi.clases.Archivo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class datos_activity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etApellido;
    private EditText etFechaNac;
    private String email;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_activity);
        etNombre = findViewById(R.id.etDatosNombre);
        etApellido = findViewById(R.id.etDatosApellido);
        etFechaNac = findViewById(R.id.etDatosFechaNac);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        email = prefs.getString("email", null);
        preCargarDatos();
    }

    public void preCargarDatos(){
        try {
            db.collection("Users")
                    .document(email)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            Object nombre = documentSnapshot.get("firstName");
                            if(nombre != null){
                                etNombre.setText(nombre.toString());
                            }else{
                                etNombre.setText("");
                            }
                            Object apellido = documentSnapshot.get("lastName");
                            if(apellido != null){
                                etApellido.setText(apellido.toString());
                            }else{
                                etApellido.setText("");
                            }
                            Object fecha = documentSnapshot.get("born");
                            if(fecha != null){
                                etFechaNac.setText(fecha.toString());
                            }else{
                                etFechaNac.setText("");
                            }
                        }
                    });
        }catch (Exception e){}

    }

    public void onClickGrabar(View v){
        db.collection("Users")
                .document(email)
                .update("firstName", etNombre.getText().toString(), "lastName", etApellido.getText().toString(), "born", etFechaNac.getText().toString());

        finish();
//        Intent i = new Intent(this, inicio_activity.class);
//        startActivity(i);
    }

    public void onClickFecha(View v){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etFechaNac.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

}