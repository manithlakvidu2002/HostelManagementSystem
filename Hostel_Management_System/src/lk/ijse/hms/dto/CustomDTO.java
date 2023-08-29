package lk.ijse.hms.dto;

import lk.ijse.hms.entity.Reservation;
import lk.ijse.hms.entity.Room;
import lk.ijse.hms.entity.Student;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomDTO {
    // reservation
    private String res_id;
    private LocalDate res_date;
    private String status;
    private Student student;
    private Room room;

    // Room
    private String room_type_id;
    private String type;
    private String key_money;
    private int qty;
    private List<Reservation> reservationList = new ArrayList<>();

    // Student
    private String id;
    private String name;
    private String address;
    private String contact_no;
    private String dob;
    private String gender;


    public CustomDTO(String res_id, LocalDate res_date, String room_type_id,  String type,String id, String name,String key_money,String status ) {
        this.res_id = res_id;
        this.res_date = res_date;
        this.status = status;
        this.room_type_id = room_type_id;
        this.type = type;
        this.key_money = key_money;
        this.id = id;
        this.name = name;
    }
}
