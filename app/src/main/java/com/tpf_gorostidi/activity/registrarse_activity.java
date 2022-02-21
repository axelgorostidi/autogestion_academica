package com.tpf_gorostidi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.tpf_gorostidi.R;

import java.util.HashMap;
import java.util.Map;

public class registrarse_activity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etContraseña;
    private EditText etRepetirContraseña;
    private EditText etFechaNac;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_activity);

        etNombre = findViewById(R.id.etDatosNombre);
        etApellido = findViewById(R.id.etDatosApellido);
        etEmail = findViewById(R.id.etRegEmail);
        etContraseña = findViewById(R.id.etRegContrasena);
        etRepetirContraseña = findViewById(R.id.etRegRepetirContrasena);
        etFechaNac = findViewById(R.id.etDatosFechaNac);

    }

    public void onClickRegistarse(View v){
        if(controlarCampos()){
            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(etEmail.getText().toString(), etContraseña.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Map<String, String> data = new HashMap<>();
                                data.put("born", etFechaNac.getText().toString());
                                data.put("firstName", etNombre.getText().toString());
                                data.put("lastName", etApellido.getText().toString());
                                data.put("provider", ProviderType.BASIC.toString());

                                DocumentReference newUserRef = db.collection("Users").document(etEmail.getText().toString());
                                newUserRef
                                        .set(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(getApplicationContext(), inicio_activity.class);
                                                intent.putExtra("email", etEmail.getText().toString());
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
        if(!etNombre.getText().toString().isEmpty() && !etApellido.getText().toString().isEmpty() && !etEmail.getText().toString().isEmpty() &&
        !etContraseña.getText().toString().isEmpty() && !etRepetirContraseña.getText().toString().isEmpty() && !etFechaNac.getText().toString().isEmpty()){
            if(etContraseña.getText().toString().equals(etRepetirContraseña.getText().toString())){
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
                etFechaNac.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }
}