package com.fiapstore.dao.impl;

import com.fiapstore.dao.ConnectionManager;
import com.fiapstore.dao.ProdutoDao;
import com.fiapstore.exception.DBException;
import com.fiapstore.model.Categoria;
import com.fiapstore.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OracleProdutoDao implements ProdutoDao {

    private Connection conexao;

    @Override
    public void cadastrar(Produto produto) throws DBException {
        PreparedStatement stmt = null;
        conexao = ConnectionManager.getInstance().getConnection();
        String sql = "INSERT INTO PRODUTO (NOME, VALOR, QUANTIDADE, CATEGORIA) VALUES (?, ?, ?, ?)";

        try {
            stmt = conexao.prepareStatement(sql);
            valores(produto, stmt);
            //Prefiro utilizar date pelo próprio banco de dados.
            //stmt.setDate(4, Date.valueOf(produto.getDataFabricacao()));
            stmt.executeUpdate();
            System.out.println("Produto cadastro com sucesso!!!");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao cadastrar");
        } finally {
            try {
                stmt.close();
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void atualizar(Produto produto) throws DBException {
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE PRODUTO SET NOME = ?, VALOR = ?, QUANTIDADE = ?, CATEGORIA = ? WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            valores(produto, stmt);
            stmt.setInt(5, produto.getId());
            stmt.executeUpdate();
            System.out.println("Produto atualizado com sucesso");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao atualizar.");
        } finally {
            try {
                stmt.close();
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void valores(Produto produto, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, produto.getNome());
        stmt.setDouble(2, produto.getValor());
        stmt.setInt(3, produto.getQuantidade());
        stmt.setObject(4, produto.getCategoria().getId() != 0 ? produto.getCategoria().getId() : null, java.sql.Types.INTEGER);
    }

    @Override
    public void remover(int id) throws DBException {
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "DELETE FROM PRODUTO WHERE ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Produto removido com sucesso");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Erro ao remover.");
        } finally {
            try {
                stmt.close();
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Produto buscar(int id) {
        Produto produto = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM PRODUTO LEFT JOIN CATEGORIA ON (PRODUTO.CATEGORIA = CATEGORIA.ID) WHERE PRODUTO.ID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("ID");
                String nome = rs.getString("NOME");
                double valor = rs.getDouble("VALOR");
                int quantidade = rs.getInt("QUANTIDADE");
                LocalDate dataCriacao = rs.getDate("DATA_CRIACAO").toLocalDate();
                produto = new Produto(id, nome, valor, quantidade, dataCriacao);

                int categoriaId = rs.getInt(6);
                String categoriaNome = rs.getString(7);
                Categoria categoria = new Categoria(categoriaId, categoriaNome);
                produto.setCategoria(categoria);

                System.out.println("Produto encontrado!");
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
        return produto;
    }

    @Override
    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM PRODUTO LEFT JOIN CATEGORIA ON (PRODUTO.CATEGORIA = CATEGORIA.ID) ORDER BY 1";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            System.out.println("Lista de produtos");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nome = rs.getString("NOME");
                double valor = rs.getDouble("VALOR");
                int quantidade = rs.getInt("QUANTIDADE");
                LocalDate dataCriacao = rs.getDate("DATA_CRIACAO").toLocalDate();
                Produto produto = new Produto(id, nome, valor, quantidade, dataCriacao);

                int categoriaId = rs.getInt(7);
                String categoriaNome = rs.getString(8);
                Categoria categoria = new Categoria(categoriaId, categoriaNome);
                produto.setCategoria(categoria);

                lista.add(produto);
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
