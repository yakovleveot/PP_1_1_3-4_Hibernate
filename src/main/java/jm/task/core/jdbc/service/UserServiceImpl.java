package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService, UserDao {

    private final UserDao userDao = new UserDaoHibernateImpl();

    public UserServiceImpl() {
    }

    public void createUsersTable() {
        try {
            userDao.createUsersTable();
        } catch (RuntimeException | SQLException ignored) {
        }
    }

    public void dropUsersTable() {
        try {
            userDao.dropUsersTable();
        } catch (RuntimeException | SQLException ignored) {
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) throws SQLException {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        try {
            return userDao.getAllUsers();
        } catch (RuntimeException | SQLException ignored) {
        }
        return Collections.emptyList();
    }

    public void cleanUsersTable() throws SQLException {
        userDao.cleanUsersTable();
    }
}
