package controller;

import dao.MenuDao;
import dao.MenuDaoImpl;
import helper.Koneksi;
import model.Menu;
import view.DashboardViews;
import view.PesananViews;

import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DashboardController {
    private DashboardViews view;
    private MenuDao menuDao;

    public DashboardController(DashboardViews view) {
        this.view = view;
        this.menuDao = new MenuDaoImpl();
        initController();
        loadAllMenu();
    }

    private void initController() {
        view.getBtnCari().addActionListener(e -> searchMenu());
        view.getBtnPesan().addActionListener(e -> {
            PesananViews pesananView = new PesananViews();
            new PesananController(pesananView);
            pesananView.setVisible(true);
            view.dispose();
        });
        view.getjButton_TampilkanMenu().addActionListener(e -> TampilkanMenu());
    }

    private void loadAllMenu() {
        List<Menu> menuList = menuDao.getAll();
        updateTable(menuList);
    }

    private void searchMenu() {
        String keyword = view.getTxtCari();
        List<Menu> menuList = menuDao.searchByName(keyword);
        if (menuList.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Menu tidak ditemukan");
        } else {
            updateTable(menuList);
        }
    }

    private void updateTable(List<Menu> menuList) {
        String[] kolom = {"ID", "Nama", "Harga"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);

        for (Menu m : menuList) {
            Object[] row = {m.getId(), m.getNama(), m.getHarga()};
            model.addRow(row);
        }

        view.setTableMenuModel(model); 
        sembunyikanKolomId();
    }
    
    private void sembunyikanKolomId() {
        view.getTableMenu().getColumnModel().getColumn(0).setMinWidth(0);
        view.getTableMenu().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getTableMenu().getColumnModel().getColumn(0).setWidth(0);
    }


    private void TampilkanMenu() {
        try {
            // Clear text search bar
            view.clearSearchBar();

            // Query ambil semua data menu
            Connection con = Koneksi.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM menu");  

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nama");
            model.addColumn("Harga");

            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getInt("id"); 
                row[1] = rs.getString("nama");
                row[2] = rs.getInt("harga");
                model.addRow(row);
            }

            view.setTableMenuModel(model);
            sembunyikanKolomId();

            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Gagal memuat data menu: " + ex.getMessage());
        }
    }

}
