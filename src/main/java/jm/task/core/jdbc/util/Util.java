package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "first_lesson";
        String userName = "root";
        String password = "develop1379";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) throws SQLException, ClassNotFoundException {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(connectionURL, userName, password);
    }

    public static Session getHibernateSession() throws HibernateException {
        if (concreteSessionFactory == null) {
            openSessionFactory();
        }
        return concreteSessionFactory.openSession();
    }

    public static void closeSessionFactory() {
        if (concreteSessionFactory == null) {
            return;
        }
        concreteSessionFactory.close();
    }

    private static SessionFactory concreteSessionFactory = null;

    private static void openSessionFactory() {
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/first_lesson");
            prop.setProperty("hibernate.connection.username", "root");
            prop.setProperty("hibernate.connection.password", "develop1379");
            prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            prop.setProperty("current_session_context_class", "thread");
            prop.setProperty("show_sql", "true");

            concreteSessionFactory = new AnnotationConfiguration()
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
