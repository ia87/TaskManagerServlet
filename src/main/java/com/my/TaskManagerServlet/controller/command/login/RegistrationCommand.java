package com.my.TaskManagerServlet.controller.command.login;

import com.my.TaskManagerServlet.controller.command.Command;
import com.my.TaskManagerServlet.exception.AppException;
import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.entity.Role;
import com.my.TaskManagerServlet.model.entity.RoleType;
import com.my.TaskManagerServlet.model.entity.User;
import com.my.TaskManagerServlet.model.service.UserService;
import com.my.TaskManagerServlet.util.DelegatingPasswordEncoder;
import com.my.TaskManagerServlet.util.Path;
import com.my.TaskManagerServlet.util.Validators;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;


public class RegistrationCommand extends Command {
    private static final long serialVersionUID = -30715374936692473L;
    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);
    UserService userService = new UserService();
    DelegatingPasswordEncoder delegatingPasswordEncoder;

    public RegistrationCommand() throws DBException {
        delegatingPasswordEncoder = new DelegatingPasswordEncoder();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        if("GET".equals(request.getMethod())) return Path.PAGE_REGISTRATION;

        String page = Path.PAGE_ERROR_PAGE;
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPass = request.getParameter("confirmPass");

        String valid;

        valid = Validators.validateFields(firstName, "firstName", 20);
            if (valid != null) throw new AppException(valid);
        valid = Validators.validateFields(lastName, "lastName", 20);
            if (valid != null) throw new AppException(valid);
        valid = Validators.validateEMail(email);
            if (valid != null) throw new AppException(valid);
            if (password == null || !password.equals(confirmPass)) throw new AppException("Password mismatch");

        User user = User.UserBuilder.anUser()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withPassword(delegatingPasswordEncoder.encode(password))
                .withRoles(new HashSet<>(Collections.singletonList(new Role(1L, RoleType.ROLE_USER.name()))))
                .build();
        LOG.info(user);
        try {
            userService.addNewUser(user);
            LOG.info(user);

        } catch (SQLException throwables) {
            LOG.error(throwables);
            String errorMessage = "Can't add new User!";
            request.setAttribute("errorMessage", errorMessage);
            return Path.PAGE_ERROR_PAGE;
        }


        page = Path.REDIRECT + Path.PAGE_LOGIN;
        request.setAttribute("register", true);

        return page;
    }
}
