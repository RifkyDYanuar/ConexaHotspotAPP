package com.media.conexahotspot.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.media.conexahotspot.Item.TransaksiClass;
import com.media.conexahotspot.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<TransaksiClass> transaksiClassList;
    public HistoryAdapter(List<TransaksiClass> transaksiClassList){
        this.transaksiClassList = transaksiClassList;
    }
    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_transaksi, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        TransaksiClass transaksiClass = transaksiClassList.get(position);
        holder.NamaPaket.setText(transaksiClass.getNama_paket());
        Long Harga = transaksiClass.getHarga();
        String formatHarga = NumberFormat.getInstance(Locale.getDefault()).format(Harga);
        holder.Harga.setText("Rp. " + formatHarga);
        holder.Status.setText(transaksiClass.getStatus());
        holder.TanggalPemasangan.setText(transaksiClass.getTanggal_pemasangan());
    }

    @Override
    public int getItemCount() {
        return transaksiClassList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView NamaPaket;
        TextView Harga;
        Button Status;
        TextView TanggalPemasangan;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            NamaPaket = itemView.findViewById(R.id.nama_paket);
            Harga = itemView.findViewById(R.id.harga);
            Status = itemView.findViewById(R.id.status);
            TanggalPemasangan = itemView.findViewById(R.id.tanggal_pemasangan);
        }
    }
}
