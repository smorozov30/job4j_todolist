package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.function.Predicate;

public interface Store {
    Task addTask(Task task);
    List<Task> getTasks(Predicate<Task> condition);
    Task setDone(Task task);
}
