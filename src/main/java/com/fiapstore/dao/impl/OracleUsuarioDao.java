package com.fiapstore.dao.impl;

import com.fiapstore.dao.ConnectionManager;
import com.fiapstore.dao.UsuarioDAO;
import com.fiapstore.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleUsuarioDao implements UsuarioDAO {

    private Connection conexao;

    @Override
    public boolean validarUsuario(Usuario usuario) {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();

            String sql = "SELECT * FROM FS_USUARIO WHERE EMAIL = ? AND SENHA = ?";

            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            };

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
                rs.close();
                conexao.close();
            } catch (SQLException | RuntimeException e) {
                e.printStackTrace();
            }
        } return false;
    }

}
