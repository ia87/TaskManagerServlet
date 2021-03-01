package com.my.TaskManagerServlet.controller.command;

import com.my.TaskManagerServlet.controller.command.login.LoginCommand;
import com.my.TaskManagerServlet.controller.command.login.LogoutCommand;
import com.my.TaskManagerServlet.controller.command.login.RegistrationCommand;
import com.my.TaskManagerServlet.controller.command.task.*;
import com.my.TaskManagerServlet.controller.command.user.UsersCommand;
import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.service.UserService;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 * 
 */
public class CommandContainer {
	
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);
	
	private static final Map<String, Command> commands = new TreeMap<>();
	
	static {
		// common commands
		try {
			commands.put("login", new LoginCommand());
			commands.put("users", new UsersCommand(new UserService()));
			commands.put("tasks", new TasksCommand());
			commands.put("addTask", new AddTaskCommand());
			commands.put("editTask", new EditTaskCommand());
			commands.put("startTask", new StartTaskCommand());
			commands.put("finishTask", new FinishTaskCommand());
			commands.put("deleteTask", new DeleteTaskCommand());
			commands.put("registration", new RegistrationCommand());

		} catch (DBException e) {
			LOG.error(e.getMessage());
		}
		commands.put("logout", new LogoutCommand());
		commands.put("noCommand", new NoCommand());

		// client commands
//		commands.put("listMenu", new ListMenuCommand());
		
		// admin commands
//		commands.put("listOrders", new ListOrdersCommand());
		
		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		
		return commands.get(commandName);
	}
	
}