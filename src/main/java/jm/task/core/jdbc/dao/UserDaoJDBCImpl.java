package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        try {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                String sql = "CREATE TABLE if not exists `users` ( `id` bigint NOT NULL AUTO_INCREMENT," +
                        "`name` varchar(255) NOT NULL," +
                        "`lastName` varchar(255) NOT NULL," +
                        "`age` smallint NOT NULL, PRIMARY KEY (`id`))";
                statement.executeUpdate(sql);

            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void dropUsersTable() {
        try {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP table IF EXISTS users");
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                String format = "INSERT INTO users (name, lastname, age) values ('%s', '%s', (%d))";
                String sql = String.format(format, name, lastName, age);
                statement.executeUpdate(sql);
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void removeUserById(long id) {
        try {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                String sql = "DELETE FROM users WHERE id = " + id + " LIMIT 1";
                statement.executeUpdate(sql);
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> resultList = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("select name, lastName, age from users");
                while (resultSet.next()) {
                    resultList.add(new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age")));
                }
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return resultList;
    }

    public void cleanUsersTable() {
        try {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                String sql = "TRUNCATE table users";
                statement.executeUpdate(sql);
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
