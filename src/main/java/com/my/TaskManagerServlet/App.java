package com.my.TaskManagerServlet;

import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.dao.impl.JDBCTodoDao;
import com.my.TaskManagerServlet.model.dao.impl.JDBCUserDao;
import com.my.TaskManagerServlet.model.dao.inf.TodoDao;
import com.my.TaskManagerServlet.model.dao.inf.UserDao;
import com.my.TaskManagerServlet.model.entity.Todo;
import com.my.TaskManagerServlet.model.entity.User;
import com.my.TaskManagerServlet.util.DBRequests;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) throws DBException, SQLException {
        System.out.println("____App.main method.");

        Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/taskmanagerdb" +
                        "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                                "user" ,
                                "user");

//        Statement query = con.createStatement();
//        ResultSet rs = query.executeQuery("SELECT * FROM todos");
//            query.executeUpdate("INSERT INTO todos ('description') VALUES ('some description')");

//        while( rs.next()) {
//            System.out.println(rs.getString("id"));
//            System.out.println(rs.getString("description"));
//            System.out.println(rs.getString("created_at"));
//        }

        TodoDao todoDao = new JDBCTodoDao(con);
        UserDao userDao = new JDBCUserDao(con);

        if(true) {
//            System.out.println("findAll------------------");
//            List<Todo> all = todoDao.findAll();
//            all.forEach(System.out::println);
//            System.out.println("findByID------------------");
//            System.out.println(todoDao.findById(52L));
//            System.out.println("findByUser------------------");
//            todoDao.findAllByUserID(2L).forEach(System.out::println);
            System.out.println("Create new todo------------------");
            Todo todo = Todo.TodoBuilder.aTodo()
                    .withDescription("new todo for user 2")
                    .withCreatedAt(LocalDateTime.now())
                    .withTargetDate(LocalDate.now())
                    .withUser(User.UserBuilder.anUser().withId(2L).build())
                    .build();
            System.out.println("todo id=" + todo.getId());
            System.out.println(todo);
//            todoDao.create(todo);
//            System.out.println("todo id=" + todo.getId());
//            System.out.println(todoDao.findById(todo.getId()));

            try (PreparedStatement ps = con.prepareStatement(DBRequests.SQL_ADD_NEW_TODO, Statement.RETURN_GENERATED_KEYS)) {
                ps.setObject(1, todo.getCreatedAt());
                ps.setString(2, todo.getDescription());
                ps.setObject(3, todo.getFinished());
                ps.setObject(4, todo.getStarted());
                ps.setObject(5, LocalDate.now().toString());
                ps.setLong(6, todo.getUser().getId());
                if(ps.executeUpdate()>0){
                    try(ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) todo.setId(rs.getLong(1));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

            System.out.println(todoDao.findById(todo.getId()));
//


//            System.out.println("Update TODO------------------");
//            todo.setDescription("Updates description for TODO");
//            todoDao.update(todo);
//            System.out.println(todoDao.findById(todo.getId()));
//            System.out.println("DELETE TODO------------------");
//            todoDao.delete(todo.getId());
//            try {
//                System.out.println(todoDao.findById(todo.getId()));
//            } catch (DBException e) {
//                System.out.println(e.getMessage());
//            }
//            System.out.println("USER------------------");
        }

//        userDao.findAll().forEach(System.out::println);
//        userDao.findAllWithRoles().forEach(System.out::println);
//        userDao.findAllWithRolesAndTodos().forEach(System.out::println);

/*        User user = User.UserBuilder.anUser()
                .withEmail("testUSer2@gmail.com")
                .withFirstName("First Name")
                .withLastName("Last Name")
                .withPassword("password")
                .withRoles(new HashSet<>(Collections.singletonList(new Role(1L, RoleType.ROLE_USER.name()))))
                .build();
        System.out.println(user);
        userDao.create(user);
        System.out.println(user);
        System.out.println(userDao.findById(user.getId()));
        userDao.delete(user.getId());
        System.out.println(userDao.findById(user.getId()));*/

//        userDao.delete(209L);
//        System.out.println(userDao.findById(2L));

//        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder();
//        String  originalPassword = "admin";
//        String  originalPasswordEncrypted = "{bcrypt}$2a$10$pL1oszLc74Eg1EwetiLtgukDOALc6o4LQcGraEhQ.7wsrHtvKM2xu";
//
//        System.out.println(delegatingPasswordEncoder.encode(originalPassword));
//        System.out.println(originalPasswordEncrypted);
//        System.out.println(delegatingPasswordEncoder.matches(originalPassword, originalPasswordEncrypted));

    }
}
