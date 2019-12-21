package crudMVC.service;

import crudMVC.model.User;
import crudMVC.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void add(User user);

    User getUserById(long id);

    List<User> getAllUsers();

    void update(User user);

    void delete(long id);
}
