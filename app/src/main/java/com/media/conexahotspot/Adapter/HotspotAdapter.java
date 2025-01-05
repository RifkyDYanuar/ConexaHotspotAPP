package com.media.conexahotspot.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public class HotspotAdapter extends RecyclerView.Adapter<HotspotAdapter.HotspotViewHolder> {
    private List<PaketItem> hotspotItems;


    public HotspotAdapter(List<PaketItem> hotspotItems) {
        this.hotspotItems = hotspotItems;
    }

    @NonNull
    @Override
    public HotspotAdapter.HotspotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_paket_hotspot, parent, false);
        return new HotspotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotspotAdapter.HotspotViewHolder holder, int position) {
        PaketItem paketItem = hotspotItems.get(position);
        holder.itemNama.setText(paketItem.getNama_paket());
        holder.itemPInet.setText(paketItem.getP_inet());
        Long Harga = paketItem.getHarga();
        String formatHarga = NumberFormat.getInstance(Locale.getDefault()).format(Harga);
        holder.itemHarga.setText("Rp. " + formatHarga);
        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailPaket.class);
                intent.putExtra("nama_paket", paketItem.getNama_paket());
                intent.putExtra("p_inet", paketItem.getP_inet());
                intent.putExtra("harga", paketItem.getHarga());
//                    intent.putExtra("imageId", item.getImageResId());
                intent.putExtra("biaya_pemasangan", paketItem.getBiaya_pemasangan());
//                List<String> deskripsiList = item.getDeskripsi();
//                if (deskripsiList != null) {
//                    intent.putStringArrayListExtra("deskripsi", new ArrayList<>(deskripsiList));
//                }
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return hotspotItems.size();
    }
    public static class HotspotViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        CardView itemCard;
        TextView itemNama;
        TextView itemPInet;
        TextView itemHarga;
        Button itemButton;
        public HotspotViewHolder(View itemView){
            super(itemView);
            itemNama = itemView.findViewById(R.id.nama_paket);
            itemPInet = itemView.findViewById(R.id.p_inet);
            itemHarga = itemView.findViewById(R.id.harga);
            itemCard = itemView.findViewById(R.id.hotspot);

        }
    }
}
