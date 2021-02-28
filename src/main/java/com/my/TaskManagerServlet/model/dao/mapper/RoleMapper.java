package com.my.TaskManagerServlet.model.dao.mapper;

import com.my.TaskManagerServlet.model.dao.Fields;
import com.my.TaskManagerServlet.model.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class RoleMapper implements ObjectMapper<Role> {
    @Override
    public Role extractFromResultSet(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong(Fields.USER_ROLE_ID));
        role.setName(rs.getString(Fields.USER_ROLE_NAME));
        return role;
    }

    @Override
    public Role makeUnique(Map<Long, Role> cache, Role role) {
        cache.putIfAbsent(role.getId(), role);
        return cache.get(role.getId());
    }
}
