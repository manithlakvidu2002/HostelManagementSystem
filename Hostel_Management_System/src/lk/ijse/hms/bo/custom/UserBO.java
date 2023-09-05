

package lk.ijse.hms.bo.custom;

import lk.ijse.hms.bo.SuperBO;
import lk.ijse.hms.dto.UserDTO;
import lk.ijse.hms.entity.User;

import java.util.List;

public interface UserBO extends SuperBO {
    User getUser(String id);

    String getPassword(String id);

    boolean updateUser_Pw(String newUserName, String newPw);

    boolean saveUser(UserDTO dto);

    List<UserDTO> loadAll();

    boolean updateUser(UserDTO dto);
}
