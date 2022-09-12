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

        Session session = Util.hibernateSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery(query).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.hibernateSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE USERS").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.hibernateSessionFactory().openSession();
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
        System.out.println("User " +name+ " was added!");
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.hibernateSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete User " +
                    "where id = " + id).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        Session session = Util.hibernateSessionFactory().openSession();
        session.beginTransaction();
        users = session.createQuery("from User")
                .getResultList();
        session.getTransaction().commit();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.hibernateSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
    }
}
