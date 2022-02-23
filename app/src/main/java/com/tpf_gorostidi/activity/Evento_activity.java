package com.tpf_gorostidi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.tpf_gorostidi.R;

import org.w3c.dom.Text;

public class Evento_activity extends AppCompatActivity {

    private TextView tvEventoDetalleTitulo;
    private TextView tvEventoDetalleCuerpo;
    private TextView tvEventoDetalleFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_activity);
        tvEventoDetalleTitulo = findViewById(R.id.tvEventoDetalleTitulo);
        tvEventoDetalleFecha = findViewById(R.id.tvEventoDetalleFecha);
        tvEventoDetalleCuerpo = findViewById(R.id.tvEventoDetalleCuerpo);
        tvEventoDetalleCuerpo.setMovementMethod(new ScrollingMovementMethod());

        Bundle datos = getIntent().getExtras();
        tvEventoDetalleTitulo.setText(datos.getString("titulo"));
        tvEventoDetalleFecha.setText(datos.getString("fecha"));
        tvEventoDetalleCuerpo.setText(Html.fromHtml(datos.getString("cuerpo")));
    }

}