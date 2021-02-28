package com.my.TaskManagerServlet.util;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author D.Kolesnikov
 * 
 */
public final class Path {
	
	// pages
	public static final String PAGE_HOME = "index.jsp";
	public static final String PAGE_LOGIN = "login.jsp";
	public static final String PAGE_REGISTRATION = "/WEB-INF/jsp/registration.jsp";
	public static final String PAGE_ERROR_PAGE = "WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_USERS = "/WEB-INF/jsp/users.jsp";
	public static final String PAGE_TASKS = "/WEB-INF/jsp/tasks.jsp";
	public static final String PAGE_TASK = "/WEB-INF/jsp/todo-form.jsp";
	public static final String PAGE_EDIT_TASK = "/WEB-INF/jsp/edit-task.jsp";

	public static final String PAGE_LIST_MENU = "/WEB-INF/jsp/client/list_menu.jsp";
	public static final String PAGE_LIST_ORDERS = "/WEB-INF/jsp/admin/list_orders.jsp";

	// commands
	public static final String COMMAND_LIST_ORDERS = "/controller?command=listOrders";
	public static final String COMMAND_LIST_MENU = "/controller?command=listMenu";
	public static final String COMMAND_TASKS = "app?command=tasks";

	public static final String REDIRECT = "redirect:";



}