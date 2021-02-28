package com.my.TaskManagerServlet.model.dao.impl;

import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.exception.Messages;
import com.my.TaskManagerServlet.model.dao.Fields;
import com.my.TaskManagerServlet.model.dao.inf.TodoDao;
import com.my.TaskManagerServlet.model.dao.mapper.TodoMapper;
import com.my.TaskManagerServlet.model.dao.mapper.UserMapper;
import com.my.TaskManagerServlet.model.entity.Todo;
import com.my.TaskManagerServlet.model.entity.User;
import com.my.TaskManagerServlet.util.DBRequests;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class JDBCTodoDao implements TodoDao {
    private final Connection connection;
    private static final Logger LOG = Logger.getLogger(JDBCTodoDao.class);
    UserMapper userMapper;
    TodoMapper todoMapper;



    public JDBCTodoDao(Connection connection) {
        this.connection = connection;
        userMapper = new UserMapper();
        todoMapper = new TodoMapper();
    }

    @Override
    public User create(Todo entity) throws DBException {
        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_ADD_NEW_TODO, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForCreateOrUpdateTODO(entity, ps);
            if(ps.executeUpdate()>0){
                try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) entity.setId(rs.getLong(1));
             }
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_INSERT_NEW_TODO_TO_DB, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_INSERT_NEW_TODO_TO_DB);
        }
        return null;
    }

    @Override
    public Todo findById(Long id) throws DBException {
        Todo todo = null;
        User user;
        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_FIND_TODO_BY_ID)) {
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    todo =  todoMapper.extractFromResultSet(rs);
                    user = userMapper.extractFromResultSet(rs);
                    user.setId(Long.parseLong(rs.getString(Fields.TODO_USER_ID)));
                    todo.setUser(user);
                }
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_TODO_BY_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TODO_BY_ID);
        }
        return todo;
    }

    @Override
    public List<Todo> findAll() throws DBException {
        Map<Long, Todo> todos = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        final String query = DBRequests.SQL_FIND_ALL_TODOS;
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Todo todo = todoMapper.extractFromResultSet(rs);
                todo = todoMapper.makeUnique(todos, todo);
//                user.getTodos().add(todo);
            }
            return new ArrayList<>(todos.values());

        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_TODOS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TODOS);
        }
    }

    @Override
    public List<Todo> findAllByUserID(Long id, int page, int size, String sortingBy, String sortingOrder) throws DBException {
        Map<Long, Todo> todos = new LinkedHashMap<>();
        User user=null;
        final String query = DBRequests.SQL_FIND_ALL_TODOS_BY_USER_ID
                +DBRequests.SQL_ORDER_BY
                +sortingBy+" "+sortingOrder
                +DBRequests.SQL_LIMIT;
        LOG.debug("Query: "+query);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.setInt(2, page);
            ps.setInt(3, size);

            try( ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Todo todo = todoMapper.extractFromResultSet(rs);
                    User userMapped = userMapper.extractFromResultSet(rs);
                    userMapped.setId(id);
                    if (!userMapped.equals(user)) user = userMapped;
                    todoMapper.makeUnique(todos, todo);
                    todo.setUser(user);
                }
            }

            return new ArrayList<>(todos.values());

        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_TODO_BY_USER_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TODO_BY_USER_ID);
        }
    }

    @Override
    public void update(Todo entity) throws DBException {
        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_UPDATE_TODO)) {
            prepareStatementForCreateOrUpdateTODO(entity, ps);
            ps.setLong(7, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_UPDATING_TODO_TO_DB, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_UPDATING_TODO_TO_DB);
        }
    }

    private void prepareStatementForCreateOrUpdateTODO(Todo entity, PreparedStatement ps) throws SQLException {
        ps.setObject(1, entity.getCreatedAt());
        ps.setString(2, entity.getDescription());
        ps.setObject(3, entity.getFinished());
        ps.setObject(4, entity.getStarted());
        ps.setObject(5, entity.getTargetDate().toString());
        ps.setLong(6, entity.getUser().getId());
    }

    @Override
    public void delete(Long id) throws DBException {
        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_DELETE_TODO)) {
            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            LOG.error(Messages.ERR_DELETING_TODO_FROM_DB, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_DELETING_TODO_FROM_DB);
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

    @Override
    public void startTodo(Long id) throws DBException {
        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_UPDATE_TODO_SET_STARTED)) {
            ps.setObject(1, LocalDateTime.now());
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_UPDATING_TODO_TO_DB, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_UPDATING_TODO_TO_DB);
        }
    }

    @Override
    public void finishTodo(long id) throws DBException {
        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_UPDATE_TODO_SET_FINISHED)) {
            ps.setObject(1, LocalDateTime.now());
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_UPDATING_TODO_TO_DB, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_UPDATING_TODO_TO_DB);
        }
    }

    @Override
    public int getTasksCount(Long userId) throws DBException {
        int count=0;
        try (PreparedStatement ps = connection.prepareStatement(DBRequests.SQL_COUNT_TODOS_BY_USER_ID)) {
            ps.setLong(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) count=resultSet.getInt(1);
        return count;
        } catch (SQLException e) {
            LOG.error(Messages.ERR_COUNT_TODOS_FROM_DB, e);
            LOG.error(e.getMessage());
            throw new DBException(Messages.ERR_COUNT_TODOS_FROM_DB);
        }
    }
}
