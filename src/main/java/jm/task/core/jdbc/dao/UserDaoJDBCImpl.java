package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getNewConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE user " +
                    "(ID int not null auto_increment, " +
                    " firstName varchar(300) not null, " +
                    " lastName varchar(300) not null, " +
                    " age int not null, " +
                    " PRIMARY KEY (ID))");
            System.out.println("Table created");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Table creation failed");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            System.out.println("Table dropped");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Table drop failed");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (firstName, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User " + name + " has been created");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("User " + name + " could not be created");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User with id:" + id + " has been removed");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("User with id:" + id + " could not be removed");
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }

            users.forEach(user -> System.out.println(user.toString()));

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while getting users");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM user");
            System.out.println("Table cleared");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error cleaning table: " + e.getMessage());
        }
    }
}
