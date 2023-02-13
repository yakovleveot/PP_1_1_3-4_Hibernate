package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();
        System.out.println("Таблица создана");
        System.out.println(userDao.getAllUsers().toString());
        System.out.println("-----------------------------------------------");

        userDao.saveUser("Name1", "LastName1", (byte) 20);
        System.out.println("Юзер с именем " + userDao.getAllUsers().get(0).getName() + " добавлен в базу данных");
        userDao.saveUser("Name2", "LastName2", (byte) 25);
        System.out.println("Юзер с именем " + userDao.getAllUsers().get(1).getName() + " добавлен в базу данных");
        userDao.saveUser("Name3", "LastName3", (byte) 31);
        System.out.println("Юзер с именем " + userDao.getAllUsers().get(2).getName() + " добавлен в базу данных");
        userDao.saveUser("Name4", "LastName4", (byte) 38);
        System.out.println("Юзер с именем " + userDao.getAllUsers().get(3).getName() + " добавлен в базу данных");
        System.out.println("-----------------------------------------------");
        System.out.println(userDao.getAllUsers().toString().replaceAll(",", "\n"));
        System.out.println("-----------------------------------------------");

        long id = 3;
        userDao.removeUserById(3);
        System.out.println("Юзер с id: " + id + " удален из базы данных");
        System.out.println(userDao.getAllUsers().toString().replaceAll(",", "\n"));
        System.out.println("-----------------------------------------------");

        userDao.getAllUsers();
        userDao.cleanUsersTable();
        System.out.println("Таблица с юзерами очищена");
        System.out.println(userDao.getAllUsers().toString().replaceAll(",", "\n"));
        System.out.println("-----------------------------------------------");

        userDao.dropUsersTable();
        System.out.println("Таблица с юзерами удалена");
        System.out.println(userDao.getAllUsers().toString().replaceAll(",", "\n"));
        System.out.println("-----------------------------------------------");
    }
}
