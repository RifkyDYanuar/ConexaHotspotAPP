package com.media.conexahotspot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.media.conexahotspot.Adapter.AllPaketAdapter;
import com.media.conexahotspot.Item.PaketItem;

import java.util.ArrayList;
import java.util.List;


public class HotspotFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference reference;
    FirebaseAuth auth;
    List<PaketItem> allpaketItems;
    AllPaketAdapter adapter;

    public HotspotFragment() {
        // Required empty public constructor
    }

    public static HotspotFragment newInstance() {
        HotspotFragment fragment = new HotspotFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotspot, container, false);
        recyclerView = view.findViewById(R.id.all_paket);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false));
        allpaketItems = new ArrayList<>();
        adapter = new AllPaketAdapter(allpaketItems);
        recyclerView.setAdapter(adapter);
        reference = FirebaseDatabase.getInstance().getReference("paket_item");
        auth = FirebaseAuth.getInstance();
        Query  query = reference.orderByKey().startAt("PKH0001").endAt("PKH0004");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    allpaketItems.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        PaketItem paketItem = dataSnapshot.getValue(PaketItem.class);
                        if (paketItem != null) {
                            allpaketItems.add(paketItem);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}