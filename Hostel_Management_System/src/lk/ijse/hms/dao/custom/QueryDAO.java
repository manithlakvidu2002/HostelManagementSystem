

package lk.ijse.hms.dao.custom;

import lk.ijse.hms.dao.SuperDAO;
import lk.ijse.hms.entity.CustomEntity;

import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomEntity> getData();
}
