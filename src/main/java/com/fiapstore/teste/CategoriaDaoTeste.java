package com.fiapstore.teste;

import com.fiapstore.dao.CategoriaDao;
import com.fiapstore.factory.DaoFactory;
import com.fiapstore.model.Categoria;

import java.util.List;

public class CategoriaDaoTeste {
    public static void main(String[] args) {
        CategoriaDao dao = DaoFactory.getCategoriaDao();

        List<Categoria> lista = dao.listar();
        for (Categoria categoria : lista) {
            System.out.println(categoria.getId() + " " + categoria.getCategoria());
        }

    }
}
