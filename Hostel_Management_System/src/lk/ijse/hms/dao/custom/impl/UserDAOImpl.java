

package lk.ijse.hms.dao.custom.impl;

import lk.ijse.hms.dao.custom.UserDAO;
import lk.ijse.hms.dto.UserDTO;
import lk.ijse.hms.entity.User;
import lk.ijse.hms.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getData() {
        return null;
    }

    @Override
    public boolean delete(String id) {
       return false;
    }

    @Override
    public boolean add(User user) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            String use = String.valueOf(session.save(user));
            transaction.commit();
            session.close();
            if(use !=  null){
                return true;
            }
            return false;

        }catch (Exception ex){
            transaction.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            String use = String.valueOf(session.save(user));
            transaction.commit();
            session.close();
            if(use !=  null){
                return true;
            }
            return false;

        }catch (Exception ex){
            transaction.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public String getCurrentID() {
        return null;
    }

    @Override
    public User getUser(String id) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.get(User.class, id);
            transaction.commit();
            session.close();
            return user;

        }catch (Exception ex){
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public String getPassword(String id) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.get(User.class, id);
            transaction.commit();
            session.close();
            return user.getPassword();

        }catch (Exception ex){
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public boolean updateUser_Pw(String newUserName, String newPw) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.get(User.class, "1");
            user.setName(newUserName);
            user.setPassword(newPw);
            session.update(user);

            transaction.commit();
            session.close();
            return true;

        }catch (Exception ex){
            transaction.rollback();
            session.close();
            return false;
        }
    }
    @Override
    public List<User> loadAll() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT C FROM User AS C";
        Query query = session.createQuery(sql);
        List list = query.list();
        session.close();
        return list;
    }

    @Override
    public boolean updateUser(User user) {
        Session session=SessionFactoryConfig.getInstance ().getSession ();
        Transaction transaction=session.beginTransaction ();
        try {
            session.update (user);
            transaction.commit ();
            session.close ();
            return true;
        }catch (Exception e){
            transaction.rollback ();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

}
