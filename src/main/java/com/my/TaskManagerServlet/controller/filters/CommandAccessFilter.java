package com.my.TaskManagerServlet.controller.filters;

import com.my.TaskManagerServlet.model.entity.Role;
import com.my.TaskManagerServlet.model.entity.RoleType;
import com.my.TaskManagerServlet.model.entity.User;
import com.my.TaskManagerServlet.util.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter. Disabled by default. Uncomment Security filter
 * section in web.xml to enable.
 * 
 */
public class CommandAccessFilter implements Filter {
	
	private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

	// commands access	
	private Map<String, List<String>> accessMap = new HashMap<String, List<String>>();
	private List<String> commons = new ArrayList<String>();	
	private List<String> outOfControl = new ArrayList<String>();
	
	public void destroy() {
		LOG.debug("Filter destruction starts");
		// do nothing
		LOG.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.debug("Filter starts");
		
		if (accessAllowed(request)) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";
			
			request.setAttribute("errorMessage", errorMessasge);
			LOG.trace("Set the request attribute: errorMessage --> " + errorMessasge);
			
			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
					.forward(request, response);
		}
	}
	
	private boolean accessAllowed(ServletRequest request) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		if (commandName == null || commandName.isEmpty()) {
			LOG.trace("Empty command name");
			return false;
		}
		
		if (outOfControl.contains(commandName)) {
			return true;
		}
		
		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			LOG.trace("Empty session");
			return false;
		}

		User user = (User) session.getAttribute("user");
		if (user!=null){
			Set<Role> roles = user.getRoles();
			if (roles.isEmpty()) {
				LOG.trace("Empty user role");
				return false;
			}
//		LOG.trace(accessMap.get(userRole).contains(commandName));
//		LOG.trace(commons.contains(commandName));

			for (Role role : roles) {
				List<String> commands = Optional.ofNullable(accessMap.computeIfPresent(role.getName(), (k, v) -> v)).orElse(Collections.emptyList());
				if (commands.contains(commandName)) return true;
			}
			return commons.contains(commandName);

		}
		return false;
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization starts");
		
		// roles
		accessMap.put(RoleType.ROLE_ADMIN.name(), asList(fConfig.getInitParameter("admin")));
		accessMap.put(RoleType.ROLE_USER.name(), asList(fConfig.getInitParameter("user")));
		LOG.trace("Access map --> " + accessMap);

		// commons
		commons = asList(fConfig.getInitParameter("common"));
		LOG.trace("Common commands --> " + commons);

		// out of control
		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		LOG.trace("Out of control commands --> " + outOfControl);
		
		LOG.debug("Filter initialization finished");
	}
	
	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;		
	}
	
}