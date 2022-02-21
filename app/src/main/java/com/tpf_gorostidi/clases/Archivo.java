package com.tpf_gorostidi.clases;

public class Archivo {
    private String usuario;
    private String titulo;
    private String descripcion;
    private String link;

    public Archivo(String usuario, String titulo, String descripcion, String link)
    {
        this.usuario = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.link = link;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLink() {
        return link;
    }
}
