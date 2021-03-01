package com.my.TaskManagerServlet.controller.command.user;

import com.my.TaskManagerServlet.controller.command.Command;
import com.my.TaskManagerServlet.controller.command.login.LoginCommand;
import com.my.TaskManagerServlet.exception.AppException;
import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.entity.User;
import com.my.TaskManagerServlet.model.service.UserService;
import com.my.TaskManagerServlet.util.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UsersCommand extends Command {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);
    UserService userService;

    public UsersCommand(UserService userService) throws DBException {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();
        List<User> allUsers = userService.getAllUsers();
        session.setAttribute("users", allUsers);
        return Path.PAGE_USERS;
    }
}
