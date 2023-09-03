

package lk.ijse.hms.bo.custom.impl;

import lk.ijse.hms.bo.custom.ReservationBO;
import lk.ijse.hms.dao.FactoryDAO;
import lk.ijse.hms.dao.custom.QueryDAO;
import lk.ijse.hms.dao.custom.ReservationDAO;
import lk.ijse.hms.dao.custom.RoomsDAO;
import lk.ijse.hms.dao.custom.StudentDAO;
import lk.ijse.hms.dto.CustomDTO;
import lk.ijse.hms.dto.ReservationDTO;
import lk.ijse.hms.dto.RoomsDTO;
import lk.ijse.hms.dto.StudentDTO;
import lk.ijse.hms.entity.CustomEntity;
import lk.ijse.hms.entity.Reservation;
import lk.ijse.hms.entity.Room;
import lk.ijse.hms.entity.Student;

import java.util.ArrayList;


public class ReservationBOImpl implements ReservationBO {
    ReservationDAO reservationDAO = (ReservationDAO) FactoryDAO.getFactoryDAO().getDAO(FactoryDAO.Types.RECEPTION);
    RoomsDAO roomDAO = (RoomsDAO) FactoryDAO.getFactoryDAO().getDAO(FactoryDAO.Types.ROOM);
    StudentDAO studentDAO = (StudentDAO) FactoryDAO.getFactoryDAO().getDAO(FactoryDAO.Types.STUDENT);
    QueryDAO queryDAO = (QueryDAO) FactoryDAO.getFactoryDAO().getDAO(FactoryDAO.Types.JOIN_QUERY);

    @Override
    public String getCurrentID() {
        return reservationDAO.getCurrentID();
    }

    @Override
    public ArrayList<RoomsDTO> getRoomsData() {
        ArrayList<RoomsDTO> roomDTOs = new ArrayList<>();
        ArrayList<Room> roomData = roomDAO.getData();

        for (Room r : roomData) {
            roomDTOs.add(new RoomsDTO(
                    r.getRoom_type_id(),
                    r.getType(),
                    r.getKey_money(),
                    r.getQty()));
        }
        return roomDTOs;
    }

    public ArrayList<StudentDTO> getStudentData() {
        ArrayList<StudentDTO> studentDTOs = new ArrayList<>();
        ArrayList<Student> studentData = studentDAO.getData();

        for (Student std : studentData) {
            studentDTOs.add(new StudentDTO(std.getId(),
                    std.getName(), std.getAddress(),
                    std.getContact_no(),
                    std.getDob(),
                    std.getGender()));
        }
        return studentDTOs;
    }

    @Override
    public ArrayList<CustomDTO> getReservationData() {
        ArrayList<CustomDTO> customDTOS = new ArrayList<>();
        ArrayList<CustomEntity> customEntities = queryDAO.getData();

        for (CustomEntity c : customEntities) {
            customDTOS.add(new CustomDTO(
                    c.getRes_id(),
                    c.getRes_date(),
                    c.getRoom_type_id(),
                    c.getType(),
                    c.getId(),
                    c.getName(),
                    c.getKey_money(),
                    c.getStatus()));
        }
        return customDTOS;
    }

    @Override
    public boolean addReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();

        reservation.setRes_id(reservationDTO.getRes_id());
        reservation.setRes_date(reservationDTO.getRes_date().toString());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setRoom(reservationDTO.getRoom());
        reservation.setStudent(reservationDTO.getStudent());

        return reservationDAO.add(reservation);
    }

    @Override
    public boolean deleteReservation(ReservationDTO reservationDTO) {
        return reservationDAO.delete(reservationDTO.getRes_id());
    }

    @Override
    public boolean updateReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();

        reservation.setRes_id(reservationDTO.getRes_id());
        reservation.setRes_date(reservationDTO.getRes_date().toString());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setRoom(reservationDTO.getRoom());
        reservation.setStudent(reservationDTO.getStudent());

        return reservationDAO.update(reservation);
    }
}
