package crudMVC.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import crudMVC.model.User;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOHibernate implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User get(long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User ", User.class);
        return query.getResultList();
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Object persistentInstance = session.load(User.class, id);
        if (persistentInstance != null) {
            session.delete(persistentInstance);
        }
    }
}
