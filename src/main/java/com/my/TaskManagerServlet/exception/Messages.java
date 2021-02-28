package com.my.TaskManagerServlet.exception;

/**
 * Holder for messages of exceptions.
 * 
 *
 */
public class Messages {

	private Messages() {
		// no op
	}
	
	public static final String ERR_CANNOT_OBTAIN_TODO_BY_ID = "Cannot obtain todo by id";
	public static final String ERR_CANNOT_OBTAIN_TODO_BY_USER_ID = "Cannot obtain todos by user_id";
	public static final String ERR_CANNOT_OBTAIN_TODOS = "Cannot obtain todos";
	public static final String ERR_INSERT_NEW_TODO_TO_DB = "Cannot insert new TODO to DB";
	public static final String ERR_UPDATING_TODO_TO_DB = "Cannot update TODO";
	public static final String ERR_UPDATING_USER_TO_DB = "Cannot update USER";
	public static final String ERR_DELETING_TODO_FROM_DB = "Cannot delete TODO from DB";
	public static final String ERR_DELETING_USER_FROM_DB = "Cannot delete USER from DB";
	public static final String ERR_COUNT_TODOS_FROM_DB = "Cannot count tasks from DB";

	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";

	public static final String ERR_CANNOT_OBTAIN_CATEGORIES = "Cannot obtain categories";

	public static final String ERR_CANNOT_OBTAIN_MENU_ITEMS = "Cannot obtain menu items";

	public static final String ERR_CANNOT_OBTAIN_MENU_ITEMS_BY_ORDER = "Cannot obtain menu items by order";

	public static final String ERR_CANNOT_OBTAIN_MENU_ITEMS_BY_IDENTIFIERS = "Cannot obtain menu items by its identifiers";

	public static final String ERR_CANNOT_OBTAIN_ORDERS = "Cannot obtain orders";

	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_STATUS_ID = "Cannot obtain orders by status id";

	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_IDENTIFIERS = "Cannot obtain orders by its identifiers";

	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_USER_AND_STATUS_ID = "Cannot obtain orders by user and status id";

	public static final String ERR_CANNOT_OBTAIN_USERS = "Cannot obtain users";
	public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain a user by its id";

	public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain a user by its login";

	public static final String ERR_CANNOT_ADD_NEW_USER = "Cannot add new user";
	public static final String ERR_CANNOT_UPDATE_USER = "Cannot update a user";

	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";

	public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";

	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";
	
}