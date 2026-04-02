package net.engineeringdigest.journalApp.Dao.Impl;

import net.engineeringdigest.journalApp.Dao.JournalEntryDAO;
import net.engineeringdigest.journalApp.Dao.UserDAO;
import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.HibernateUtils.HibernateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JournalEntryDaoImpl implements JournalEntryDAO {
    private static final Logger logger = LogManager.getLogger(JournalEntryDaoImpl.class);

    @Autowired
    private HibernateUtils hibernateUtils;

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<JournalEntry> getAlldetails() {
        List<JournalEntry> entries = new ArrayList<>();
        String hql = "From JournalEntry";
        try( Session session = hibernateUtils.getSession()) {
            Query<JournalEntry> query = session.createQuery(hql, JournalEntry.class);
            entries = query.list();
        } catch (Exception e) {
            logger.error("Data Error while getAlldetails {}", e.getMessage());
        }
        return entries;
    }

    @Override
    public JournalEntry getJournalEntryById(Long id) {
        JournalEntry entry = null;
        try( Session session = hibernateUtils.getSession()) {
            String hql = "From JournalEntry where id = :id";
            Query<JournalEntry> query = session.createQuery(hql, JournalEntry.class);
            query.setParameter("id", id);
             entry = query.getSingleResult();
        } catch (Exception e) {
            logger.error("Data Error while getJournalEntryById Id{}",id, e.getMessage());
        }
        return entry;
    }

    @Override
    public String save(JournalEntry entry) {
        Session session = hibernateUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entry);
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
    public JournalEntry saveMenual(JournalEntry entry) {
        Session session = hibernateUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entry);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Data Error while save {}", e.getMessage());
        } finally {
            session.close();
        }
        return entry;
    }

    @Override
    public String delete(Long id) {
        Transaction transaction = null;
        try(Session session = hibernateUtils.getSession()) {
            transaction = session.beginTransaction();
            JournalEntry getRecord = session.get(JournalEntry.class, id);
            if (getRecord != null) {
                session.remove(getRecord);
                transaction.commit();
                return "JournalEntry deleted successfully!";
            } else {
                return "JournalEntry not found!";
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while deleting JournalEntry: {}", e.getMessage());
            return "Error while deleting JournalEntry!";
        }
    }
}
