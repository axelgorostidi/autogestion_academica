package com.tpf_gorostidi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.Touch;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ortiz.touchview.TouchImageView;
import com.tpf_gorostidi.R;

import java.util.ArrayList;

public class Aulas_activity extends AppCompatActivity {

    private Spinner spinnerUbiTipo;
    private Spinner spinnerUbiAula;
    private ImageView imageViewUbi;
    private TouchImageView imageViewUbiZoom;
    private TextView tvPiso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas_activity);

        spinnerUbiTipo = findViewById(R.id.spinnerUbiTipo);
        spinnerUbiAula = findViewById(R.id.spinnerUbiAula);
//        imageViewUbi = findViewById(R.id.imageViewUbi);
        imageViewUbiZoom = findViewById(R.id.imageViewUbiZoom);
        tvPiso = findViewById(R.id.tvPiso);

        ArrayList<String> tipos = new ArrayList<>();
        tipos.add("Aulas");
        tipos.add("Oficinas y Otros");

        ArrayAdapter<String> adapterTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
        spinnerUbiTipo.setAdapter(adapterTipos);

        ArrayList<String> aulas = new ArrayList<>();
        ArrayList<String> oficinas = new ArrayList<>();

        spinnerUbiTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinnerUbiAula.setAdapter(null);
                if(spinnerUbiTipo.getSelectedItem().toString() == "Aulas"){
                    aulas.clear();
                    aulas.add("Aula 1"); //
                    aulas.add("Aula 2"); //
                    aulas.add("Aula 3"); //
                    aulas.add("Aula 4"); //
                    aulas.add("Aula 5"); //
                    aulas.add("Aula 8"); //
                    aulas.add("Aula 9");
                    aulas.add("Aula 10");
                    aulas.add("Aula Magna"); //
                    aulas.add("Laboratorio 1");
                    aulas.add("Laboratorio 2");
                    aulas.add("Laboratorio 3");
                    aulas.add("Laboratorio 4");
                    aulas.add("Laboratorio de Electr??nica");
                    aulas.add("Laboratorio de Qu??mica");
                    aulas.add("Aula FICH-CIMNE");
                    aulas.add("Aula de Dibujo");
                    aulas.add("Gabinete Emprendedores");

                    ArrayAdapter<String> adapterAulas = new ArrayAdapter<String>(parentView.getContext(), android.R.layout.simple_spinner_dropdown_item, aulas);
                    spinnerUbiAula.setAdapter(adapterAulas);
                }else{
                    oficinas.clear();
                    oficinas.add("Fotocopiadora"); //
                    oficinas.add("Ba??os Planta Baja"); //
                    oficinas.add("Ba??os Primer Piso");
                    oficinas.add("Ba??os Segundo Piso");
                    oficinas.add("Centro de Estudiantes");
                    oficinas.add("Asuntos Estudiantiles");
                    oficinas.add("Asesor??a Pedag??gica");
                    oficinas.add("Secretar??a Acad??mica");
                    oficinas.add("Mesa de Entradas");
                    oficinas.add("Alumnado");
                    oficinas.add("Bedel??a");

                    ArrayAdapter<String> adapterOficinas = new ArrayAdapter<String>(parentView.getContext(), android.R.layout.simple_spinner_dropdown_item, oficinas);
                    spinnerUbiAula.setAdapter(adapterOficinas);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                spinnerUbiAula.setAdapter(null);
            }
        });

        spinnerUbiAula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selec = spinnerUbiAula.getSelectedItem().toString();
                if(selec == "Aula 1" || selec == "Aula 2" || selec == "Aula 3" || selec == "Aula 4" || selec == "Aula 5" || selec == "Aula 8" ||
                        selec == "Aula Magna" || selec == "Ba??os Planta Baja" || selec == "Fotocopiadora"){
                    tvPiso.setText("Planta Baja");
                }
                if(selec == "Laboratorio 1" || selec == "Laboratorio 2" || selec == "Laboratorio 3" || selec == "Laboratorio 4" || selec == "Aula FICH-CIMNE" || selec == "Laboratorio de Electr??nica" ||
                        selec == "Centro de Estudiantes" || selec == "Asuntos Estudiantiles" || selec == "Asesor??a Pedag??gica" || selec == "Secretar??a Acad??mica" || selec == "Mesa de Entradas"
                        || selec == "Alumnado" || selec == "Bedel??a" || selec == "Ba??os Primer Piso"){
                    tvPiso.setText("Primer Piso");
                }
                if(selec == "Aula de Dibujo" || selec == "Laboratorio de Qu??mica" || selec == "Ba??os Segundo Piso"){
                    tvPiso.setText("Segundo Piso");
                }
                if(selec == "Aula 10" || selec == "Aula 9" || selec == "Gabinete Emprendedores"){
                    tvPiso.setText("Tercer Piso");
                }

