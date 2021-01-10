package ru.job4j.todo.store;

import org.junit.Test;
import ru.job4j.todo.model.Task;

import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MemStoreTest {

    @Test
    public void whenAddNewTaskThenReturnNull() {
        Store store = MemStore.instOf();
        Task task = store.addTask(new Task(3, "Описание",
                                             new Timestamp(System.currentTimeMillis()),
                                        false), new String[]{"1"});
        assertNull(task);
    }

    @Test
    public void whenAddExistIdThenReturnNotNull() {
        Store store = MemStore.instOf();
        Task added = new Task(1, "Описание", new Timestamp(System.currentTimeMillis()), false);
        Task result = store.addTask(added, new String[]{"1"});
        assertThat(added.getId(), is(result.getId()));
    }

    @Test
    public void whenGetDoneTasksThenReturnListSizeOne() {
        int result = MemStore.instOf().getTasks(Task::isDone).size();
        assertThat(result, is(1));
    }

    @Test
    public void setDone() {
        Store store = MemStore.instOf();
        Task update = new Task(1);
        update = store.setDone(update);
        assertFalse(update.isDone());
    }
}