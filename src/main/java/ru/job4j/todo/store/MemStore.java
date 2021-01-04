package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MemStore implements Store {
    private static final AtomicInteger TASK_ID = new AtomicInteger(2);

    private final Map<Integer, Task> tasks = new ConcurrentHashMap<>();

    private MemStore() {
        tasks.put(1, new Task(1, "Task number 1", new Timestamp(System.currentTimeMillis()), true));
        tasks.put(2, new Task(2, "Task number 2", new Timestamp(System.currentTimeMillis()), false));
    }

    private static final class Lazy {
        private static final Store INST = new MemStore();
    }

    public static Store instOf() {
        return MemStore.Lazy.INST;
    }

    @Override
    public Task addTask(Task task) {
        if (task.getId() == 0) {
            task.setId(TASK_ID.incrementAndGet());
        }
        return tasks.putIfAbsent(task.getId(), task);
    }

    @Override
    public List<Task> getTasks(Predicate<Task> condition) {
        return tasks.values().stream().filter(condition).collect(Collectors.toList());
    }

    @Override
    public Task setDone(Task task) {
        int id = task.getId();
        task = tasks.get(id);
        task.setDone(!task.isDone());
        return task;
    }
}