package com.media.conexahotspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateAdress extends AppCompatActivity {
    ImageView goBack ;

    EditText txtAddress;
    Button btnUpdate;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_adress);

        txtAddress = findViewById(R.id.alamat);
        btnUpdate = findViewById(R.id.btn_addres);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Alamat = txtAddress.getText().toString();
                if(Alamat.isEmpty()){
                    txtAddress.setError("Alamat Tidak Boleh Kosong");
                    return;
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null){
                    String userId = user.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String currentPassword = snapshot.child("password").getValue(String.class);
                                String name = snapshot.child("name").getValue(String.class); // Ambil data nama
                                String username = snapshot.child("username").getValue(String.class);
                                String email = snapshot.child("email").getValue(String.class);
                                String nohp = snapshot.child("nohp").getValue(String.class);
                                String address = snapshot.child("address").getValue(String.class);

                                if(address != null){
                                    reference.child("address").setValue(Alamat).addOnCompleteListener(task -> {
                                                Toast.makeText(UpdateAdress.this, "Alamat Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(UpdateAdress.this, MainActivity.class);
                                        intent.putExtra("fragment", "profil");
                                        intent.putExtra("name", name);
                                        intent.putExtra("username", username);
                                        intent.putExtra("email", email);
                                        intent.putExtra("nohp", nohp);
                                        intent.putExtra("address", address);
                                        startActivity(intent);
                                    });
                                }else {
                                    Toast.makeText(UpdateAdress.this, "Alamat Gagal Diperbarui", Toast.LENGTH_SHORT).show();
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        goBack = findViewById(R.id.back);
        goBack.setOnClickListener(view -> {
            finish();
        });


    }
}