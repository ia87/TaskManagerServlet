package com.my.TaskManagerServlet.controller.command.login;

import com.my.TaskManagerServlet.controller.command.Command;
import com.my.TaskManagerServlet.exception.AppException;
import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.entity.User;
import com.my.TaskManagerServlet.model.service.UserService;
import com.my.TaskManagerServlet.util.DelegatingPasswordEncoder;
import com.my.TaskManagerServlet.util.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Login command.
 * 
 *
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;
	private static final Logger LOG = Logger.getLogger(LoginCommand.class);
	UserService userService = new UserService();
	DelegatingPasswordEncoder delegatingPasswordEncoder;


	public LoginCommand() throws DBException {
		delegatingPasswordEncoder = new DelegatingPasswordEncoder();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		if("GET".equals(request.getMethod())) return Path.PAGE_LOGIN;



		HttpSession session = request.getSession();


		// obtain login and password from a request
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);

		String password = request.getParameter("password");
		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			LOG.error("Login/password cannot be empty");
			throw new AppException("Login/password cannot be empty");
		}

		User user = Optional.ofNullable(userService.getUserByEmail(login))
				.orElseThrow(() -> new AppException("Cannot find user with such login/password"));
		LOG.trace("Found in DB: user --> " + user);

		boolean matches = delegatingPasswordEncoder.matches(password, user.getPassword());
		if (!matches) throw new AppException("Wrong password");

		String forward = Path.REDIRECT + Path.PAGE_HOME;

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		user.getRoles().forEach(role -> session.setAttribute("is"+role.toString().replace("ROLE_",""),true));
		if (!user.getRoles().isEmpty()) session.setAttribute("unfinishedTasks", String.valueOf(user.getTodos().size()));

		LOG.info("User " + user + " logged as " + user.getRoles());

		LOG.debug("Command finished");
		return forward;

	}

}