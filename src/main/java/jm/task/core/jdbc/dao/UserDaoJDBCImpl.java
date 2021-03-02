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
    >>> но в методах интерфейса UserDao не объявлен выброс исключений, его допустимо менять?
    2. Где закрытие соединений в методах дао ?
    >>> используется try-with-resource, соединение объявлено в блоке ресурсов -> автоматически закрывается
    3. Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
    >>> done
    4. Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    >>> done
     */

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate("create table if not exists users(" +
                    "id int auto_increment, " +
                    "name varchar(100) not null, " +
                    "lastName varchar(100) not null, " +
                    "age int not null, " +
                    "constraint users_pk primary key (id))");

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Невозможно создать таблицу! Message: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate("drop table if exists users");

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Невозможно удалить таблицу! Message: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getMySQLConnection();
             PreparedStatement statement = conn.prepareStatement("insert into users(name, lastName, age) values(?, ?, ?)")) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Невозможно удалить таблицу! Message: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getMySQLConnection();
             PreparedStatement statement = conn.prepareStatement("delete from users where id=?")) {

            statement.setInt(1, (int) id);
            statement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Невозможно удалить запись! Message: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()) {

            ResultSet rs = statement.executeQuery("select * from users");
            while (rs.next()) {
                list.add(new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age")));
            }
            rs.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Невозможно получить записи! Message: " + e.getMessage());
        }

        return list;
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate("truncate table if exists users");

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Невозможно очистить таблицу! Message: " + e.getMessage());
        }
    }
}
