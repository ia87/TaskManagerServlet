package com.my.TaskManagerServlet.model.dao.inf;

import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> extends AutoCloseable {
    User create (T entity) throws DBException, SQLException;
    T findById(Long id) throws DBException;
    List<T> findAll() throws DBException;
    void update(T entity) throws DBException;
    void delete(Long id) throws DBException;
    void close();
}
