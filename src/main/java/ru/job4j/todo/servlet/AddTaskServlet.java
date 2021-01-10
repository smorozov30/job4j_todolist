package ru.job4j.todo.servlet;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.HibernateService;
import ru.job4j.todo.service.MemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class AddTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String description = req.getParameter("description");
        String[] cIds = req.getParameterValues("cIds[]");
        HibernateService.instOf().addTask(new Task(description, user), cIds);
    }
}
