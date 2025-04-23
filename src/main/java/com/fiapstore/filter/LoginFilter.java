package com.fiapstore.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        String url = req.getRequestURI();

        boolean logado = session.getAttribute("user") != null;
        boolean acessoLogin = url.endsWith("home") || url.endsWith("login");
        boolean recursos = url.contains("resources");

        if (!logado && !acessoLogin && !recursos)  {
            req.setAttribute("erro", "Entre com o usuário e senha");
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        } else {
            filterChain.doFilter(req, resp);
        }
    }
}
