package com.tpf_gorostidi.clases;

public class Comentario {

    private final String usuario;
    private final String comentario;
    private final String tipoComentario;

    public Comentario(String usuario, String comentario, String tipoComentario){
        this.usuario = usuario;
        this.comentario = comentario;
        this.tipoComentario = tipoComentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public String getTipoComentario() {
        return tipoComentario;
    }
}
