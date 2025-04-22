package com.fiapstore.controller;

import com.fiapstore.dao.CategoriaDao;
import com.fiapstore.dao.ProdutoDao;
import com.fiapstore.exception.DBException;
import com.fiapstore.factory.DaoFactory;
import com.fiapstore.model.Categoria;
import com.fiapstore.model.Produto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    private ProdutoDao dao;
    private CategoriaDao categoriaDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        dao = DaoFactory.getProdutoDao();
        categoriaDao = DaoFactory.getCategoriaDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String acao = req.getParameter("acao");

        switch (acao) {
            case "cadastrar":
                cadastrar(req, resp);
                break;
            case "editar":
                editar(req, resp);
                break;
            case "excluir":
                excluir(req, resp);
                break;
            case "login":
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String acao = req.getParameter("acao");

        switch (acao) {
            case "listar":
                listar(req, resp);
                break;

            case "abrir-form-edicao":
                abrirForm(req, resp);
                break;

            case "abrir-form-cadastro":
                abrirFormCadastro(req, resp);
                break;
        }
    }

    private void cadastrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        double valor = Double.valueOf(req.getParameter("valor"));
        int quantidade = Integer.valueOf(req.getParameter("quantidade"));
        LocalDate data = LocalDate.parse(req.getParameter("criacao"));
        Produto produto = new Produto(0, nome, valor, quantidade, data);

        int id_categoria = Integer.valueOf(req.getParameter("categoria"));

        Categoria categoria = new Categoria();
        categoria.setId(id_categoria);
        produto.setCategoria(categoria);

        try {
            dao.cadastrar(produto);
            req.setAttribute("mensagem", "Produto cadastrado com sucesso!");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao cadastrar produto");
        }
        req.getRequestDispatcher("cadastro-produto.jsp").forward(req, resp);
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String nome = req.getParameter("nome");
            int quantidade = Integer.parseInt(req.getParameter("quantidade"));
            double valor = Double.parseDouble(req.getParameter("valor"));
            LocalDate dataCriacao = LocalDate.parse(req.getParameter("criacao"));
            Produto produto = new Produto(id, nome, valor, quantidade, dataCriacao);

            int id_categoria = Integer.parseInt(req.getParameter("categoria"));
            Categoria categoria = new Categoria();
            categoria.setId(id_categoria);
            produto.setCategoria(categoria);

            dao.atualizar(produto);
            req.setAttribute("mensagem", "Produto editado com sucesso!");
            listar(req, resp);

        } catch (DBException db) {
            db.printStackTrace();
            //StringWriter sw = new StringWriter();
            //db.printStackTrace(new PrintWriter(sw));
            req.setAttribute("erro", "Erro ao editar produto"); //+ sw);
            abrirForm(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao editar produto, favor valide-os");
            abrirForm(req, resp);

        }
    }

    public void excluir(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("idExcluir"));
        String nome = req.getParameter("nomeExcluir");
        try {
            dao.remover(id);
            req.setAttribute("mensagem", "Produto removido com sucesso!");
        } catch (DBException e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao remover produto: '" + nome + "'");
        }

        listar(req, resp);
    }

    //DoPost /\
    //DoGet \/


    private void abrirForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Produto produto = dao.buscar(id);
        req.setAttribute("produto", produto);
        listarCategorias(req);
        req.getRequestDispatcher("editar-produto.jsp").forward(req, resp);
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Produto> lista = dao.listar();
        req.setAttribute("produtos", lista);
        req.getRequestDispatcher("lista-produto.jsp").forward(req, resp);
    }

    private void abrirFormCadastro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        listarCategorias(req);
        req.getRequestDispatcher("cadastro-produto.jsp").forward(req, resp);
    }

    private void listarCategorias(HttpServletRequest req) {
        List<Categoria> lista = categoriaDao.listar();
        req.setAttribute("categorias", lista);
    }
}
