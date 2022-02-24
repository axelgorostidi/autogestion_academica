package com.tpf_gorostidi.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpf_gorostidi.R;
import com.tpf_gorostidi.clases.Comentario;
import com.tpf_gorostidi.clases.Notificacion;

import java.util.ArrayList;

public class Notificacion_adapter extends BaseAdapter {

    private final ArrayList<Notificacion> notificaciones;
    private final Context context;

    public Notificacion_adapter(Context context, ArrayList<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notificaciones.size();
    }

    @Override
    public Object getItem(int i) {
        return notificaciones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Notificacion notificacion = (Notificacion) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_notificacion,null);
        TextView tvNotiTitulo = view.findViewById(R.id.tvNotiTitulo);
        TextView tvNotiCuerpo = view.findViewById(R.id.tvNotiCuerpo);
        TextView tvNotiFecha = view.findViewById(R.id.tvNotiFecha);
        tvNotiTitulo.setText(notificacion.getTitulo());
        tvNotiCuerpo.setText(notificacion.getDescripcion());

        tvNotiFecha.setText(notificacion.getFecha());

        return view;
    }
}
