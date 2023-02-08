package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() throws SQLException {
    }
    Connection connection = Util.getNewConnection();
    boolean tableExists(Connection connection) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, "users", new String[] {"TABLE"});
        return resultSet.next();
    }

    public void createUsersTable() throws SQLException {
        if (!this.tableExists(connection)) {
            String createTable = "CREATE TABLE users (" +
                    "    id INT PRIMARY KEY AUTO_INCREMENT," +
                    "    name VARCHAR(45)," +
                    "    lastName VARCHAR(45)," +
                    "    age INT" +
                    ");";

            try (Statement statement = connection.createStatement()) {
                statement.execute(createTable);
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        if (this.tableExists(connection)) {
            String dropTable = "DROP TABLE users;";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(dropTable);
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String saveUser = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(saveUser)) {
            // Определяем значения переменных
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }


    public void removeUserById(long id) throws SQLException {
        String removeUserById = "DELETE FROM users WHERE id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(removeUserById)) {
            // Определяем значения переменных
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String getAllUsers = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        if (this.tableExists(connection)) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(getAllUsers);
                connection.commit();

                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getNString("name");
                    String lastName = resultSet.getNString("lastName");
                    byte age = resultSet.getByte("age");
                    User user = new User(name, lastName, age);
                    user.setId(id);
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return users;

        } else {
            return Collections.emptyList();
        }
    }

    public void cleanUsersTable() throws SQLException {
        String cleanUsersTable = "DELETE FROM users;";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanUsersTable);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
}
