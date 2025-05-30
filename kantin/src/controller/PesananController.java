package controller;

import dao.PesananDao;
import model.Pesanan;
import view.PesananViews;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PesananController {

    private PesananViews view;
    private PesananDao dao;
    private DefaultTableModel tableModel;


    public PesananController(PesananViews view) {
        this.view = view;
        dao = new dao.PesananDaoImpl(); // pastikan import benar
        tableModel = (DefaultTableModel) view.getTablePesanan().getModel();
        tampilData();
    }

    public void clearForm() {
        view.getjText_Makanan().setText("");
        view.getjText_Jumlah().setText("");
    }

    public void tambahPesanan() {
        try {
            String nama = view.getjText_Makanan().getText().trim();
            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Nama makanan tidak boleh kosong.");
                return;
            }

            int jumlah = Integer.parseInt(view.getjText_Jumlah().getText());
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(view, "Jumlah harus lebih dari 0.");
                return;
            }

            int harga = dao.getHargaMakanan(nama);
            if (harga == 0) {
                JOptionPane.showMessageDialog(view, "Menu \"" + nama + "\" tidak ditemukan di database.");
                return;
            }

            int total = harga * jumlah;

            Pesanan p = new Pesanan();
            p.setNamaMenu(nama);
            p.setJumlah(jumlah);
            p.setHarga(harga);
            p.setTotalHarga(total);

            dao.tambahPesanan(p);
            JOptionPane.showMessageDialog(view, "Pesanan berhasil ditambahkan!");
            tampilData();
            clearForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Jumlah harus berupa angka.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Terjadi kesalahan: " + e.getMessage());
        }
    }

    public void hapusPesanan() {
        int selectedRow = view.getTablePesanan().getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            dao.hapusPesanan(id);
            JOptionPane.showMessageDialog(view, "Pesanan berhasil dihapus!");
            tampilData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(view, "Pilih data yang ingin dihapus.");
        }
    }

    public void tampilData() {
        List<Pesanan> list = dao.getAllPesanan();
        tableModel.setRowCount(0);
        for (Pesanan p : list) {
            Object[] row = new Object[]{
                p.getId(),
                p.getNamaMenu(),
                p.getJumlah(),
                p.getHarga(),
                p.getTotalHarga()
            };
            tableModel.addRow(row);
        }
    }
}