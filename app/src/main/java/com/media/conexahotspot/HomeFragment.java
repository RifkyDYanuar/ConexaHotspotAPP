package com.media.conexahotspot;

import static android.graphics.Color.rgb;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.media.conexahotspot.Adapter.HotspotAdapter;
import com.media.conexahotspot.Adapter.PaketAdapter;
import com.media.conexahotspot.Item.PaketItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private String mParam1;
    private String mParam2;
    FirebaseAuth auth;
    ImageView btn_logout;
    private List<PaketItem> paketItems;
    private PaketAdapter adapter;
    private boolean scrollKiri;
    DatabaseReference reference;
    Handler handler;
    RecyclerView recyclerView;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String Name) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("name",Name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        AppBarLayout bar = view.findViewById(R.id.appbar);
        NestedScrollView nestedScrollView = view.findViewById(R.id.nested);
        ImageView notifIcon = view.findViewById(R.id.notif_icon);
        CardView Tagihan = view.findViewById(R.id.tagihan);
        CardView Coverage = view.findViewById(R.id.coverage);
        CardView Paket = view.findViewById(R.id.paket);
        CardView Upgrade = view.findViewById(R.id.upgrade);
        TextView txtName = view.findViewById(R.id.show_username);
        ProgressBar loading = view.findViewById(R.id.progress_bar);
        loading.setVisibility(View.VISIBLE);

        Tagihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.fragment_content, new HistoryFragment())
                        .commit();
                ((MainActivity) requireActivity()).setSelectedMenuItem(R.id.page_2);
            }
        });
        Coverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Paket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ConexaPaket.class);
                startActivity(intent);
            }
        });
        Upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (getArguments() != null) {
            String Name = getArguments().getString("name");
            txtName.setText(Name);
        }
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 0) {
                    txtName.setTextColor(rgb(11 ,48, 84));
                    notifIcon.setColorFilter(rgb(255,41 ,14));
                }else {
                    txtName.setTextColor(Color.WHITE);
                    notifIcon.setColorFilter(Color.WHITE);
                }
            }
        });

        notifIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConexaNotification.class);
                startActivity(intent);
            }
        });
//      RecycleView
        RecyclerView recyclerView = view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false));
        paketItems = new ArrayList<>();
        PaketAdapter adapter = new PaketAdapter(paketItems);
        recyclerView.setAdapter(adapter);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("paket_item");
        Query query = reference.orderByKey().startAt("PKT0001").endAt("PKT0003");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PaketItem paketItem = dataSnapshot.getValue(PaketItem.class);
                    if (paketItem != null) {
                        paketItems.add(paketItem);
                    }
                }
                adapter.notifyDataSetChanged();
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);

                Log.e("FirebaseError", error.getMessage());
            }
        });

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if(scrollKiri){
                    recyclerView.smoothScrollToPosition(currentPosition -1);
                }else {
                    recyclerView.smoothScrollToPosition(currentPosition +1);
                }

                handler.postDelayed(this, 10000);

            }
            };
         handler.postDelayed(runnable, 10000);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    handler.removeCallbacks(runnable);
                } else if (newState== RecyclerView.SCROLL_STATE_IDLE) {
                    handler.postDelayed(runnable, 10000);
                }
            }
        });
        RecyclerView recyclerView2 = view.findViewById(R.id.grid_view_paket);
        List<PaketItem>hotspotItems = new ArrayList<>();
        HotspotAdapter hotspotAdapter = new HotspotAdapter(hotspotItems);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(hotspotAdapter);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("paket_item");
        Query queryhotspot = reference.orderByKey().startAt("PKH0001").endAt("PKH0004");
        queryhotspot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PaketItem paketHotspotItem = dataSnapshot.getValue(PaketItem.class);
                    if (paketHotspotItem != null) {
                        hotspotItems.add(paketHotspotItem);
                    }
                }
                hotspotAdapter.notifyDataSetChanged();
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);

                Log.e("FirebaseError", error.getMessage());
            }
        });

        return view;
    }

    public void updateProfileData(String updatedName, String updatedUsername) {
        TextView nameTextView = getView().findViewById(R.id.show_name);
        TextView usernameTextView = getView().findViewById(R.id.show_username);
        nameTextView.setText(updatedName);
        usernameTextView.setText(updatedUsername);
    }

}
