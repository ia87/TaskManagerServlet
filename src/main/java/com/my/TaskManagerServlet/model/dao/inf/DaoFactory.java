package com.my.TaskManagerServlet.model.dao.inf;


import com.my.TaskManagerServlet.exception.DBException;
import com.my.TaskManagerServlet.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao() throws DBException;
    public abstract TodoDao createTodoDao() throws DBException;

    public static DaoFactory getInstance() throws DBException {
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
