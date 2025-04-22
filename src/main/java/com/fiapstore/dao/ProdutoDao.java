package com.fiapstore.dao;

import com.fiapstore.exception.DBException;
import com.fiapstore.model.Produto;

import java.util.List;

public interface ProdutoDao {

    //Public por padrão
    void cadastrar(Produto produto) throws DBException;
    void atualizar(Produto produto) throws DBException;
    void remover(int id) throws DBException;
    Produto buscar(int id);
    List<Produto> listar();

}
