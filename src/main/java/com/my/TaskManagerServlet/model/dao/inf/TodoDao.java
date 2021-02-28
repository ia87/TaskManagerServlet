package com.my.TaskManagerServlet.model.dao.inf;


import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.entity.Todo;

import java.util.List;

public interface TodoDao extends GenericDao<Todo> {
    List<Todo> findAllByUserID(Long id, int page, int size, String sortingBy, String sortingOrder) throws DBException;

    void startTodo(Long id) throws DBException;

    void finishTodo(long id) throws DBException;

    int getTasksCount(Long userId) throws DBException;
}
