package com.my.TaskManagerServlet.model.dao.impl;


import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.exception.Messages;
import com.my.TaskManagerServlet.model.dao.inf.DaoFactory;
import com.my.TaskManagerServlet.model.dao.inf.TodoDao;
import com.my.TaskManagerServlet.model.dao.inf.UserDao;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private static final Logger LOG = Logger.getLogger(JDBCDaoFactory.class);
    //    private DataSource dataSource = ConnectionPoolHolder.getDataSource();
    private final DataSource dataSource;



    public JDBCDaoFactory() throws DBException {
        try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                dataSource = (DataSource) envContext.lookup("jdbc/taskManagerDB");
                LOG.trace("Data source ==> " + dataSource);
            } catch (NamingException ex) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            }
    }


    @Override
    public UserDao createUserDao() throws DBException {
        return new JDBCUserDao(getConnection());
    }
    @Override
    public TodoDao createTodoDao() throws DBException {
        return new JDBCTodoDao(getConnection());
    }

    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in
     * your WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return DB connection.
     */
    private Connection getConnection() throws DBException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }
}
