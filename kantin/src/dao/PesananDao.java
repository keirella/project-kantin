package dao;

import model.Pesanan;
import java.util.List;

public interface PesananDao {
    int getHargaMakanan(String nama);
    int getIdMenuByNama(String nama);
    void tambahPesanan(Pesanan p);
    void hapusPesanan(int id);
    void updatePesanan(Pesanan p); 
    void clearPesanan();  
    List<Pesanan> getAllPesanan();
}
