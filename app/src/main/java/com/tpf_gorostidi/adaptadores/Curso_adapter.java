package com.tpf_gorostidi.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpf_gorostidi.R;
import com.tpf_gorostidi.clases.Curso;

import java.util.ArrayList;

public class Curso_adapter extends BaseAdapter {

    private final ArrayList<Curso> cursos;
    private final ArrayList<Integer> suscriptos;
    private final Context context;
    public Curso_adapter(Context context, ArrayList<Curso> cursos, ArrayList<Integer> suscriptos) {
        this.cursos = cursos;
        this.context = context;
        this.suscriptos = suscriptos;
    }

    @Override
    public int getCount() {
        return cursos.size();
    }

    @Override
    public Object getItem(int i) {
        return cursos.get(i);
    }

    public Integer getSuscripto(int i){
        if(suscriptos.isEmpty()){
            return 0;
        }
        return suscriptos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Curso curso = (Curso) getItem(i);
        Integer suscripto = getSuscripto(i);

        view = LayoutInflater.from(context).inflate(R.layout.item_curso,null);
        TextView tvItemCursoNombre = view.findViewById(R.id.tvItemCursoNombre);
        TextView tvItemCursoFacultad = view.findViewById(R.id.tvItemCursoFacultad);
        TextView tvItemCursoSuscripto = view.findViewById(R.id.tvItemCursoSuscripto);
        tvItemCursoNombre.setText(curso.getNombre());
        tvItemCursoFacultad.setText(curso.getFacultad());
        if(suscripto == 1){
            tvItemCursoSuscripto.setText("SUSCRIPTO");
        }else{
            tvItemCursoSuscripto.setText("");
        }

        return view;
    }
}
