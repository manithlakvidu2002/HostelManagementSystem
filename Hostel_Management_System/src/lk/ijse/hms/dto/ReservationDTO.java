

package lk.ijse.hms.dto;

import lk.ijse.hms.entity.Room;
import lk.ijse.hms.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String res_id;
    private LocalDate res_date;
    private String status;

    private Student student;
    private Room room;
}
