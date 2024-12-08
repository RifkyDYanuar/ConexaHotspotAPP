package com.media.conexahotspot.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.media.conexahotspot.DetailPaket;
import com.media.conexahotspot.Item.PaketItem;
import com.media.conexahotspot.R;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaketAdapter extends RecyclerView.Adapter<PaketAdapter.PaketViewHolder> {

        private List<PaketItem> paketItems;
        private DatabaseReference databaseReference;
        private FirebaseAuth auth;

        public PaketAdapter(List<PaketItem> paketItems) {
            this.paketItems = paketItems;


        }

    @NonNull
    @Override
    public PaketAdapter.PaketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paket, parent, false);
        return new PaketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaketAdapter.PaketViewHolder holder, int position) {
            int actualPosition = position % paketItems.size();
            PaketItem item = paketItems.get(actualPosition);
            holder.itemImage.setImageResource(item.getImageResId());
            holder.itemNama.setText(item.getNama_paket());
            holder.itemPInet.setText(item.getP_inet());
            Long harga = item.getHarga();
            String formatHarga = NumberFormat.getInstance(Locale.getDefault()).format(harga);
            holder.itemHarga.setText("Rp. " + formatHarga);
            holder.itemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailPaket.class);
                    intent.putExtra("nama_paket", item.getNama_paket());
                    intent.putExtra("p_inet", item.getP_inet());
                    intent.putExtra("harga", item.getHarga());
//                    intent.putExtra("imageId", item.getImageResId());
                    intent.putExtra("biaya_pemasangan", item.getBiaya_pemasangan());
                    List<String> deskripsiList = item.getDeskripsi();
                    if (deskripsiList != null) {
                        intent.putStringArrayListExtra("deskripsi", new ArrayList<>(deskripsiList));
                    }
                    context.startActivity(intent);
                }
            });
    }
    @Override
    public int getItemCount() {
            return paketItems.size();
    }
    public static class PaketViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemNama;
        TextView itemPInet;
        TextView itemHarga;
        Button itemButton;
        public PaketViewHolder(View itemView){
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemNama = itemView.findViewById(R.id.nama_paket);
            itemPInet = itemView.findViewById(R.id.p_inet);
            itemHarga = itemView.findViewById(R.id.item_harga);
            itemButton = itemView.findViewById(R.id.itemButton);
        }
    }
}
