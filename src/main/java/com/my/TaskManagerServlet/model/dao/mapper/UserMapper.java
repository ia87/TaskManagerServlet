package com.my.TaskManagerServlet.model.dao.mapper;

import com.my.TaskManagerServlet.model.dao.Fields;
import com.my.TaskManagerServlet.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.UserBuilder.anUser()
                .withId(rs.getLong(Fields.ENTITY_ID))
                .withEmail(rs.getString(Fields.USER_EMAIL))
                .withFirstName(rs.getString(Fields.USER_FIRST_NAME))
                .withLastName(rs.getString(Fields.USER_LAST_NAME))
                .withPassword(rs.getString(Fields.USER_PASSWORD))
                .withRoles(new HashSet<>())
                .withTodos(new HashSet<>())
                .build();

    }

    public User makeUnique(Map<Long, User> cache,
                              User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
