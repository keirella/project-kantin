package model;

public class Pesanan {
    private int id;
    private int id_menu;
    private String nama_menu;
    private int jumlah;
    private double harga;
    private double total_harga;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }
    

    public String getNamaMenu() {
        return nama_menu;
    }

    public void setNamaMenu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public double getTotalHarga() {
        return total_harga;
    }

    public void setTotalHarga(double total_harga) {
        this.total_harga = total_harga;
    }
}
