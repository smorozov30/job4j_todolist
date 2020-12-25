package ru.job4j.todo.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.todo.service.HibernateService;
import ru.job4j.todo.service.MemService;
import ru.job4j.todo.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HibernateService.class)
public class DoneServletTest {

    @Test
    public void doPost() throws ServletException, IOException {
        Service stubService = MemService.instOf();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        PowerMockito.mockStatic(HibernateService.class);
        when(HibernateService.instOf()).thenReturn(stubService);
        when(req.getParameter("id")).thenReturn("2");

        new DoneServlet().doPost(req, resp);
        assertThat(stubService.getTasks(task -> !task.isDone()).size(), is(0));
    }
}