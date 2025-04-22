package com.fiapstore.controller;

import com.fiapstore.bo.EmailBo;
import com.fiapstore.dao.UsuarioDAO;
import com.fiapstore.exception.EmailException;
import com.fiapstore.factory.DaoFactory;
import com.fiapstore.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDao;
    private EmailBo bo;

    public LoginServlet() {
        usuarioDao = DaoFactory.getUsuarioDao();
        bo = new EmailBo();
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = new Usuario(email, senha);

        if (usuarioDao.validarUsuario(usuario)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", email);
            String mensagem = "Um login foi realizado na plataforma em " + LocalDate.now();
            request.getRequestDispatcher("home.jsp").forward(request, response);
            try {
                bo.enviarEmail(email, "Login realizado", mensagem);
            } catch (EmailException e) {
                e.printStackTrace();
            }
        } else {
            request.setAttribute("erro", "Usuário e/ou senha inválidos");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
