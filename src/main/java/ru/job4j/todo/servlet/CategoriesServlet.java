package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.service.HibernateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoriesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Category> categories = HibernateService.instOf().getCategories();
        String jsonResp = gson.toJson(categories);
        resp.getWriter().write(jsonResp);
    }
}
