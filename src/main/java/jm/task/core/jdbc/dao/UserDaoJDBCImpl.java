package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    /*
    Результат проверки ментором:
    1. В блоке catch нужно выбрасывать исключения SQLException
    >>> но ни в методах UserDao, ни в методах UserService не объявлен выброс исключений, их допустимо менять?
    2. Где закрытие соединений в методах дао ?
    >>> используется try-with-resource, соединение объявлено в блоке ресурсов -> автоматически закрывается
    3. Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
    >>> done
    4. Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    >>> done
     */

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate("create table if not exists users(" +
                    "id int auto_increment, " +
                    "name varchar(100) not null, " +
                    "lastName varchar(100) not null, " +
                    "age int not null, " +
                    "constraint users_pk primary key (id))");
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate("drop table if exists users");
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Connection conn = Util.getMySQLConnection();
             PreparedStatement statement = conn.prepareStatement("insert into users(name, lastName, age) values(?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (Connection conn = Util.getMySQLConnection();
             PreparedStatement statement = conn.prepareStatement("delete from users where id=?")) {
            statement.setInt(1, (int) id);
            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();

        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("select * from users")) {
            while (rs.next()) {
                list.add(new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e);
        }

        return list;
    }

    public void cleanUsersTable() throws SQLException {
        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate("truncate table users");
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }
}
