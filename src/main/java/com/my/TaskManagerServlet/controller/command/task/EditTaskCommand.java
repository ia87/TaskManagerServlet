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

public class EditTaskCommand  extends Command {
    TodoService todoService = new TodoService();
    private static final Logger LOG = Logger.getLogger(EditTaskCommand.class);

    public EditTaskCommand() throws DBException {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();
        String method = request.getMethod();
        LOG.debug("Method: " + method);
        if (!"GET".equals(method)){
            long taskId = Long.parseLong(request.getParameter("taskId"));
            Long userId = Long.parseLong(request.getParameter("userId"));
            String username = request.getParameter("username");
            String description = request.getParameter("description");
            String createdAt = request.getParameter("createdAt");
            String started = request.getParameter("started");
            String finished = request.getParameter("finished");

            LocalDateTime createdAtTime=null;
            LocalDateTime startedTime=null;
            LocalDateTime finishedTime=null;

            if (createdAt!=null && !createdAt.isEmpty()) createdAtTime = LocalDateTime.parse(createdAt);
            if (started!=null && !started.isEmpty())  startedTime = LocalDateTime.parse(started);
            if (finished!=null & !finished.isEmpty()) finishedTime = LocalDateTime.parse(finished);

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));

            User user = (User) session.getAttribute("user");
            if (userId != null) user = User.UserBuilder.anUser()
                    .withId(userId).build();
            Todo todo = Todo.TodoBuilder.aTodo()
                    .withId(taskId)
                    .withDescription(description)
                    .withTargetDate(targetDate)
                    .withStarted(startedTime)
                    .withFinished(finishedTime)
                    .withCreatedAt(createdAtTime)
                    .withUser(user)
                    .build();
            try {
                    todoService.updateTodo(todo);
                } catch (SQLException e) {
                    LOG.error("{}", e);
                    String errorMessage = "Can't insert new Task! ";
                    request.setAttribute("errorMessage", errorMessage);
                    return Path.PAGE_ERROR_PAGE;
                }
            return Path.REDIRECT+Path.COMMAND_TASKS+"&userId="+userId;
        }

        String taskID = request.getParameter("taskId");
        LOG.debug("taskId = " + taskID);
        if(taskID!=null) {
            Todo todoByID = todoService.getTodoByID(Long.parseLong(taskID));
            request.setAttribute("todo", todoByID);
            LOG.debug("Added TODO to the request : " + todoByID);
        }
        return Path.PAGE_TASK;
    }
}
