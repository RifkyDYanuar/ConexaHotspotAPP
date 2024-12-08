package com.media.conexahotspot.Item;

import com.media.conexahotspot.R;

import java.util.ArrayList;
import java.util.List;

public class PaketItem {
    private int imageResId;
    private String nama_paket;
    private String p_inet;
    private Long harga;
    private Long biaya_pemasangan;
    private List<String> deskripsi;

    public PaketItem(){

    }

    public PaketItem(int imageResId, String nama_paket, String p_inet, Long harga, Long biaya_pemasangan, ArrayList<String> deskripsi) {
        this.imageResId = imageResId;
        this.nama_paket = nama_paket;
        this.p_inet = p_inet;
        this.harga = harga;
        this.biaya_pemasangan = biaya_pemasangan;
        this.deskripsi = deskripsi;
    }
    public int getImageResId() {
        return R.drawable.cable;
    }
    public String getNama_paket() {
        return nama_paket;
    }
    public String getP_inet() {
        return p_inet;
    }
    public Long getHarga() {
        return harga;
    }

    public Long getBiaya_pemasangan() {
        return biaya_pemasangan;
    }
    public List<String> getDeskripsi() {
        return deskripsi;
    }
}
