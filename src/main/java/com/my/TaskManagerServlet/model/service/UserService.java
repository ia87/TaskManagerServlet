package com.my.TaskManagerServlet.model.service;

import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.dao.impl.JDBCDaoFactory;
import com.my.TaskManagerServlet.model.dao.inf.DaoFactory;
import com.my.TaskManagerServlet.model.dao.inf.UserDao;
import com.my.TaskManagerServlet.model.entity.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    DaoFactory daoFactory = JDBCDaoFactory.getInstance();

    public UserService() throws DBException {
    }

    public List<User> getAllUsers() throws DBException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findAllWithRoles();
        }
    }

    public User getUserByID(Long id) throws DBException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findById(id);
        }
    }

    public User getUserByEmail(String email) throws DBException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findByEmail(email);
        }
    }
    public User addNewUser(User user) throws DBException, SQLException {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.create(user);
        }
    }


}
