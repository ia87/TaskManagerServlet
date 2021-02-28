package com.my.TaskManagerServlet.model.service;


import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.dao.impl.JDBCDaoFactory;
import com.my.TaskManagerServlet.model.dao.inf.DaoFactory;
import com.my.TaskManagerServlet.model.dao.inf.TodoDao;
import com.my.TaskManagerServlet.model.entity.Todo;

import java.sql.SQLException;
import java.util.List;

public class TodoService {

    DaoFactory daoFactory = JDBCDaoFactory.getInstance();

    public TodoService() throws DBException {
    }

    public List<Todo> getAllTodos() throws DBException {
        try (TodoDao dao = daoFactory.createTodoDao()) {
            return dao.findAll();
        }
    }

    public Todo getTodoByID(Long id) throws DBException {
        try (TodoDao dao = daoFactory.createTodoDao()) {
            return dao.findById(id);
        }
    }

    public List<Todo> getAllByUserId(Long id, int page, int size, String sortingBy, String sortingOrder) throws DBException {
        try (TodoDao dao = daoFactory.createTodoDao()) {
            return dao.findAllByUserID(id, page, size, sortingBy, sortingOrder);
        }
    }

    public void insertNewTodo(Todo todo) throws DBException, SQLException {
        try (TodoDao dao = daoFactory.createTodoDao()) {
            dao.create(todo);
        }
    }

    public void updateTodo(Todo todo) throws DBException, SQLException {
        try (TodoDao dao = daoFactory.createTodoDao()) {
            dao.update(todo);
        }
    }

    public void deleteTodo(long id) throws DBException {
        try (TodoDao dao = daoFactory.createTodoDao()) {
            dao.delete(id);
        }
    }

    public void startTodo(long id) throws DBException {
        try (TodoDao dao = daoFactory.createTodoDao()) {
            dao.startTodo(id);
        }
    }

    public void finishTodo(long id) throws DBException {
        try (TodoDao dao = daoFactory.createTodoDao()) {
            dao.finishTodo(id);
        }
    }
    public int countTodos(Long userId) throws DBException {
        try (TodoDao dao = daoFactory.createTodoDao()) {
            return dao.getTasksCount(userId);
        }
    }
}
