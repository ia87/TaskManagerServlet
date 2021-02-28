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
import java.util.List;
import java.util.Optional;

public class TasksCommand extends Command {
    private static final Logger LOG = Logger.getLogger(TasksCommand.class);
    TodoService todoService = new TodoService();

    public TasksCommand() throws DBException {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();
        final User[] user = {(User) session.getAttribute("user")};

        int currentPage = Integer.parseInt(Optional.ofNullable(request.getParameter("currentPage")).orElse("1"));
        int recordsPerPage = Integer.parseInt(Optional.ofNullable(request.getParameter("recordsPerPage")).orElse("10"));
        String sortingOrder = Optional.ofNullable(request.getParameter("sortingOrder")).orElse("asc");
        String sortingBy = Optional.ofNullable(request.getParameter("sortingBy"))
                                            .orElse((String) session.getAttribute("tasksSortingBy"));
        if (sortingBy==null) sortingBy = "id";

        Long userId = user[0].getId();
        String s = Optional.ofNullable(request.getParameter("userId")).orElse("0");
        long idFromRequest = Long.parseLong(s);
        if(idFromRequest > 0) userId = idFromRequest;

        List<Todo> tasks = todoService.getAllByUserId(userId, (currentPage-1)*recordsPerPage, recordsPerPage, sortingBy, sortingOrder);
        int countTodos = todoService.countTodos(userId);
        int noOfPages = (int) Math.ceil(countTodos * 1.0 / recordsPerPage);

        session.setAttribute("tasks", tasks);
        session.setAttribute("noOfPages", noOfPages);
        session.setAttribute("currentPage", currentPage);
        session.setAttribute("recordsPerPage", recordsPerPage);
        session.setAttribute("userId", userId);
        session.setAttribute("tasksSortingBy", sortingBy);

        tasks.stream().findFirst().ifPresent(t -> user[0] =t.getUser());
        tasks.removeIf(t->t.getId()==0);
        session.setAttribute("userTasks", user[0]);
        return Path.PAGE_TASKS;
    }
}
