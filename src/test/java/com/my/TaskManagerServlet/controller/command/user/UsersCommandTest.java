package com.my.TaskManagerServlet.controller.command.user;

import com.my.TaskManagerServlet.exception.AppException;
import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.exception.Messages;
import com.my.TaskManagerServlet.model.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UsersCommandTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;
    private static UserService userService;

    @BeforeAll
    static void beforeAll() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        userService = mock(UserService.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void executeTest() throws AppException, ServletException, IOException {
        userService = mock(UserService.class);
        UsersCommand command  = new UsersCommand(userService);

        assertEquals( "/WEB-INF/jsp/users.jsp", command.execute(request, response));
        verify(session, atLeastOnce()).setAttribute(eq("users"), anyCollection());
    }

    @Test
    void executeWIthExceptionTest() throws AppException, ServletException, IOException {
        userService = mock(UserService.class);
        UsersCommand command  = new UsersCommand(userService);
        when(userService.getAllUsers()).thenThrow(new DBException(Messages.ERR_CANNOT_OBTAIN_USERS));

        assertThrows(Throwable.class, ()->{
            command.execute(request, response);
        });
    }
}