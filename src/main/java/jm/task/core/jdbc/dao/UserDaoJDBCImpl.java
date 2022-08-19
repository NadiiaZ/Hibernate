package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Statement statement;
    private final String tableName = "users";
    public UserDaoJDBCImpl() {
    }
    public void createUsersTable() {
        String query = "CREATE TABLE " +
                tableName +
                " (id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NULL, " +
                "lastName VARCHAR(45) NULL, " +
                "age TINYINT NULL, " +
                "PRIMARY KEY (id))";

        try (Connection connection = Util.dataBaseConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Table with this name already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE " + tableName;
        try (Connection connection = Util.dataBaseConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Table with this name has already deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO " + tableName + " (name, lastName, age)" +
                " VALUES ('" + name + "', '" + lastName + "'," + age + ")";
        try (Connection connection = Util.dataBaseConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);

            System.out.println("User " +name+ " was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM " + tableName +
                " WHERE id = " + id;

        try (Connection connection = Util.dataBaseConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM " + tableName;
        List<User> users = new ArrayList<>();
        User user;

        try (Connection connection = Util.dataBaseConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM " + tableName;

        try (Connection connection = Util.dataBaseConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
