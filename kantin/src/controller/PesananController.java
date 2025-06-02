package controller;

import dao.PesananDao;
import dao.PesananDaoImpl;
import model.Pesanan;
import view.PesananViews;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PesananController {
    private PesananViews view;
    private PesananDao dao;

    public PesananController(PesananViews view) {
        this.view = view;
        this.dao = new PesananDaoImpl();
    }

    public void tambahPesanan() {
        String nama = view.getJText_Makanan().getText();
        String jumlahStr = view.getJText_Jumlah().getText();

        if (nama.isEmpty() || jumlahStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama menu dan jumlah harus diisi!");
            return;
        }

        try {
            int jumlah = Integer.parseInt(jumlahStr);
            int harga = dao.getHargaMakanan(nama);
            int total = harga * jumlah;
            int idMenu = dao.getIdMenuByNama(nama);

            Pesanan p = new Pesanan();
            p.setId_menu(idMenu);
            p.setNamaMenu(nama);
            p.setJumlah(jumlah);
            p.setTotalHarga(total);

            dao.tambahPesanan(p);
            tampilData();
            clearPesanan();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Jumlah harus berupa angka!");
        }
    }

    public void updatePesanan() {
        int row = view.getJTable_Pesanan().getSelectedRow();
        if (row >= 0) {
            String nama = view.getJText_Makanan().getText();
            String jumlahStr = view.getJText_Jumlah().getText();

            if (nama.isEmpty() || jumlahStr.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Nama menu dan jumlah harus diisi!");
                return;
            }

            try {
                int jumlah = Integer.parseInt(jumlahStr);
                int harga = dao.getHargaMakanan(nama);
                int total = harga * jumlah;

                Pesanan p = new Pesanan();
                p.setNamaMenu(nama);
                p.setJumlah(jumlah);
                p.setTotalHarga(total);

                int idPesanan = (int) view.getJTable_Pesanan().getValueAt(row, 0);
                p.setId(idPesanan);

                int idMenu = dao.getIdMenuByNama(nama);
                p.setId_menu(idMenu);

                dao.updatePesanan(p);
                tampilData();
                clearPesanan();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Jumlah harus berupa angka!");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Pilih data yang akan diubah!");
        }
    }

    public void hapusPesanan() {
        int row = view.getJTable_Pesanan().getSelectedRow();
        if (row >= 0) {
            int idPesanan = (int) view.getJTable_Pesanan().getValueAt(row, 0);
            dao.hapusPesanan(idPesanan);
            tampilData();
            clearPesanan();
        } else {
            JOptionPane.showMessageDialog(view, "Pilih data yang akan dihapus!");
        }
    }

    public void tampilData() {
        DefaultTableModel model = (DefaultTableModel) view.getJTable_Pesanan().getModel();
        model.setRowCount(0); // clear tabel

        for (Pesanan p : dao.getAllPesanan()) {
            Object[] row = {
                p.getId(),
                p.getNamaMenu(),
                p.getJumlah(),
                p.getHarga(),
                p.getTotalHarga()
            };
            model.addRow(row);
        }
    }

    public void clearPesanan() {
        view.getJText_Makanan().setText("");
        view.getJText_Jumlah().setText("");
        view.getJText_Makanan().requestFocus();
    }

    public void clearForm() {
        clearPesanan();
        view.getJTable_Pesanan().clearSelection();
    }
}
