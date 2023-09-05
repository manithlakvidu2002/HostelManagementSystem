

package lk.ijse.hms.bo.custom.impl;

import lk.ijse.hms.bo.custom.UserBO;
import lk.ijse.hms.dao.FactoryDAO;
import lk.ijse.hms.dao.custom.UserDAO;
import lk.ijse.hms.dto.UserDTO;
import lk.ijse.hms.entity.User;
import lk.ijse.hms.util.SessionFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) FactoryDAO.getFactoryDAO().getDAO(FactoryDAO.Types.USER);

    @Override
    public User getUser(String id) {
        return userDAO.getUser(id);
    }

    @Override
    public String getPassword(String id) {
        return userDAO.getPassword(id);
    }

    @Override
    public boolean updateUser_Pw(String newUserName, String newPw) {
        return userDAO.updateUser_Pw(newUserName,newPw);
    }

    @Override
    public boolean saveUser(UserDTO dto) {
       return userDAO.add(new User(dto.getName(),dto.getPassword()));
    }
    @Override
    public List<UserDTO> loadAll() {
        try {
            List<User> stList = userDAO.loadAll();
            List<UserDTO> list = new ArrayList<>();
            for (User user : stList) {
                list.add(new UserDTO(user.getName(),user.getPassword()));
            }
            if (list != null) {
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean updateUser(UserDTO dto) {
        return userDAO.updateUser(new User(dto.getName(),dto.getPassword()));
    }

}