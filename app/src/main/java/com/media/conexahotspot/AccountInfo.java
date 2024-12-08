package com.media.conexahotspot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class AccountInfo extends AppCompatActivity {
    TextView showName, showUsername, showEmail, showNohp, showAddres;
    Button GoUpdate;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_info);

        showName = findViewById(R.id.show_name);
        showUsername = findViewById(R.id.show_username);
        showEmail = findViewById(R.id.show_email);
        showNohp = findViewById(R.id.show_nohp);
        showAddres = findViewById(R.id.show_addres);
        GoUpdate = findViewById(R.id.update);
        back = findViewById(R.id.back);

        Intent intent = getIntent();
        if (getIntent()!= null) {
            String Name = intent.getStringExtra("name");
            String Username = intent.getStringExtra("username");
            String Email = intent.getStringExtra("email");
            String Nohp = intent.getStringExtra("nohp");
            String Addres = intent.getStringExtra("address");

            showName.setText(Name != null ? Name : "Belum diisi");
            showUsername.setText(Username != null ? Username : "Belum diisi");
            showEmail.setText(Email != null ? Email : "Belum diisi");
            showNohp.setText(Nohp != null ? Nohp : "Belum diisi");
            showAddres.setText(Addres != null ? Addres : "Belum diisi");


            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", Name);  // Data yang diperbarui
            resultIntent.putExtra("username", Name);
            resultIntent.putExtra("email", Name);
            resultIntent.putExtra("nohp", Name);// Data yang diperbarui
            setResult(Activity.RESULT_OK, resultIntent);


        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GoUpdate.setOnClickListener(view -> {
            Intent i = new Intent(AccountInfo.this, UpdateProfil.class);
            i.putExtra("name", showName.getText().toString());
            i.putExtra("username", showUsername.getText().toString());
            i.putExtra("email", showEmail.getText().toString());
            i.putExtra("nohp", showNohp.getText().toString());
            startActivity(i);
            finish();
        });

    }


}