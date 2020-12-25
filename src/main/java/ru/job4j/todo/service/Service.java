package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.function.Predicate;

public interface Service {
    boolean addTask(Task task);
    List<Task> getTasks(Predicate<Task> condition);
    boolean setDone(Task task);
}
