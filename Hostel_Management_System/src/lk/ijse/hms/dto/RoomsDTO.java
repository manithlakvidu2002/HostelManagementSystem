

package lk.ijse.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomsDTO {
    private String room_type_id;
    private String type;
    private String key_money;
    private int qty;
}
