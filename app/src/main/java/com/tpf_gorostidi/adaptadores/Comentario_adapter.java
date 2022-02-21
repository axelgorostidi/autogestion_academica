package com.tpf_gorostidi.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tpf_gorostidi.R;
import com.tpf_gorostidi.clases.Comentario;
import com.tpf_gorostidi.clases.Curso;

import java.util.ArrayList;

public class Comentario_adapter extends BaseAdapter {

    private final ArrayList<Comentario> comentarios;
    private final Context context;

    public Comentario_adapter(Context context, ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comentarios.size();
    }

    @Override
    public Object getItem(int i) {
        return comentarios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Comentario comentario = (Comentario) getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.item_comentario,null);
        TextView tvItemComentarioUsuario = view.findViewById(R.id.tvItemComentarioUsuario);
        TextView tvItemComentario = view.findViewById(R.id.tvItemComentario);
        ImageView imgItemComentarioTipo = view.findViewById(R.id.imgItemComentarioTipo);
        tvItemComentarioUsuario.setText(comentario.getUsuario().toString());
        tvItemComentario.setText(comentario.getComentario().toString());
        String tipoComent = comentario.getTipoComentario();
        if(tipoComent.equals("0")){
            imgItemComentarioTipo.setImageResource(R.drawable.comentinfo);
        }else if(tipoComent.equals("1")){
            imgItemComentarioTipo.setImageResource(R.drawable.comentpositivo);
        }else{
            imgItemComentarioTipo.setImageResource(R.drawable.comentnegativo);
        }

        return view;
    }
}
