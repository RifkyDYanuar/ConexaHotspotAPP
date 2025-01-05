package com.media.conexahotspot.Item;


public class TransaksiClass {
    private String address;
    private String kodeTransaksi;
    private String name;
    private String notelp;
    private String status;
    private String tanggalPemasangan;
    private String tanggalTransaksi;
    private int total;

    // Constructor tanpa parameter (default)
    public TransaksiClass() {
    }

    // Constructor dengan parameter
    public TransaksiClass(String address, String kodeTransaksi, String name, String notelp,
                       String status, String tanggalPemasangan, String tanggalTransaksi, int total) {
        this.address = address;
        this.kodeTransaksi = kodeTransaksi;
        this.name = name;
        this.notelp = notelp;
        this.status = status;
        this.tanggalPemasangan = tanggalPemasangan;
        this.tanggalTransaksi = tanggalTransaksi;
        this.total = total;
    }

    // Getter dan Setter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggalPemasangan() {
        return tanggalPemasangan;
    }

    public void setTanggalPemasangan(String tanggalPemasangan) {
        this.tanggalPemasangan = tanggalPemasangan;
    }

    public String getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(String tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
