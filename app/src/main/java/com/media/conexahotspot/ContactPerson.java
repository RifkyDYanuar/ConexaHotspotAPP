package com.media.conexahotspot;

import static com.google.android.gms.common.util.CollectionUtils.listOf;

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

import java.util.ArrayList;

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
            Intent intent = getIntent();
            if (intent != null){
                String Nama = intent.getStringExtra("name");
                String Username = intent.getStringExtra("username");
                String Email = intent.getStringExtra("email");
                String Nohp = intent.getStringExtra("nohp");
                String Addres = intent.getStringExtra("address");
                ArrayList<String[]>customerData = new ArrayList<>();
                customerData.add(new String[]{"Nama", Nama});
                customerData.add(new String[]{"Nomor HP", Nohp});
                customerData.add(new String[]{"Alamat", Addres});
                customerData.add(new String[]{"Paket", "Conexa Breeze"});
                customerData.add(new String[]{"Jadwal Pemasangan", "18-01-2024"});
                String headerPesan = "Hallo admin conexa, tiket pemasangan internet baru telah muncul!\n\n" +
                        "Berikut Rincian informasi data pelanggan baru:\n";

                StringBuilder builder = new StringBuilder(headerPesan);
                int maxLength = 0;
                for (String[] data : customerData) {
                    if (data[0].length() > maxLength) {
                        maxLength = data[0].length();

                    }
                }
                for (String[] data : customerData) {
                    String key = String.format("%-" + maxLength + "s", data[0]); // Ratakan key
                    builder.append(key).append(" : ").append(data[1]).append("\n");
                }
                String number = "+62 82116747973";

                String Message = builder.toString();
                String url = "https://api.whatsapp.com/send?phone="+number+ "&text=" + Uri.encode("```" + Message + "```");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                i.setPackage("com.whatsapp");
                startActivity(i);

            }


        });
        notelepon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:082116747973"));
            startActivity(intent);
        });

    }
}