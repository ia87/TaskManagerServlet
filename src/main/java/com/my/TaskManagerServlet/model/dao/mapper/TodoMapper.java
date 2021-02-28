package com.my.TaskManagerServlet.model.dao.mapper;

import com.my.TaskManagerServlet.model.dao.Fields;
import com.my.TaskManagerServlet.model.entity.Todo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class TodoMapper implements ObjectMapper<Todo> {


    @Override
    public Todo extractFromResultSet(ResultSet rs) throws SQLException {
        return Todo.TodoBuilder.aTodo()
            .withId(rs.getLong(Fields.ENTITY_ID))
            .withDescription(rs.getString(Fields.TODO_DESCRIPTION))
            .withTargetDate(rs.getObject(Fields.TODO_TARGET_DATE, LocalDate.class))
            .withCreatedAt(rs.getObject(Fields.TODO_CREATED_AT, LocalDateTime.class))
            .withStarted  (rs.getObject(Fields.TODO_STARTED, LocalDateTime.class))
            .withFinished(rs.getObject(Fields.TODO_FINISHED, LocalDateTime.class)).build();
    }

    @Override
    public Todo makeUnique(Map<Long, Todo> cache, Todo todo) {
        cache.putIfAbsent(todo.getId(), todo);
        return cache.get(todo.getId());
    }
}
