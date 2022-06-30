package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        User user1 = new User("Sergey", "Popov", (byte)35);
        User user2 = new User("Rick", "Sanchez", (byte)65);
        User user3 = new User("Morty", "Smite", (byte)14);
        User user4 = new User("Gena", "The Crocodile", (byte)30);

        UserService userService = new UserServiceImpl();
        try {
            userService.createUsersTable();

            userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
            System.out.println("User с именем – " + user1.getName() + " добавлен в базу данных");
            userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
            System.out.println("User с именем – " + user2.getName() + " добавлен в базу данных");
            userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
            System.out.println("User с именем – " + user3.getName() + " добавлен в базу данных");
            userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
            System.out.println("User с именем – " + user4.getName() + " добавлен в базу данных");

            for (User user : userService.getAllUsers()) {
                System.out.println(user.toString());
            }
            userService.cleanUsersTable();
            userService.dropUsersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
