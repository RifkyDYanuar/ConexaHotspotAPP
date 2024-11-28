package com.media.conexahotspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfil extends AppCompatActivity {

    EditText txtName,txtUsername,txtEmail,txtTelepon;
    FirebaseDatabase database;
    ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profil);

        goBack = findViewById(R.id.back);
//        txtName.findViewById(R.id.name);
//        txtUsername.findViewById(R.id.username);
//        txtEmail.findViewById(R.id.email);
//        txtTelepon.findViewById(R.id.notelepon);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }
}