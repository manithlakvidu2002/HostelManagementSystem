package lk.ijse.hms.dao;

import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{
    // facade -	Gathering most common methods into a single interface to improve code re-usability

    public ArrayList<T> getData();

    public boolean delete(String id);

    public boolean add(T entity);

    public boolean update(T entity);

    public String getCurrentID();
}
