package com.tpf_gorostidi.clases;

public class EventoWebService {
    private final String cuerpo;
    private final String titulo;
    private final String fecha;

    public EventoWebService(String titulo, String cuerpo, String fecha){
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.fecha = fecha;

    }

    public String getTitulo() {
        return titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public String getFecha() {
        return fecha;
    }

}
