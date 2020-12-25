package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Task;

import java.util.List;
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
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        }
        return task;
    }

    @Override
    public List<Task> getTasks(Predicate<Task> condition) {
        List<Task> result = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.createQuery("FROM ru.job4j.todo.model.Task").list();
            session.getTransaction().commit();
        }
        return result.stream().filter(condition).collect(Collectors.toList());
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
}
