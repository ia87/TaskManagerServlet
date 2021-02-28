package com.my.TaskManagerServlet.controller.command.task;

import com.my.TaskManagerServlet.controller.command.Command;
import com.my.TaskManagerServlet.exception.AppException;
import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.entity.Todo;
import com.my.TaskManagerServlet.model.entity.User;
import com.my.TaskManagerServlet.model.service.TodoService;
import com.my.TaskManagerServlet.util.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddTaskCommand extends Command {
    TodoService todoService = new TodoService();
    private static final Logger LOG = Logger.getLogger(AddTaskCommand.class);


    public AddTaskCommand() throws DBException {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();
        String method = request.getMethod();

        String userId = request.getParameter("userId");
        User user = (User) session.getAttribute("user");
        if (userId != null) user = User.UserBuilder.anUser()
                .withId(Long.parseLong(userId)).build();

        if (!"GET".equals(method)) {
            String description = request.getParameter("description");
            LOG.debug("Task description: " + description);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"), df);

            Todo todo = Todo.TodoBuilder.aTodo()
                    .withDescription(description)
                    .withTargetDate(targetDate)
                    .withCreatedAt(LocalDateTime.now())
                    .withUser(user)
                    .build();
            try {
                todoService.insertNewTodo(todo);
                LOG.debug( "new task added to DB");
            } catch (SQLException e) {
                LOG.error("{}", e);
                throw new AppException("Can't insert new Task!");
            }
            return Path.REDIRECT+Path.COMMAND_TASKS+"&userId="+userId;
        }
        session.setAttribute("userTaskId", userId);
        return Path.PAGE_TASK;
    }
}
