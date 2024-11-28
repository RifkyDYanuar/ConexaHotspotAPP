package com.media.conexahotspot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactPerson extends AppCompatActivity {
    ImageView back, email, whatsapps, notelepon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_person);
        email = findViewById(R.id.intentEmail);
        whatsapps = findViewById(R.id.intentWhatsapps);
        notelepon = findViewById(R.id.intentPhone);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
        });
        email.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:conexamediaacces@gmail.com"));
            startActivity(intent);
        });
        whatsapps.setOnClickListener(v -> {
            String number = "+62 82116747973";
            String Message = "Halo Admin, saya ingin bertanya terkait Conexa!";
            String url = "https://api.whatsapp.com/send?phone="+number+ "&text=" + Uri.encode(Message);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            i.setPackage("com.whatsapp");
            startActivity(i);
        });
        notelepon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:082116747973"));
            startActivity(intent);
        });

    }
}