//                switch(selec){
//                    case "Aula 1": imageViewUbi.setImageResource(R.drawable.a1);
//                    break;
//                    case "Aula 2": imageViewUbi.setImageResource(R.drawable.a2);
//                        break;
//                    case "Aula 3": imageViewUbi.setImageResource(R.drawable.a3);
//                        break;
//                    case "Aula 4": imageViewUbi.setImageResource(R.drawable.a4);
//                        break;
//                    case "Aula 5": imageViewUbi.setImageResource(R.drawable.a5);
//                        break;
//                    case "Aula 8": imageViewUbi.setImageResource(R.drawable.a8);
//                        break;
//                    case "Aula Magna": imageViewUbi.setImageResource(R.drawable.amagna);
//                        break;
//                    case "Ba??os Planta Baja": imageViewUbi.setImageResource(R.drawable.banospn);
//                        break;
//                    case "Fotocopiadora": imageViewUbi.setImageResource(R.drawable.fotocopiadora);
//                        break;
//                    case "Laboratorio 1": imageViewUbi.setImageResource(R.drawable.lab1);
//                        break;
//                    case "Laboratorio 2": imageViewUbi.setImageResource(R.drawable.lab2);
//                        break;
//                    case "Laboratorio 3": imageViewUbi.setImageResource(R.drawable.lab3);
//                        break;
//                    case "Laboratorio 4": imageViewUbi.setImageResource(R.drawable.lab4);
//                        break;
//                    case "Aula FICH-CIMNE": imageViewUbi.setImageResource(R.drawable.acimne);
//                        break;
//                    case "Laboratorio de Electr??nica": imageViewUbi.setImageResource(R.drawable.labelectronica);
//                        break;
//                    case "Centro de Estudiantes": imageViewUbi.setImageResource(R.drawable.centroestudiantes);
//                        break;
//                    case "Asuntos Estudiantiles": imageViewUbi.setImageResource(R.drawable.aestudiantiles);
//                        break;
//                    case "Asesor??a Pedag??gica": imageViewUbi.setImageResource(R.drawable.apedagogica);
//                        break;
//                    case "Secretar??a Acad??mica": imageViewUbi.setImageResource(R.drawable.sacademica);
//                        break;
//                    case "Mesa de Entradas": imageViewUbi.setImageResource(R.drawable.mesaentradas);
//                        break;
//                    case "Alumnado": imageViewUbi.setImageResource(R.drawable.alumnado);
//                        break;
//                    case "Bedel??a": imageViewUbi.setImageResource(R.drawable.bedelia);
//                        break;
//                    case "Ba??os Primer Piso": imageViewUbi.setImageResource(R.drawable.banospp);
//                        break;
//                    case "Aula de Dibujo": imageViewUbi.setImageResource(R.drawable.auladibujo);
//                        break;
//                    case "Laboratorio de Qu??mica": imageViewUbi.setImageResource(R.drawable.laboratorioquimica);
//                        break;
//                    case "Ba??os Segundo Piso": imageViewUbi.setImageResource(R.drawable.banossp);
//                        break;
//                    case "Aula 9": imageViewUbi.setImageResource(R.drawable.a9);
//                        break;
//                    case "Gabinete Emprendedores": imageViewUbi.setImageResource(R.drawable.gemprendedores);
//                        break;
//                }
                switch(selec){
                    case "Aula 1": imageViewUbiZoom.setImageResource(R.drawable.a1);
                        break;
                    case "Aula 2": imageViewUbiZoom.setImageResource(R.drawable.a2);
                        break;
                    case "Aula 3": imageViewUbiZoom.setImageResource(R.drawable.a3);
                        break;
                    case "Aula 4": imageViewUbiZoom.setImageResource(R.drawable.a4);
                        break;
                    case "Aula 5": imageViewUbiZoom.setImageResource(R.drawable.a5);
                        break;
                    case "Aula 8": imageViewUbiZoom.setImageResource(R.drawable.a8);
                        break;
                    case "Aula Magna": imageViewUbiZoom.setImageResource(R.drawable.amagna);
                        break;
                    case "Ba??os Planta Baja": imageViewUbiZoom.setImageResource(R.drawable.banospn);
                        break;
                    case "Fotocopiadora": imageViewUbiZoom.setImageResource(R.drawable.fotocopiadora);
                        break;
                    case "Laboratorio 1": imageViewUbiZoom.setImageResource(R.drawable.lab1);
                        break;
                    case "Laboratorio 2": imageViewUbiZoom.setImageResource(R.drawable.lab2);
                        break;
                    case "Laboratorio 3": imageViewUbiZoom.setImageResource(R.drawable.lab3);
                        break;
                    case "Laboratorio 4": imageViewUbiZoom.setImageResource(R.drawable.lab4);
                        break;
                    case "Aula FICH-CIMNE": imageViewUbiZoom.setImageResource(R.drawable.acimne);
                        break;
                    case "Laboratorio de Electr??nica": imageViewUbiZoom.setImageResource(R.drawable.labelectronica);
                        break;
                    case "Centro de Estudiantes": imageViewUbiZoom.setImageResource(R.drawable.centroestudiantes);
                        break;
                    case "Asuntos Estudiantiles": imageViewUbiZoom.setImageResource(R.drawable.aestudiantiles);
                        break;
                    case "Asesor??a Pedag??gica": imageViewUbiZoom.setImageResource(R.drawable.apedagogica);
                        break;
                    case "Secretar??a Acad??mica": imageViewUbiZoom.setImageResource(R.drawable.sacademica);
                        break;
                    case "Mesa de Entradas": imageViewUbiZoom.setImageResource(R.drawable.mesaentradas);
                        break;
                    case "Alumnado": imageViewUbiZoom.setImageResource(R.drawable.alumnado);
                        break;
                    case "Bedel??a": imageViewUbiZoom.setImageResource(R.drawable.bedelia);
                        break;
                    case "Ba??os Primer Piso": imageViewUbiZoom.setImageResource(R.drawable.banospp);
                        break;
                    case "Aula de Dibujo": imageViewUbiZoom.setImageResource(R.drawable.auladibujo);
                        break;
                    case "Laboratorio de Qu??mica": imageViewUbiZoom.setImageResource(R.drawable.laboratorioquimica);
                        break;
                    case "Ba??os Segundo Piso": imageViewUbiZoom.setImageResource(R.drawable.banossp);
                        break;
                    case "Aula 9": imageViewUbiZoom.setImageResource(R.drawable.a9);
                        break;
                    case "Gabinete Emprendedores": imageViewUbiZoom.setImageResource(R.drawable.gemprendedores);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

}