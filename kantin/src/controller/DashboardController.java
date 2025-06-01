package controller;

import dao.MenuDao;
import dao.MenuDaoImpl;
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

        view.setTableMenuModel(model); // ini baru!
    }
    
    

}
