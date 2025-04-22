package com.fiapstore.model;

public class Categoria {

    private int id;
    private String categoria;

    public Categoria() {
        super();
    }

    public Categoria(int id, String categoria) {
        super();
        this.id = id;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategorias(String categoria) {
        this.categoria = categoria;
    }

}
