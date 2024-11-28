package com.media.conexahotspot;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.media.conexahotspot.Adapter.PaketAdapter;
import com.media.conexahotspot.Item.PaketItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth auth;
    ImageView btn_logout;
    private List<PaketItem> pakeItems;
    private PaketAdapter adapter;
    private boolean scrollKiri;

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
        TextView txtName = view.findViewById(R.id.show_username);
        if (getArguments() != null) {
            String Name = getArguments().getString("name");
            txtName.setText(Name);
        }
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.client_id))
//                .requestEmail()
//                .build();
//        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
//        auth = FirebaseAuth.getInstance();


//      RecycleView
        RecyclerView recyclerView = view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false));

        pakeItems = new ArrayList<>();
        pakeItems.add(new PaketItem(R.drawable.cable, "Conexa Breeze", "Speed 5 Mbps","Rp.120.000"));
        pakeItems.add(new PaketItem(R.drawable.cable, "Conexa Platinum", "Speed 5 Mbps","Rp.150.000"));
        pakeItems.add(new PaketItem(R.drawable.cable, "Conexa Extras", "Speed 5 Mbps","Rp.200.000"));

        adapter = new PaketAdapter(pakeItems);
        recyclerView.setAdapter(adapter);
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

        return view;

    }

}