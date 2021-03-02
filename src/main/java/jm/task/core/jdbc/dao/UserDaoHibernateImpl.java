package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collector;

public class UserDaoHibernateImpl implements UserDao {

    /*
    Результат проверки ментором
    1. В дао вынести Session в переменную класса
    >>>
    2. Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
    >>> done
    3. Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    >>> done
     */

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = Util.getHibernateSession();
            session.beginTransaction();
            session.createSQLQuery("create table if not exists user(\n" +
                    " id bigint auto_increment primary key,\n" +
                    " name varchar(100) not null,\n" +
                    " lastName varchar(100) not null,\n" +
                    " age int not null)")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
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
            session.createSQLQuery("drop table if exists user")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
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
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
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
            User user = (User) session.load(User.class, new Long(4));
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
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
        List<User> list = null;
        try {
            session = Util.getHibernateSession();
            session.beginTransaction();

            list = session.createQuery("from jm.task.core.jdbc.model.User").list();

            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Невозможно извлечь записи! Message: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = Util.getHibernateSession();
            session.beginTransaction();
            session.createSQLQuery("truncate table user")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Невозможно очистить таблицу! Message: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
