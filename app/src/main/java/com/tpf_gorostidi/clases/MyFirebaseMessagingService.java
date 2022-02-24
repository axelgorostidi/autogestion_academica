package com.tpf_gorostidi.clases;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tpf_gorostidi.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) { //agregamos la notificacion a la base de datos local
        super.onMessageReceived(remoteMessage);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);


        RemoteMessage.Notification noti = remoteMessage.getNotification();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();
        ContentValues regNotificacion = new ContentValues();
        String usuario = prefs.getString("email", null);;
        String titulo = noti.getTitle();
        String descripcion = noti.getBody();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String fecha = formatter.format(date);
        Log.e("Messaging: ", "EJECUTANDO");

        regNotificacion.put("usuario",usuario);
        regNotificacion.put("titulo",titulo);
        regNotificacion.put("descripcion", descripcion);
        regNotificacion.put("fecha", fecha);
        baseDeDatos.insert("notificaciones",null,regNotificacion);

        baseDeDatos.close();

        //ToastEntrante();
    }

//    public static void showToastMethod(Context context) {
//
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(context, R.string.notificacion, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void ToastEntrante(){

        try {
            Toast.makeText(getApplicationContext(), R.string.notificacion, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Resuelve el manejo de excepciones de llamar a Toast en el hilo secundario
            Looper.prepare();
            Toast.makeText(getApplicationContext(), R.string.notificacion, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}
