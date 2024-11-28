package com.media.conexahotspot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.media.conexahotspot.Item.PaketItem;
import com.media.conexahotspot.R;

import java.util.List;

public class PaketAdapter extends RecyclerView.Adapter<PaketAdapter.PaketViewHolder> {

        private List<PaketItem> paketItems;

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
            holder.itemTitle.setText(item.getTitle());
            holder.itemDescription.setText(item.getDescription());
            holder.itemHarga.setText(item.getPrice());

            holder.itemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
    }

    @Override
    public int getItemCount() {
            return paketItems.size();
    }
    public static class PaketViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemDescription;
        TextView itemHarga;
        Button itemButton;

        public PaketViewHolder(View itemView){

            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemHarga = itemView.findViewById(R.id.itemHarga);
            itemButton = itemView.findViewById(R.id.itemButton);
        }

    }
}
