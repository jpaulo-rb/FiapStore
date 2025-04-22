package com.fiapstore.factory;

import com.fiapstore.dao.CategoriaDao;
import com.fiapstore.dao.ProdutoDao;
import com.fiapstore.dao.UsuarioDAO;
import com.fiapstore.dao.impl.OracleCategoriaDao;
import com.fiapstore.dao.impl.OracleProdutoDao;
import com.fiapstore.dao.impl.OracleUsuarioDao;

public class DaoFactory {

    public static ProdutoDao getProdutoDao() {
        return new OracleProdutoDao();
    }

    public static CategoriaDao getCategoriaDao() {
        return new OracleCategoriaDao();
    }

    public static UsuarioDAO getUsuarioDao () {
        return new OracleUsuarioDao();
    }
}