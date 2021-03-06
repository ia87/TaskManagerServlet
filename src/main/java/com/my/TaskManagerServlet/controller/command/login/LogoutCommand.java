package com.my.TaskManagerServlet.controller.command.login;

import com.my.TaskManagerServlet.controller.command.Command;
import com.my.TaskManagerServlet.util.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout command.
 * 
 * @author D.Kolesnikov
 * 
 */
public class LogoutCommand extends Command {

	private static final long serialVersionUID = -2785976616686657267L;
	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		LOG.debug("Command finished");
		return Path.REDIRECT+Path.PAGE_HOME;
	}

}