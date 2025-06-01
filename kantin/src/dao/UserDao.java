package dao;

import model.User;

public interface UserDao {
    boolean login(User user);
    public boolean insertUser(User user);
}