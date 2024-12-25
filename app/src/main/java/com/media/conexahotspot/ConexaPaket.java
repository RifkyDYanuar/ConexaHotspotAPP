package com.media.conexahotspot;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.media.conexahotspot.Adapter.AllPaketAdapter;
import com.media.conexahotspot.Adapter.MyPaketAdapter;
import com.media.conexahotspot.Item.PaketItem;

import java.util.ArrayList;
import java.util.List;

public class ConexaPaket extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PaketItem> allpaketItems;
    FirebaseAuth auth;
    DatabaseReference reference;
    ImageView Back;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MyPaketAdapter myPaketAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conexa_paket);
        Back = findViewById(R.id.back);
        Back.setOnClickListener(v -> {
            onBackPressed();
        });
        tabLayout = findViewById(R.id.tableLayout);
        viewPager2 = findViewById(R.id.view_pager);
        tabLayout.addTab(tabLayout.newTab().setText("Conexa"));
        tabLayout.addTab(tabLayout.newTab().setText("Hotspot"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        myPaketAdapter = new MyPaketAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(myPaketAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Pindahkan ViewPager ke posisi tab yang dipilih
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Tidak diperlukan
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Tidak diperlukan
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_to_bottom);
    }

}