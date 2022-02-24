package com.tpf_gorostidi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.tpf_gorostidi.R;

enum ProviderType{
    BASIC, GOOGLE, FACEBOOK
}

public class inicio_activity extends AppCompatActivity {

    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null){
            String email = datos.getString("email");
            provider = datos.get("provider").toString();

            //Guardamos los datos de inicio de sesión
            SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
            SharedPreferences.Editor editorPrefs = prefs.edit();
            editorPrefs.putString("email", email.trim());
            editorPrefs.putString("provider", provider);
            editorPrefs.apply();
        }
    }

    public void onClickCerrarSesion(View v){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editorPrefs = prefs.edit();
        editorPrefs.clear();
        editorPrefs.apply();
        if(provider == "FACEBOOK"){
            LoginManager.getInstance().logOut();
        }

        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void onClickMisCursos(View v){
        Intent intent = new Intent(v.getContext(), MisCursos_activity.class);
        startActivity(intent);
    }
    public void onClickEventos(View v){
        Intent intent = new Intent(v.getContext(), WebService_activity.class);
        startActivity(intent);
    }
    public void onClickAulas(View v){
        Intent intent = new Intent(v.getContext(), Aulas_activity.class);
        startActivity(intent);
    }
    public void onClickNotificaciones(View v){
        Intent intent = new Intent(v.getContext(), notificaciones_activity.class);
        startActivity(intent);
    }
    public void onClickFacultad(View v){
        Uri gmmIntentUri = Uri.parse("geo:-31.640206, -60.672399");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    public void onClickMisDatos(View v){
        Intent intent = new Intent(v.getContext(), datos_activity.class);
        startActivity(intent);
    }
}