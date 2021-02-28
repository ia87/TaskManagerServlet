package com.my.TaskManagerServlet.model.dao.inf;

import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User> {
    User findByEmail(String email) throws DBException;

    List<User> findAllWithRoles() throws DBException;

    List<User> findAllWithRolesAndTodos() throws DBException;
}
