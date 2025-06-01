package dao;

import dao.UserDao;
import helper.Koneksi;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean login(User user) {
        try (Connection conn = Koneksi.getConnection()) {
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean insertUser(User user) {
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        try (Connection con = Koneksi.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}