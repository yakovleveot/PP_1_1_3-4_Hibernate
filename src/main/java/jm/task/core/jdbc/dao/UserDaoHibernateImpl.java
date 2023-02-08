package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getNewSessionFactory();
    private Transaction transaction = null;

    public boolean tableExists(SessionFactory sessionFactory) {
        String showTables = "SHOW TABLES";
        String answer = "";
        String users = "[users]";
        StringBuilder sb = new StringBuilder(answer);
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            answer = sb.append(session.createSQLQuery(showTables).list()).toString();
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return users.equals(answer);
    }

    @Override
    public void createUsersTable() throws RuntimeException {
        if (!tableExists(sessionFactory)) {
            String createTable = "CREATE TABLE users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(45)," +
                    " lastName VARCHAR(45)," +
                    " age INT" +
                    ");";

            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery(createTable).executeUpdate();
                transaction.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        if (tableExists(sessionFactory)) {
            String dropTable = "DROP TABLE users;";

            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery(dropTable).executeUpdate();
                transaction.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

        } catch (RuntimeException e) {
            e.printStackTrace();
            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        if (tableExists(sessionFactory)) {
            Transaction transaction;

            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                User user = session.load(User.class, id);
                session.delete(user);
                transaction.commit();

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        if (tableExists(sessionFactory)) {
            Transaction transaction;
            List<User> users = new ArrayList<>();

            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                users = (List<User>) session.createCriteria(User.class).list();
                transaction.commit();

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return users;
        }
        return Collections.emptyList();
    }

    @Override
    public void cleanUsersTable() {
        if (tableExists(sessionFactory)) {
            String dropTable = "DELETE FROM users;";

            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery(dropTable).executeUpdate();
                transaction.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
