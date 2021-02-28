package com.my.TaskManagerServlet.controller.command.task;

import com.my.TaskManagerServlet.controller.command.Command;
import com.my.TaskManagerServlet.exception.AppException;
import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.service.TodoService;
import com.my.TaskManagerServlet.util.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FinishTaskCommand extends Command {
    TodoService todoService = new TodoService();
    private static final Logger LOG = Logger.getLogger(FinishTaskCommand.class);

    public FinishTaskCommand() throws DBException {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        String taskId = request.getParameter("taskId");
        String userId = request.getParameter("userId");
        if(taskId!=null && !taskId.isEmpty()){
            try {
                todoService.finishTodo(Long.parseLong(taskId));
                LOG.debug("Finished TODO id = " + taskId);
            } catch (DBException e) {
                LOG.error("{}", e);
                String errorMessage = "Can't update Task! ";
                request.setAttribute("errorMessage", errorMessage);
                return Path.PAGE_ERROR_PAGE;
            }
        }
        return Path.REDIRECT+Path.COMMAND_TASKS+"&userId="+userId;
    }
}
