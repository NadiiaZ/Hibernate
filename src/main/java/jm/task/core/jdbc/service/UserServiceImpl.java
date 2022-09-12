package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDaoDB;

    public UserServiceImpl()
    {
       // userDaoHibernate = new UserDaoJDBCImpl();
        userDaoDB = new UserDaoHibernateImpl();

    }
    public void createUsersTable() {
        userDaoDB.createUsersTable();
    }

    public void dropUsersTable() {
        userDaoDB.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDaoDB.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDaoDB.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDaoDB.getAllUsers();
    }

    public void cleanUsersTable() {
        userDaoDB.cleanUsersTable();
    }
}
