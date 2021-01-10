package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Category;
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
    public Task addTask(Task task, String[] cIds) {
        return execute(session -> {
            for (String id : cIds) {
                Category category = session.find(Category.class, Integer.parseInt(id));
                task.addCategory(category);
            }
            session.save(task);
            return task;
        });
    }

    @Override
    public List<Task> getTasks(Predicate<Task> condition) {
        List<Task> list = execute(session -> session.createQuery("SELECT DISTINCT t FROM ru.job4j.todo.model.Task t JOIN FETCH t.categories").list());
        return list.stream().filter(condition).collect(Collectors.toList());
    }

    @Override
    public Task setDone(Task task) {
        return execute(session -> {
            Task temp = session.get(Task.class, task.getId());
            temp.setDone(!temp.isDone());
            session.update(temp);
            return temp;
        });
    }

    @Override
    public User getUser(String email) {
        List<User> list = execute(session -> session.createQuery("FROM ru.job4j.todo.model.User WHERE email = :email").setParameter("email", email).list());
        return list.stream().findFirst().orElse(null);
    }

    @Override
    public void addUser(User user) {
        execute(session -> session.save(user));
    }

    @Override
    public List<Category> getCategories() {
        return execute(session -> session.createQuery("FROM ru.job4j.todo.model.Category").list());
    }

    private <T> T execute(final Function<Session, T> command) {
        T result = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = command.apply(session);
            session.getTransaction().commit();
        }
        return result;
    }
}
