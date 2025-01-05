package com.media.conexahotspot.Item;


public class TransaksiClass {
    private String nama_paket;
    private Long harga;
    private String tanggal_pemasangan;
    private  String status;

    // Default constructor (diperlukan untuk Firebase)
    public TransaksiClass() {
    }


    // Getters dan Setters
    public String getNama_paket() {
        return nama_paket;
    }

    public void setNama_paket(String nama_paket) {
       this.nama_paket = nama_paket;
    }

    public Long getHarga() {
        return harga;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    public String getTanggal_pemasangan() {
        return tanggal_pemasangan;
    }

    public void setTanggal_pemasangan(String tanggal_pemasangan) {
        this.tanggal_pemasangan = tanggal_pemasangan;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}

