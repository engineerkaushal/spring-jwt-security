package net.engineeringdigest.journalApp.Dao.Impl;

import net.engineeringdigest.journalApp.Dao.UserDAO;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.HibernateUtils.HibernateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    @Autowired
    private HibernateUtils hibernateUtils;

    @Override
    public List<Users> getAllUserDetails() {
        List<Users> users = new ArrayList<>();
        Session session = hibernateUtils.getSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        String hql = "From Users";
        try {
            Query<Users> query = session.createQuery(hql, Users.class);
            users = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Data Error while getAlldetails {}", e.getMessage());
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public Users getUserById(Long id) {
        Users users = null;
        try(Session session = hibernateUtils.getSession()) {
            String hql = "From Users where id = :id";
            Query<Users> query = session.createQuery(hql, Users.class);
            query.setParameter("id", id);
            List<Users> usersList = query.list();
            if (!CollectionUtils.isEmpty(usersList)) {
                users = usersList.get(0);
            }
        } catch (Exception e) {
            logger.error("Data Error while getJournalEntryById Id{}",id, e.getMessage());
        }
        return users;
    }

    @Override
    public Users getUserByUserName(String userName) {
        Users users = null;
        try(Session session = hibernateUtils.getSession()) {
            String hql = "From Users where userName = :userName";
            Query<Users> query = session.createQuery(hql, Users.class);
            query.setParameter("userName", userName);
             users = query.getSingleResult();
        } catch (Exception e) {
            logger.error("Data Error while getUserByUserName userName{}",userName, e.getMessage());
        }
        return users;
    }

    @Override
    public String save(Users users) {
        Session session = hibernateUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(users);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Data Error while save {}", e.getMessage());
        } finally {
            session.close();
        }
        return "Data saved successfully !!";
    }

    @Override
    public String deleteUserById(Long id) {
        Transaction transaction = null;
        Session session = hibernateUtils.getSession();
        try {
            transaction = session.beginTransaction();
            Users getRecord = session.get(Users.class, id);
            if (getRecord != null) {
                session.remove(getRecord);
                transaction.commit();
                return "User deleted successfully!";
            } else {
                return "User not found!";
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Data Error while delete {}", e.getMessage());
            return "Error while deleting user!";
        } finally {
            session.close();
        }
    }

    @Override
    public String deleteUserByUserName(Users user) {
        Transaction transaction = null;
        Session session = hibernateUtils.getSession();
        try {
            transaction = session.beginTransaction();
            if (user != null) {
                session.remove(user);
                transaction.commit();
                return "User deleted successfully!";
            } else {
                return "User not found!";
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Data Error while delete {}", e.getMessage());
            return "Error while deleting user!";
        } finally {
            session.close();
        }
    }
}
