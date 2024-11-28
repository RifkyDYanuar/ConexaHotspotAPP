package com.media.conexahotspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button btn_logout;
    BottomNavigationView bottomNavigationView;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String Name= intent.getStringExtra("name");
        String Username = intent.getStringExtra("username");
        String Email = intent.getStringExtra("email");
        String Nohp = intent.getStringExtra("nohp");
        String Addres = intent.getStringExtra("address");


        bottomNavigationView = findViewById(R.id.menu_navbottom);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, HomeFragment.newInstance(Name))
                .commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment pilihFragment = null;
                if(item.getItemId() == R.id.home){
                    pilihFragment = HomeFragment.newInstance(Name);
                }else if(item.getItemId() == R.id.page_2){
                    pilihFragment = new HistoryFragment();
                }else if(item.getItemId() == R.id.page_4){
                    pilihFragment = new LayananFragment();
                }else if(item.getItemId() == R.id.page_5){
                    pilihFragment = ProfilFragment.newInstance(Name,Username,Email,Nohp,Addres);
                }

                if(pilihFragment != null){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.fragment_content,pilihFragment)
                            .commit();
                    return true;
                }

                return false;

            }
        });





    }
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            System.exit(0);
            finish();
        } else {
            Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}