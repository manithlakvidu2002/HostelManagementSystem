
package lk.ijse.hms.dao.custom.impl;

import lk.ijse.hms.dao.custom.ReservationDAO;
import lk.ijse.hms.entity.Reservation;
import lk.ijse.hms.entity.Room;
import lk.ijse.hms.entity.Student;
import lk.ijse.hms.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public ArrayList<Reservation> getData() {
        return null;
    }

    @Override
    public boolean delete(String id) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Reservation reservation = session.load(Reservation.class, id);

            // +1 room to available room count
            Room room = reservation.getRoom();
            int qty = room.getQty();
            room.setQty(qty+1);

            session.update(room);
            session.delete(reservation);

            transaction.commit();
            session.close();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public boolean add(Reservation entity) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            // get room entity object from reservation entity
            Room room = entity.getRoom();
            Student student = entity.getStudent();

            // add entity objects to Reservation list
            room.getReservationList().add(entity);
            student.getReservationList().add(entity);

            // -1 room from available room count
            int qty = room.getQty();
            room.setQty(qty-1);

            session.saveOrUpdate(entity);
            session.saveOrUpdate(entity.getRoom().getRoom_type_id(), room);
            session.saveOrUpdate(entity.getStudent().getId(), student);

            transaction.commit();
            session.close();
            return true;

        } catch (Exception ex) {
            transaction.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public boolean update(Reservation entity) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Room room = entity.getRoom();
            Student student = entity.getStudent();

            room.getReservationList().add(entity);
            student.getReservationList().add(entity);

            session.saveOrUpdate(entity);
            session.saveOrUpdate(entity.getRoom().getRoom_type_id(), room);
            session.saveOrUpdate(entity.getStudent().getId(), student);

            transaction.commit();
            session.close();
            return true;

        } catch (Exception ex) {
            transaction.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public String getCurrentID() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            String sql = "SELECT * FROM reservation ORDER BY CAST(SUBSTRING(res_id, 3) AS UNSIGNED) DESC LIMIT 1";
            NativeQuery sqlQuery = session.createSQLQuery(sql);

            sqlQuery.addEntity(Reservation.class);

            List<Reservation> reservationList = sqlQuery.list();
            String resID = null;
            for(Reservation reservation : reservationList){
                resID = reservation.getRes_id();
            }
            transaction.commit();
            session.close();
            return resID;

        }catch (Exception e){
            transaction.commit();
            session.close();
            return null;
        }
    }
}
