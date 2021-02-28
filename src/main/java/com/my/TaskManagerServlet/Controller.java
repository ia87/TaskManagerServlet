package com.my.TaskManagerServlet;

import com.my.TaskManagerServlet.exception.AppException;
import com.my.TaskManagerServlet.controller.command.Command;
import com.my.TaskManagerServlet.controller.command.CommandContainer;
import com.my.TaskManagerServlet.util.Path;
import org.apache.log4j.Logger;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "app", value = "/app")
public class Controller extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(Controller.class);


    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.info("doGet Servlet");
        process(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        LOG.info("doPost Servlet");
        process(request, response);
    }


    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        LOG.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        LOG.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained command --> " + command);

        // execute command and get page address
        String page = Path.PAGE_ERROR_PAGE;
        try {
            page = command.execute(request, response);
        } catch (AppException ex) {
            request.setAttribute("errorMessage", ex.getMessage());
        }
        LOG.trace("Forward address --> " + page);
        LOG.debug("Controller finished, now go to page address --> " + page);

        // go to page
        if(page.contains(Path.REDIRECT)){
            response.sendRedirect(page.replace(Path.REDIRECT, ""));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

}