package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HibernateStore implements Store {
    private final SessionFactory sf;

    private HibernateStore() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private static final class Lazy {
        private static final Store INST = new HibernateStore();
    }

    public static Store instOf() {
        return HibernateStore.Lazy.INST;
    }

    @Override
    public Task addTask(Task task) {
        return (Task) add(session -> session.save(task));
    }

    @Override
    public List<Task> getTasks(Predicate<Task> condition) {
        return get(session -> session.createQuery("FROM ru.job4j.todo.model.Task").list(), condition);
    }

    @Override
    public Task setDone(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            task = session.get(Task.class, task.getId());
            task.setDone(!task.isDone());
            session.update(task);
            session.getTransaction().commit();
        }
        return task;
    }

    @Override
    public List<User> getUsers(Predicate<User> condition) {
        return get(session -> session.createQuery("FROM ru.job4j.todo.model.User").list(), condition);
    }

    @Override
    public User addUser(User user) {
        return (User) add(session -> session.save(user));
    }

    private <T> T add(final Function<Session, T> command) {
        T result = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = command.apply(session);
            session.getTransaction().commit();
        }
        return result;
    }

    private <T> List<T> get(final Function<Session, List<T>> command, Predicate<T> condition ){
        List<T> result = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = command.apply(session);
            session.getTransaction().commit();
        }
        return result.stream().filter(condition).collect(Collectors.toList());
    }
}
