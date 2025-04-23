package com.fiapstore.dao.impl;

import com.fiapstore.dao.CategoriaDao;
import com.fiapstore.dao.ConnectionManager;
import com.fiapstore.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleCategoriaDao implements CategoriaDao {
    
    private Connection conexao;

    @Override
    public List<Categoria> listar() {

        List<Categoria> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            stmt = conexao.prepareStatement("SELECT * FROM FS_CATEGORIA");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id =  rs.getInt("ID");
                String nome = rs.getString("CATEGORIA");
                Categoria categoria = new Categoria(id, nome);
                lista.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                rs.close();
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

}
