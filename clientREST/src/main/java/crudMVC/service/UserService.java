package crudMVC.service;

import crudMVC.model.User;
import crudMVC.model.UserTo;

import java.util.List;

public interface UserService {
    void add(UserTo userTo);

    User getUserById(long id);

    List<User> getAllUsers();

    void update(UserTo userTo);

    void delete(long id);
}
