package dao;

import dao.MenuDao;
import helper.Koneksi;
import model.Menu;

import java.sql.*;
import java.util.*;

public class MenuDaoImpl implements MenuDao {
    @Override
    public List<Menu> getAll() {
        List<Menu> list = new ArrayList<>();
        try (Connection conn = Koneksi.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM menu");
            while (rs.next()) {
                list.add(new Menu(rs.getInt("id"), rs.getString("nama"), rs.getDouble("harga")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Menu> searchByName(String keyword) {
        List<Menu> list = new ArrayList<>();
        try (Connection conn = Koneksi.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM menu WHERE nama LIKE ?");
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Menu(rs.getInt("id"), rs.getString("nama"), rs.getDouble("harga")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}