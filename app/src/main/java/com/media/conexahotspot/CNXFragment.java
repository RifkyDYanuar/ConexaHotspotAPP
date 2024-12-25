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
import com.google.firebase.database.ValueEventListener;
import com.media.conexahotspot.Adapter.AllPaketAdapter;
import com.media.conexahotspot.Item.PaketItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CNXFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CNXFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<PaketItem> allpaketItems;
    FirebaseAuth auth;
    DatabaseReference reference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CNXFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CNXFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CNXFragment newInstance(String param1, String param2) {
        CNXFragment fragment = new CNXFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_n_x, container, false);


        recyclerView = view.findViewById(R.id.all_paket);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false));
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
        return view;
    }
}