package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.MemStore;
import ru.job4j.todo.store.Store;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Predicate;

public class MemService implements Service {
    private final Store store = MemStore.instOf();

    private MemService() {

    }

    private static final class Lazy {
        private static final Service INST = new MemService();
    }

    public static Service instOf() {
        return MemService.Lazy.INST;
    }

    @Override
    public boolean addTask(Task task, String[] cIds) {
        task.setCreated(new Timestamp(System.currentTimeMillis()));
        task = store.addTask(task, cIds);
        return task == null;
    }

    @Override
    public List<Task> getTasks(Predicate<Task> condition) {
        return store.getTasks(condition);
    }

    @Override
    public boolean setDone(Task task) {
        boolean before = task.isDone();
        task = store.setDone(task);
        return before != task.isDone();
    }

    @Override
    public User checkUser(String email) {
        return store.getUser(email);
    }

    @Override
    public void addUser(User user) {
        store.addUser(user);
    }

    @Override
    public List<Category> getCategories() {
        return store.getCategories();
    }
}
