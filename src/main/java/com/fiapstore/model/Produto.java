package com.fiapstore.model;

import java.time.LocalDate;

public class Produto {

    private int id;
    private String nome;
    private double valor;
    private int quantidade;
    private LocalDate dataCriacao;
    private Categoria categoria;

    public Produto() {
    }

    public Produto(int id, String nome, double valor, int quantidade, LocalDate dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.dataCriacao = dataCriacao;
    }

    public Produto(String nome, double valor, int quantidade) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int codigo) {
        this.id = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}