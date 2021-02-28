package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = Util.getHibernateSession();
            session.beginTransaction();

            

            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Невозможно создать таблицу! Message: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = Util.getHibernateSession();
            session.beginTransaction();


            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Невозможно удалить таблицу! Message: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = Util.getHibernateSession();
            session.beginTransaction();


            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Невозможно добавить запись! Message: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getHibernateSession();
            session.beginTransaction();


            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Невозможно удалить запись! Message: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        try {
            session = Util.getHibernateSession();
            session.beginTransaction();


            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Невозможно извлечь записи! Message: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = Util.getHibernateSession();
            session.beginTransaction();


            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Невозможно очистить таблицу! Message: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
