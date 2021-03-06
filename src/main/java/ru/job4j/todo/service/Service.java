package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Predicate;

public interface Service {
    boolean addTask(Task task, String[] cIds);
    List<Task> getTasks(Predicate<Task> condition);
    boolean setDone(Task task);
    User checkUser(String email);
    void addUser(User user);
    List<Category> getCategories();
}
