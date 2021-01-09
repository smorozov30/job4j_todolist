package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HibernateStore;
import ru.job4j.todo.store.Store;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Predicate;

public class HibernateService implements Service {
    private final Store store = HibernateStore.instOf();

    private HibernateService() {

    }

    private static final class Lazy {
        private static final Service INST = new HibernateService();
    }

    public static Service instOf() {
        return HibernateService.Lazy.INST;
    }

    @Override
    public boolean addTask(Task task) {
        task.setCreated(new Timestamp(System.currentTimeMillis()));
        task = store.addTask(task);
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
}
