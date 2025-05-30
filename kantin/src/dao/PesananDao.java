package dao;

import model.Pesanan;
import java.util.List;

public interface PesananDao {
    int getHargaMakanan(String nama);
    void tambahPesanan(Pesanan p);
    void hapusPesanan(int id);
    List<Pesanan> getAllPesanan();
}
