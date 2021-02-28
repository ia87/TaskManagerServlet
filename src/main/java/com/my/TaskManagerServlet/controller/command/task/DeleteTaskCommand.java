package com.my.TaskManagerServlet.controller.command.task;

import com.my.TaskManagerServlet.controller.command.Command;
import com.my.TaskManagerServlet.exception.AppException;
import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.entity.User;
import com.my.TaskManagerServlet.model.service.TodoService;
import com.my.TaskManagerServlet.util.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteTaskCommand extends Command {
    TodoService todoService = new TodoService();
    private static final Logger LOG = Logger.getLogger(DeleteTaskCommand.class);


    public DeleteTaskCommand() throws DBException {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();

        String taskId = request.getParameter("taskId");
        String userId = request.getParameter("userId");
        User user = (User) session.getAttribute("user");
        if (userId==null || userId.isEmpty()) userId = String.valueOf(user.getId());

        if (taskId!=null && !taskId.isEmpty()){
            todoService.deleteTodo(Long.parseLong(taskId));
        }
        return Path.REDIRECT+Path.COMMAND_TASKS+"&userId="+userId;
    }
}
