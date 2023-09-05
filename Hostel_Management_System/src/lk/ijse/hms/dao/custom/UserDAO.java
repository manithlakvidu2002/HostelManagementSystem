
package lk.ijse.hms.dao.custom;

import lk.ijse.hms.dao.CrudDAO;
import lk.ijse.hms.dto.UserDTO;
import lk.ijse.hms.entity.User;

import java.util.List;

public interface UserDAO extends CrudDAO<User> {
    User getUser(String id);

    String getPassword(String id);

    boolean updateUser_Pw(String newUserName, String newPw);

    List<User> loadAll();

    boolean updateUser(User user);
}
