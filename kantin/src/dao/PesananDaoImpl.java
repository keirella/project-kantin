package dao;

import helper.Koneksi;
import model.Pesanan;
import java.sql.*;
import java.util.*;

public class PesananDaoImpl implements PesananDao {
    private Connection conn;

    public PesananDaoImpl() {
        conn = Koneksi.getConnection();
    }

    @Override
    public void tambahPesanan(Pesanan p) {
        String sql = "INSERT INTO pesanan (nama_menu, jumlah, harga, total_harga) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNamaMenu());
            stmt.setInt(2, p.getJumlah());
            stmt.setDouble(3, p.getHarga());
            stmt.setDouble(4, p.getTotalHarga());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapusPesanan(int id) {
        String sql = "DELETE FROM pesanan WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pesanan> getAllPesanan() {
        List<Pesanan> list = new ArrayList<>();
        String sql = "SELECT * FROM pesanan";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pesanan p = new Pesanan();
                p.setId(rs.getInt("id"));
                p.setNamaMenu(rs.getString("nama_menu"));
                p.setJumlah(rs.getInt("jumlah"));
                p.setHarga(rs.getDouble("harga"));
                p.setTotalHarga(rs.getDouble("total_harga"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public int getHargaMakanan(String nama) {
        int harga = 0;
        try {
            String sql = "SELECT harga FROM menu WHERE nama = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                harga = rs.getInt("harga");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return harga;
    }

}
