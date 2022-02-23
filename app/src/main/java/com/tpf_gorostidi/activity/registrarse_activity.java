package com.tpf_gorostidi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.tpf_gorostidi.R;

import java.util.HashMap;
import java.util.Map;

public class registrarse_activity extends AppCompatActivity {

    private EditText etRegEmail;
    private EditText etRegNombre;
    private EditText etRegApellido;
    private EditText etRegContrasena;
    private EditText etRegRepetirContrasena;
    private EditText etRegFechaNac;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_activity);

        etRegNombre = findViewById(R.id.etDatosNombre);
        etRegApellido = findViewById(R.id.etDatosApellido);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegContrasena = findViewById(R.id.etRegContrasena);
        etRegRepetirContrasena = findViewById(R.id.etRegRepetirContrasena);
        etRegFechaNac = findViewById(R.id.etDatosFechaNac);

    }

    public void onClickRegistarse(View v){
        if(controlarCampos()){
            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(etRegEmail.getText().toString().trim(), etRegContrasena.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Map<String, String> data = new HashMap<>();
                                data.put("born", etRegFechaNac.getText().toString());
                                data.put("firstName", etRegNombre.getText().toString());
                                data.put("lastName", etRegApellido.getText().toString());
                                data.put("provider", ProviderType.BASIC.toString());

                                DocumentReference newUserRef = db.collection("Users").document(etRegEmail.getText().toString().trim());
                                newUserRef
                                        .set(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(getApplicationContext(), inicio_activity.class);
                                                intent.putExtra("email", etRegEmail.getText().toString());
                                                intent.putExtra("provider", ProviderType.BASIC.toString());
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                errorFirebase();
                                            }
                                        });

                            }else{
                                Log.w("ERROR", "signInWithEmail:failure", task.getException());
                                errorRegistro();
                            }
                        }
                    });
        }
    }

    public void errorRegistro(){
        Toast.makeText(this, getString(R.string.problemaRegistro), Toast.LENGTH_SHORT).show();
    }
    public void errorFirebase(){
        Toast.makeText(this, getString(R.string.problemaBD), Toast.LENGTH_SHORT).show();
    }

    public boolean controlarCampos(){
        if(!etRegNombre.getText().toString().isEmpty() && !etRegApellido.getText().toString().isEmpty() && !etRegEmail.getText().toString().isEmpty() &&
        !etRegContrasena.getText().toString().isEmpty() && !etRegRepetirContrasena.getText().toString().isEmpty() && !etRegFechaNac.getText().toString().isEmpty()){
            if(etRegContrasena.getText().toString().equals(etRegRepetirContrasena.getText().toString())){
                return true;
            }else{
                Toast.makeText(this, getString(R.string.contraseñasAlerta), Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(this, getString(R.string.ingreseDatos), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void onClickFecha(View v){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etRegFechaNac.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

}