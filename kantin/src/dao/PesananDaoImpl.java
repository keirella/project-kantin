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
        String sql = "INSERT INTO pesanan (id_menu, nama_menu, jumlah, total_harga) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getId_menu());
            stmt.setString(2, p.getNamaMenu());
            stmt.setInt(3, p.getJumlah());
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
                p.setId_menu(rs.getInt("id_menu"));
                p.setNamaMenu(rs.getString("nama_menu"));
                p.setJumlah(rs.getInt("jumlah"));
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
        String sql = "SELECT harga FROM menu WHERE nama = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    harga = rs.getInt("harga");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return harga;
    }

    @Override
    public void updatePesanan(Pesanan p) {
        String sql = "UPDATE pesanan SET nama_menu=?, jumlah=?, total_harga=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNamaMenu());
            ps.setInt(2, p.getJumlah());
            ps.setDouble(3, p.getTotalHarga());
            ps.setInt(4, p.getId());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data berhasil diupdate.");
            } else {
                System.out.println("Data tidak ditemukan untuk diupdate.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal update data: " + e.getMessage());
        }
    }

    @Override
    public void clearPesanan() {
        String sql = "DELETE FROM pesanan"; // hapus semua data
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int rowsDeleted = ps.executeUpdate();
            System.out.println("Semua pesanan berhasil dihapus. Total baris dihapus: " + rowsDeleted);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menghapus semua pesanan: " + e.getMessage());
        }
    }
    
    @Override
    public int getIdMenuByNama(String nama) {
        int id = 0;
        String sql = "SELECT id FROM menu WHERE nama = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
