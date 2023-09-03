

package lk.ijse.hms.bo.custom;

import lk.ijse.hms.bo.SuperBO;
import lk.ijse.hms.dto.RoomsDTO;

import java.util.ArrayList;

public interface RoomsBO extends SuperBO {
    Boolean addRoom(RoomsDTO roomsDTO);

    Boolean deleteRoom(RoomsDTO roomsDTO);

    ArrayList<RoomsDTO> getRoomsData();

    //String getCurrentID();

    Boolean updateRoom(RoomsDTO roomsDTO);
}
