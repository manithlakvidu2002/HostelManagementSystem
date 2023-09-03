

package lk.ijse.hms.bo.custom;

import lk.ijse.hms.bo.SuperBO;

public interface UserBO extends SuperBO {
    String getUser(String id);

    String getPassword(String id);

    boolean updateUser_Pw(String newUserName, String newPw);
}
