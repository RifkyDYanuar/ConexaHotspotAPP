package com.media.conexahotspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfil extends AppCompatActivity {

    EditText txtName,txtUsername,txtEmail,txtTelepon;
    FirebaseDatabase database;
    DatabaseReference reference;
    ImageView goBack;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profil);
        txtName = findViewById(R.id.name);
        txtUsername = findViewById(R.id.username);
        txtEmail = findViewById(R.id.email);
        txtTelepon = findViewById(R.id.notelepon);
        btnUpdate = findViewById(R.id.update);


        Intent intent = getIntent();
        String Nama = intent.getStringExtra("name");
        String Username = intent.getStringExtra("username");
        String Email = intent.getStringExtra("email");
        String Nohp = intent.getStringExtra("nohp");


        if(intent != null){
            txtName.setText(Nama);
            txtUsername.setText(Username);
            txtEmail.setText(Email);
            txtTelepon.setText(Nohp);
        }

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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });


    }
    private void update(){
        String Nama = txtName.getText().toString();
        String Username = txtUsername.getText().toString();
        String Email = txtEmail.getText().toString();
        String Nohp = txtTelepon.getText().toString();
        if(Nama.isEmpty() || Username.isEmpty() || Email.isEmpty() || Nohp.isEmpty()){
            if(Nama.isEmpty()) txtName.setError("Nama Tidak Boleh Kosong");
            if(Username.isEmpty()) txtUsername.setError("Username Tidak Boleh Kosong");
            if(Email.isEmpty()) txtEmail.setError("Email Tidak Boleh Kosong");
            if(Nohp.isEmpty()) txtTelepon.setError("Nomor Telepon Tidak Boleh Kosong");
            return;
        }
        reference = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference.orderByChild("email").equalTo(Email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String userId = userSnapshot.getKey();

                        // Perbarui data pengguna
                        Map<String, Object> update = new HashMap<>();
                        update.put("name", Nama);
                        update.put("username", Username);
                        update.put("email", Email);
                        update.put("notelp", Nohp);

                        reference.child(userId).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(UpdateProfil.this, MainActivity.class);
                                    intent.putExtra("name", Nama);
                                    intent.putExtra("username", Username);
                                    intent.putExtra("email", Email);
                                    intent.putExtra("nohp", Nohp);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(UpdateProfil.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UpdateProfil.this, "Data Gagal Diperbarui", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(UpdateProfil.this, "Pengguna Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfil.this, "Terjadi Kesalahan: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//
    }









}