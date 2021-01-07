package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.service.HibernateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User newUser = new User(name, email, password);
        if (HibernateService.instOf().checkUser(newUser) == null) {
            HibernateService.instOf().addUser(newUser);
            resp.sendRedirect(req.getContextPath() + "/login.html");
        } else {
            doGet(req, resp);
        }
    }
}
