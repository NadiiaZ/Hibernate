package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final String tableName = "users";
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE " + tableName +
            " (id BIGINT NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(45) NULL, " +
            "lastName VARCHAR(45) NULL, " +
            "age TINYINT NULL, " +
            "PRIMARY KEY (id))";
        try (Session session = Util.hibernateSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(query).executeUpdate();
            session.getTransaction().commit();
        //} catch (SQLSyntaxErrorException e) {
         //   System.out.println("Table with this name has already deleted.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.hibernateSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE USERS").executeUpdate();
            session.getTransaction().commit();
        //} catch (SQLSyntaxErrorException e) {
          //  System.out.println("Table with this name has already deleted.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.hibernateSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User " +name+ " was added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.hibernateSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete User " +
                    "where id = " + id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        try(Session session = Util.hibernateSessionFactory().openSession()) {
            session.beginTransaction();
            users = session.createQuery("from User")
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.hibernateSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
