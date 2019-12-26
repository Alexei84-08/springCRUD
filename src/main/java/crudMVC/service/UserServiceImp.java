package crudMVC.service;

import crudMVC.model.AuthorizedUser;
import crudMVC.model.User;
import crudMVC.repository.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void add(User user) {
        userDao.save(user);
    }

    @Transactional
    @Override
    public User getUserById(long id) {
        return userDao.getById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Transactional
    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userDao.deleteById(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userDao.getUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return new AuthorizedUser(user);
    }
}
