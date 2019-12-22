package crudMVC.service;

import crudMVC.model.AuthorizedUser;
import crudMVC.model.User;
import crudMVC.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional
    @Override
    public User getUserById(long id) {
        return userDao.get(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    @Transactional
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userDao.delete(id);
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
