package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Predicate;

public interface Service {
    boolean addTask(Task task);
    List<Task> getTasks(Predicate<Task> condition);
    boolean setDone(Task task);
    User checkUser(User user);
    boolean addUser(User user);
}
