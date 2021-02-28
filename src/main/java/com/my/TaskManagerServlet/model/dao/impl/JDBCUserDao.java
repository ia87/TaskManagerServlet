package com.my.TaskManagerServlet.model.dao.impl;

import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.exception.Messages;
import com.my.TaskManagerServlet.model.dao.Fields;
import com.my.TaskManagerServlet.model.dao.inf.UserDao;
import com.my.TaskManagerServlet.model.dao.mapper.RoleMapper;
import com.my.TaskManagerServlet.model.dao.mapper.TodoMapper;
import com.my.TaskManagerServlet.model.dao.mapper.UserMapper;
import com.my.TaskManagerServlet.model.entity.Role;
import com.my.TaskManagerServlet.model.entity.Todo;
import com.my.TaskManagerServlet.model.entity.User;
import com.my.TaskManagerServlet.util.DBRequests;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao {
    private Connection connection;
    private static final Logger LOG = Logger.getLogger(JDBCUserDao.class);
    UserMapper userMapper;
    TodoMapper todoMapper;
    RoleMapper roleMapper;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
        userMapper = new UserMapper();
        todoMapper = new TodoMapper();
        roleMapper = new RoleMapper();
    }

    @Override
    public User create(User entity) throws DBException, SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_ADD_NEW_USER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement ps2 = connection.prepareStatement(DBRequests.SQL_ADD_ROLES_TO_USER_ROLES)) {
            prepareStatementForCreateOrUpdateUser(entity, ps);
            if(ps.executeUpdate()>0){
                try(ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) entity.setId(rs.getLong(1));
                }
            }
            ps2.setLong(1, entity.getId());
            ps2.setLong(2, 1L);
            ps2.executeUpdate();

        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_ADD_NEW_USER, e);
            LOG.error(e.getMessage());
            connection.rollback();
            throw new DBException(Messages.ERR_CANNOT_ADD_NEW_USER);
        } finally {
            connection.setAutoCommit(true);
        }


        return entity;
    }

    @Override
    public User findById(Long id) throws DBException {
        User user = null;
        Role role = null;
        Map<Long, Role> roles = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_FIND_USER_BY_ID_WITH_ROLES)) {
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    user =  userMapper.extractFromResultSet(rs);
                    role = roleMapper.extractFromResultSet(rs);
                    roleMapper.makeUnique(roles, role);
                    while (rs.next()) {
                        role = roleMapper.extractFromResultSet(rs);
                        roleMapper.makeUnique(roles, role);
                    }
                    user.setRoles(new HashSet<>(roles.values()));
                }

            }

        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID);
        }
        return user;
    }


    @Override
        public User findByEmail(String email) throws DBException {
            User user = null;
            Role role;
            Todo todo;
            Map<Long, Role> roles = new HashMap<>();
            Map<Long, Todo> todos = new HashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_FIND_USER_BY_EMAIL_WITH_ROLES_AND_UNFINISHED_TASKS)) {
                ps.setString(1, email);
                try(ResultSet rs = ps.executeQuery()) {
                    if(rs.next()){
                        user = userMapper.extractFromResultSet(rs);
                        role = roleMapper.extractFromResultSet(rs);
                        todo = todoMapper.extractFromResultSet(rs);
                        todo.setId(rs.getLong(Fields.TODO_ID));
                        roleMapper.makeUnique(roles, role);
                        if (todo.getId()>0) todoMapper.makeUnique(todos, todo);
                        while (rs.next()) {
                            role = roleMapper.extractFromResultSet(rs);
                            todo = todoMapper.extractFromResultSet(rs);
                            todo.setId(rs.getLong(Fields.TODO_ID));
                            roleMapper.makeUnique(roles, role);
                            if (todo.getId()>0) todoMapper.makeUnique(todos, todo);
                        }
                        user.setRoles(new HashSet<>(roles.values()));
                        user.setTodos(new HashSet<>(todos.values()));
                    }

                }

            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID);
            }
            return user;
        }

    @Override
    public List<User> findAll() throws DBException {
        Map<Long, User> users = new HashMap<>();
        final String query = DBRequests.SQL_FIND_ALL_USERS;
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs);
                userMapper.makeUnique(users, user);
            }
            return new ArrayList<>(users.values());
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS);
        }
    }
    @Override
    public List<User> findAllWithRoles() throws DBException {
        Map<Long, User> users = new HashMap<>();
        Map<Long, Role> roles = new HashMap<>();
        final String query = DBRequests.SQL_FIND_ALL_USERS_WITH_ROLES;
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs);
                Role role = roleMapper.extractFromResultSet(rs);

                user = userMapper.makeUnique(users, user);
                role = roleMapper.makeUnique(roles, role);

                user.getRoles().add(role);
            }
            return new ArrayList<>(users.values());

        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS);
        }
    }
@Override
    public List<User> findAllWithRolesAndTodos() throws DBException {
        Map<Long, Todo> todos = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        Map<Long, Role> roles = new HashMap<>();
        final String query = DBRequests.SQL_FIND_ALL_USERS_WITH_ROLES_AND_TASKS;
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs);
                Role role = roleMapper.extractFromResultSet(rs);
                Todo todo = todoMapper.extractFromResultSet(rs);

                user = userMapper.makeUnique(users, user);
                role = roleMapper.makeUnique(roles, role);
                todo = todoMapper.makeUnique(todos, todo);

                user.getRoles().add(role);
                user.getTodos().add(todo);
            }
            return new ArrayList<>(users.values());

        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS);
        }
    }

    @Override
    public void update(User entity) throws DBException {
        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_UPDATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForCreateOrUpdateUser(entity, ps);
            ps.setLong(5, entity.getId());
            if(ps.executeUpdate()>0){
                try(ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) entity.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_UPDATING_USER_TO_DB, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_UPDATING_USER_TO_DB);
        }
    }

    private void prepareStatementForCreateOrUpdateUser(User entity, PreparedStatement ps) throws SQLException {
        ps.setObject(1, entity.getEmail());
        ps.setString(2, entity.getFirstName());
        ps.setObject(3, entity.getLastName());
        ps.setObject(4, entity.getPassword());
    }

    @Override
    public void delete(Long id) throws DBException {
        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_DELETE_USER)) {
            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            LOG.error(Messages.ERR_DELETING_USER_FROM_DB, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_DELETING_USER_FROM_DB);
        }
    }

    @Override
    public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
