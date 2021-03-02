package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        // Создание таблицы User(ов)
        userService.createUsersTable();

        for (int i = 1; i <= 4; i++) {
            // Добавление 4 User(ов) в таблицу с данными на свой выбор.
            userService.saveUser("name" + i, "lastName" + i, (byte) i);

            // После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
            System.out.println("User с именем – name" + i + " добавлен в базу данных");
        }

        // Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        userService.getAllUsers().forEach(System.out::println);

        // Очистка таблицы User(ов)
        userService.cleanUsersTable();

        // Удаление таблицы
        userService.dropUsersTable();
    }
}
