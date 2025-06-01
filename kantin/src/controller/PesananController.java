package controller;

import dao.PesananDao;
import dao.PesananDaoImpl;
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
        dao = new dao.PesananDaoImpl(); 
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

            int idMenu = dao.getIdMenuByNama(nama);
            if (idMenu == 0) {
                JOptionPane.showMessageDialog(view, "Menu tidak ditemukan di database.");
                return;
            }

            int total = harga * jumlah;

            Pesanan p = new Pesanan();
            p.setId_menu(idMenu); 
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
    
    public void updatePesanan() {
        PesananDao dao = new PesananDaoImpl();

        try {
            Pesanan p = new Pesanan();
            dao.updatePesanan(p); 
            JOptionPane.showMessageDialog(null, "Pesanan berhasil diupdate!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal update pesanan: " + e.getMessage());
        }
    }
    
    public void clearPesanan() {
        PesananDao dao = new PesananDaoImpl();

        int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus semua pesanan?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dao.clearPesanan();
                JOptionPane.showMessageDialog(null, "Semua pesanan berhasil dihapus!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal menghapus pesanan: " + e.getMessage());
            }
        }
    }

    public void tampilData() {
        List<Pesanan> list = dao.getAllPesanan();
        tableModel.setRowCount(0);

        double totalSemuaHarga = 0;

        for (Pesanan p : list) {
            Object[] row = new Object[]{
                p.getId(),
                p.getNamaMenu(),
                p.getJumlah(),
                p.getHarga(),
                p.getTotalHarga()
            };
            tableModel.addRow(row);

            totalSemuaHarga += p.getTotalHarga();
        }
    }

}