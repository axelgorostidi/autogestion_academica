package com.tpf_gorostidi.clases;

public class Curso {

    private final String nombre;
    private final double puntaje;
    private final int n_puntajes;
    private final String facultad;


    public Curso(String nombre, String facultad, double puntaje, int n_puntajes){
        this.nombre = nombre;
        this.facultad = facultad;
        this.puntaje = puntaje;
        this.n_puntajes = n_puntajes;
    }

    public String getNombre(){ return this.nombre; }
    public String getFacultad(){ return this.facultad; }
    public double getPuntaje(){ return this.puntaje; }
    public int getNPuntajes(){ return this.n_puntajes; }
}
