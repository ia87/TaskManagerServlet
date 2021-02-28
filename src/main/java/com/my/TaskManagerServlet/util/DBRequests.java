package com.my.TaskManagerServlet.util;

public final class DBRequests {

    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM user order by id";
    public static final String SQL_FIND_ALL_USERS_WITH_ROLES = "SELECT * FROM user u\n" +
            "join user_roles ur on u.id = ur.user_id\n" +
            "join role r on ur.roles_id=r.id\n" +
            "order by u.id;";
    public static final String SQL_FIND_ALL_USERS_WITH_ROLES_AND_TASKS = "SELECT * FROM user u\n" +
            "join user_roles ur on u.id = ur.user_id\n" +
            "join role r on ur.roles_id=r.id\n" +
            "join todos t on t.user_id=u.id\n" +
            "order by u.id;";

    public static final String SQL_FIND_ALL_TODOS = "SELECT * FROM todos order by id";
    public static final String SQL_COUNT_TODOS_BY_USER_ID = "SELECT count(*) from todos where todos.user_id=?";
    public static final String SQL_FIND_ALL_TODOS_BY_USER_ID = "SELECT * FROM todos t RIGHT JOIN user u ON t.user_id=u.id WHERE u.id=?";
    public static final String SQL_ORDER_BY = " order by t.";
    public static final String SQL_LIMIT = " limit ?,? ";

    public static final String SQL_FIND_TODO_BY_ID = "SELECT * FROM todos t JOIN user u ON t.user_id=u.id WHERE t.id=?";

    public static final String SQL_ADD_NEW_TODO =
            "INSERT INTO todos (created_at, description, finished, started, target_date, user_id) VALUES (?,?,?,?,?,?)";

    public static final String SQL_ADD_NEW_USER =
            "INSERT INTO user (email, first_name, last_name, password) VALUES (?,?,?,?)";

    public static final String SQL_ADD_ROLES_TO_USER_ROLES =
            "INSERT INTO user_roles (user_id, roles_id) VALUES (?,?)";

    public static final String SQL_UPDATE_TODO =
            "UPDATE todos SET created_at=?, description=?, finished=?, started=?, target_date=?, user_id=? WHERE id=?";

    public static final String SQL_UPDATE_TODO_SET_STARTED = "UPDATE todos SET started=? WHERE id=?";
    public static final String SQL_UPDATE_TODO_SET_FINISHED = "UPDATE todos SET finished=? WHERE id=?";
    public static final String SQL_UPDATE_USER =
            "UPDATE user SET email=?, first_name=?, last_name=?, password=? WHERE id=?";

    public static final String SQL_DELETE_TODO = "DELETE FROM todos WHERE id=?";
    public static final String SQL_DELETE_USER = "DELETE FROM user WHERE id=?";


    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE email=?";
    public static final String SQL_FIND_USER_BY_ID = "SELECT * FROM user WHERE id=?";
    public static final String SQL_FIND_USER_BY_ID_WITH_ROLES = "SELECT * FROM user u\n" +
            "join user_roles ur on u.id = ur.user_id\n" +
            "join role r on ur.roles_id=r.id\n" +
            "WHERE id=?";
    public static final String SQL_FIND_USER_BY_EMAIL_WITH_ROLES_AND_UNFINISHED_TASKS_COUNT = "SELECT * ,\n" +
            "       (\n" +
            "           SELECT COUNT(1)\n" +
            "           FROM todos t\n" +
            "           WHERE t.user_id=u.id and t.finished is null\n" +
            "        ) as unfinishedTasks\n" +
            "FROM user u\n" +
            "            join user_roles ur on u.id = ur.user_id\n" +
            "            join role r on ur.roles_id=r.id\n" +
            "            WHERE email=?";

    public static final String SQL_FIND_USER_BY_EMAIL_WITH_ROLES_AND_UNFINISHED_TASKS = "SELECT * FROM user u\n" +
            "            join user_roles ur on u.id = ur.user_id\n" +
            "            join role r on ur.roles_id=r.id\n" +
            "            left join todos t on t.user_id=u.id\n" +
            "WHERE u.email = ? AND t.finished is null\n" +
            "            order by u.id;";

    public static final String SQL_FIND_USER_BY_EMAIL_WITH_ROLES = "SELECT * FROM user u\n" +
            "join user_roles ur on u.id = ur.user_id\n" +
            "join role r on ur.roles_id=r.id\n" +
            "WHERE email=?";

    public static final String SQL_FIND_ORDERS_BY_STATUS_AND_USER = "SELECT * FROM orders WHERE status_id=? AND user_id=?";
    public static final String SQL_FIND_MENU_ITEMS_BY_ORDER = "select * from menu where id in (select menu_id from orders_menu where order_id=?)";
    public static final String SQL_FIND_ORDERS_BY_STATUS = "SELECT * FROM orders WHERE status_id=?";
    public static final String SQL_FIND_ALL_CATEGORIES = "SELECT * FROM categories";


    public static final String SQL_GET_USER_ORDER_BEANS = "SELECT o.id, u.first_name, u.last_name, o.bill, s.name"
            + "	FROM users u, orders o, statuses s"
            + "	WHERE o.user_id=u.id AND o.status_id=s.id";
}
