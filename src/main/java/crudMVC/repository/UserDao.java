package crudMVC.repository;

import crudMVC.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void add(User user);

    User get(long id);

    List<User> getAll();

    void update(User user);

    void delete(long id);
}
