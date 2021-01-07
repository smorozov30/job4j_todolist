package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Predicate;

public interface Store {
    Task addTask(Task task);
    List<Task> getTasks(Predicate<Task> condition);
    Task setDone(Task task);
    List<User> getUsers(Predicate<User> condition);
    User addUser(User user);
}
