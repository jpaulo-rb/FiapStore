package com.fiapstore.teste;

import com.fiapstore.dao.ProdutoDao;
import com.fiapstore.exception.DBException;
import com.fiapstore.factory.DaoFactory;
import com.fiapstore.model.Produto;

import java.time.LocalDate;
import java.util.List;

public class ProdutoDaoTeste {
    public static void main(String[] args) {

        ProdutoDao dao = DaoFactory.getProdutoDao();

//        //Cadastrar um produto
//        Produto produto = new Produto("Mouse wireless", 77.49, 20);
//        try {
//            dao.cadastrar(produto);
//            System.out.println("Produto cadastrado.");
//        } catch (DBException e) {
//            e.printStackTrace();
//        }

//        //Buscar um produto pelo código e atualizar
//        produto = dao.buscar(1);
//        produto.setNome("Monitor LED 24P");
//        produto.setValor(891.99);
//        try {
//            dao.atualizar(produto);
//            System.out.println("Produto atualizado.");
//        } catch (DBException e) {
//            e.printStackTrace();
//        }
//
       //Listar os produtos
       List<Produto> lista = dao.listar();
       for (Produto item : lista) {
           System.out.println(item.getId() + " " + item.getNome() + " " + item.getValor() + " " + item.getQuantidade() +
                   " " + item.getDataCriacao() + " " + item.getCategoria().getCategoria());
       }

//        //Remover um produto
//        try {
//            dao.remover(1);
//            System.out.println("Produto removido.");
//        } catch (DBException e) {
//            e.printStackTrace();
//        }

    }
}