package com.media.conexahotspot;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.media.conexahotspot.Adapter.AllPaketAdapter;
import com.media.conexahotspot.Item.PaketItem;

import java.util.ArrayList;
import java.util.List;

public class ConexaPaket extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PaketItem> allpaketItems;
    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conexa_paket);

        recyclerView = findViewById(R.id.all_paket);
        recyclerView.setLayoutManager(new LinearLayoutManager(ConexaPaket.this, LinearLayoutManager.VERTICAL,false));
        allpaketItems = new ArrayList<>();
        AllPaketAdapter adapter = new AllPaketAdapter(allpaketItems);
        recyclerView.setAdapter(adapter);
        reference = FirebaseDatabase.getInstance().getReference("paket_item");
        auth = FirebaseAuth.getInstance();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allpaketItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PaketItem paketItem = dataSnapshot.getValue(PaketItem.class);
                    if (paketItem != null) {
                        allpaketItems.add(paketItem);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}