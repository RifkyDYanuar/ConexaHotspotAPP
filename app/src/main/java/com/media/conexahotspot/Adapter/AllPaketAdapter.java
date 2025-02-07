package com.media.conexahotspot.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.media.conexahotspot.DetailPaket;
import com.media.conexahotspot.Item.PaketItem;
import com.media.conexahotspot.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AllPaketAdapter extends RecyclerView.Adapter<AllPaketAdapter.AllPaketViewHolder> {
    private List<PaketItem> allpaketItems;

    public AllPaketAdapter(List<PaketItem> allpaketItems) {
        this.allpaketItems = allpaketItems;
    }


    @NonNull
    @Override
    public AllPaketAdapter.AllPaketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_paket, parent, false);
        return new AllPaketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPaketAdapter.AllPaketViewHolder holder, int position) {
            int actualPosition = position % allpaketItems.size();
            PaketItem item = allpaketItems.get(actualPosition);
            holder.itemNama.setText(item.getNama_paket());
            holder.itemPInet.setText(item.getP_inet());
            Long harga = item.getHarga();
            String formatHarga = NumberFormat.getInstance(Locale.getDefault()).format(harga);
            holder.itemHarga.setText("Rp. " + formatHarga);

            holder.itemCard.setOnClickListener(new View.OnClickListener() {
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
        return allpaketItems.size();
    }

    public class AllPaketViewHolder extends RecyclerView.ViewHolder {
        TextView itemNama;
        TextView itemPInet;
        TextView itemHarga;
        CardView itemCard;

        public AllPaketViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNama = itemView.findViewById(R.id.nama_paket);
            itemPInet = itemView.findViewById(R.id.p_inet);
            itemHarga = itemView.findViewById(R.id.harga);
            itemCard = itemView.findViewById(R.id.item_card);
        }
    }
}
