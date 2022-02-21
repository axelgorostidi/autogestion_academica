package com.tpf_gorostidi.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tpf_gorostidi.R;
import com.tpf_gorostidi.clases.Archivo;

import java.util.ArrayList;

public class Archivo_adapter extends BaseAdapter {
    private final ArrayList<Archivo> archivos;
    private final Context context;

    public Archivo_adapter(Context context_, ArrayList<Archivo> archivos) {
        this.archivos = archivos;
        this.context = context_;
    }

    @Override
    public int getCount() {
        return archivos.size();
    }

    @Override
    public Object getItem(int i) {
        return archivos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Archivo archivo = (Archivo) getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.item_archivo,null);

        TextView tvItemArchivoUsuario = view.findViewById(R.id.tvItemArchivoUsuario);
        TextView tvItemArchivoTitulo = view.findViewById(R.id.tvItemArchivoTitulo);
        TextView tvItemArchivoDescripcion = view.findViewById(R.id.tvItemArchivoDescripcion);
        TextView tvItemArchivoLink = view.findViewById(R.id.tvItemArchivoLink);
        tvItemArchivoUsuario.setText(archivo.getUsuario());
        tvItemArchivoTitulo.setText(archivo.getTitulo());
        tvItemArchivoDescripcion.setText(archivo.getDescripcion());
        tvItemArchivoLink.setText(archivo.getLink());

        return view;
    }
}
