package com.tpf_gorostidi.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpf_gorostidi.R;
import com.tpf_gorostidi.clases.EventoWebService;

import java.util.ArrayList;

public class Evento_adapter extends BaseAdapter {
    private final ArrayList<EventoWebService> eventos;
    private final Context context;

    public Evento_adapter(Context context, ArrayList<EventoWebService> notificaciones) {
        this.eventos = notificaciones;
        this.context = context;
    }

    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Object getItem(int i) {
        return eventos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        EventoWebService evento = (EventoWebService) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_evento,null);
        TextView tvEventoTitulo = view.findViewById(R.id.tvEventoTitulo);
        TextView tvEventoFecha = view.findViewById(R.id.tvEventoFecha);
        tvEventoTitulo.setText(evento.getTitulo());
        tvEventoFecha.setText(evento.getFecha());

        return view;
    }
}
