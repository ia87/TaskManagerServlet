package com.my.TaskManagerServlet.controller.command;

import com.my.TaskManagerServlet.util.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * No command.
 * 
 * @author D.Kolesnikov
 * 
 */
public class NoCommand extends Command {

	private static final long serialVersionUID = -2785976616686657267L;

	private static final Logger LOG = Logger.getLogger(NoCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");
		
		String errorMessage = "No such command";
		request.setAttribute("errorMessage", errorMessage);
		LOG.error("Set the request attribute: errorMessage --> " + errorMessage);

		LOG.debug("Command finished");
		return Path.PAGE_ERROR_PAGE;
	}

}