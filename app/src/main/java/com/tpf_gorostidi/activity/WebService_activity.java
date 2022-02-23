package com.tpf_gorostidi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;
import com.tpf_gorostidi.R;
import com.tpf_gorostidi.adaptadores.Evento_adapter;
import com.tpf_gorostidi.adaptadores.Notificacion_adapter;
import com.tpf_gorostidi.clases.EventoWebService;
import com.tpf_gorostidi.clases.Notificacion;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WebService_activity extends AppCompatActivity {

    private TextView tvIsConnected;
    private Evento_adapter adaptador;
    private ListView lvEventos;
    ArrayList<EventoWebService> eventos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service_activity);

        tvIsConnected = findViewById(R.id.tvIsConnected);
        lvEventos = findViewById(R.id.lvEventos);

        lvEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Evento_activity.class);
                intent.putExtra("titulo", eventos.get(i).getTitulo());
                intent.putExtra("cuerpo", eventos.get(i).getCuerpo());
                intent.putExtra("fecha", eventos.get(i).getFecha());
                startActivity(intent);
            }
        });

        if(isConnected()){
            tvIsConnected.setText("CONECTADO");
            tvIsConnected.setTextColor(Color.rgb(41, 214, 87));
        }else{
            tvIsConnected.setText("NO CONECTADO");
            tvIsConnected.setTextColor(Color.RED);
        }

        LeerWs();
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }else{
            return false;
        }
    }


    private void LeerWs() {
        lvEventos.setAdapter(null); //para limpiar el listView


        String url = "https://www.unl.edu.ar/noticias/contents/getnewshome/limit:15";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONArray auxtest = new JSONArray(jsonArray.getJSONObject(i).getString("News"));
                        JSONObject jsonObject = auxtest.getJSONObject(0);
                        String titulo = jsonObject.getString("title");
                        String cuerpo = jsonObject.getString("body");
                        String[] tmp_fecha = jsonObject.getString("modified").split("-");
                        String fecha = tmp_fecha[2].substring(0,2)+"-"+tmp_fecha[1]+"-"+tmp_fecha[0];

                        EventoWebService evento = new EventoWebService(titulo, cuerpo, fecha);
                        eventos.add(evento);
                    }

                    adaptador = new Evento_adapter(getApplication(), eventos);
                    lvEventos.setAdapter(adaptador);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postRequest);
    }

    public void onClickRecargar(View v){
        LeerWs();
    }
}