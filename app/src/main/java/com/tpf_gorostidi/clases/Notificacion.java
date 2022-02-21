package com.tpf_gorostidi.clases;

public class Notificacion {
    private final String titulo;
    private final String descripcion;
    private final String fecha;

    public Notificacion(String titulo, String descripcion, String fecha){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }
}
