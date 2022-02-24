package com.tpf_gorostidi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tpf_gorostidi.R;
import com.tpf_gorostidi.adaptadores.Notificacion_adapter;
import com.tpf_gorostidi.clases.AdminSQLiteOpenHelper;
import com.tpf_gorostidi.clases.Notificacion;

import java.util.ArrayList;

public class notificaciones_activity extends AppCompatActivity {

    private String email = "";
    private ListView listNotificaciones;
    private Notificacion_adapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones_activity);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        email = prefs.getString("email", null);
        listNotificaciones = (ListView) findViewById(R.id.lvNotificaciones);
        listNotificaciones.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(notificaciones_activity.this);
                dialogo1.setTitle(getString(R.string.atencion));
                dialogo1.setMessage(getString(R.string.eliminarNotificacion));
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        eliminarNotificacion(position);
                    }
                });

                dialogo1.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();

                return false;
            }
        });

        //llenarListaNotificaciones();
    }
    public void onResume(){
        super.onResume();
        llenarListaNotificaciones();;
    }

    public void llenarListaNotificaciones(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();

        Cursor filas = baseDeDatos.rawQuery(("select titulo, descripcion, fecha, tema from notificaciones where usuario = '"+email.toString()+"'"), null);

        ArrayList<Notificacion> notificaciones = new ArrayList<>();

        for (int i = 0 ; i < filas.getCount(); i++) {
            filas.moveToPosition(i);
            String tmp_titulo = filas.getString(0);
            String tmp_descripcion = filas.getString(1);
            String tmp_fecha = filas.getString(2);

            Notificacion noti = new Notificacion(tmp_titulo,tmp_descripcion,tmp_fecha);
            notificaciones.add(noti);
        }
        baseDeDatos.close();

        adaptador = new Notificacion_adapter(getApplication(), notificaciones);
        listNotificaciones.setAdapter(adaptador);
    }

    public void eliminarNotificacion(int i){
        Notificacion notificacion = (Notificacion) (listNotificaciones.getItemAtPosition(i));
        String fecha = notificacion.getFecha();
        String descripcion = notificacion.getDescripcion();
        String titulo = notificacion.getTitulo();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase baseDeDatos = admin.getWritableDatabase();
        baseDeDatos.delete("notificaciones", " descripcion = '"+descripcion+"' and titulo = '"+titulo+"' and usuario = '"+email+"' and fecha = '"+fecha+"'", null);
        llenarListaNotificaciones();
    }

}