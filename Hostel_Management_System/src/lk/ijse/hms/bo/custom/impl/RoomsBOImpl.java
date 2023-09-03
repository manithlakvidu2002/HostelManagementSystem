

package lk.ijse.hms.bo.custom.impl;

import lk.ijse.hms.bo.custom.RoomsBO;
import lk.ijse.hms.dao.FactoryDAO;
import lk.ijse.hms.dao.custom.RoomsDAO;
import lk.ijse.hms.dao.custom.StudentDAO;
import lk.ijse.hms.dto.RoomsDTO;
import lk.ijse.hms.entity.Room;

import java.util.ArrayList;

public class RoomsBOImpl implements RoomsBO {

    RoomsDAO roomDAO = (RoomsDAO) FactoryDAO.getFactoryDAO().getDAO(FactoryDAO.Types.ROOM);

    @Override
    public Boolean addRoom(RoomsDTO roomsDTO) {
        Room room = new Room(
                roomsDTO.getRoom_type_id(),
                roomsDTO.getType(),
                roomsDTO.getKey_money(),
                roomsDTO.getQty());

        return roomDAO.add(room);
    }

    @Override
    public Boolean deleteRoom(RoomsDTO roomsDTO) {
        return roomDAO.delete(roomsDTO.getRoom_type_id());
    }

    @Override
    public ArrayList<RoomsDTO> getRoomsData() {
        ArrayList<RoomsDTO> RoomDTOs = new ArrayList<>();
        ArrayList<Room> roomData = roomDAO.getData();

        for (Room r : roomData) {
            RoomDTOs.add(new RoomsDTO(
                    r.getRoom_type_id(),
                    r.getType(),
                    r.getKey_money(),
                    r.getQty()));
        }
        return RoomDTOs;
    }

    public String getCurrentID() {
        return roomDAO.getCurrentID();
    }

    @Override
    public Boolean updateRoom(RoomsDTO roomsDTO) {
        Room room = new Room(
                roomsDTO.getRoom_type_id(),
                roomsDTO.getType(),
                roomsDTO.getKey_money(),
                roomsDTO.getQty());

        return roomDAO.update(room);
    }
}
