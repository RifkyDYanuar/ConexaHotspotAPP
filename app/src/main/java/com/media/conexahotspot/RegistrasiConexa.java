package com.media.conexahotspot;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.media.conexahotspot.Item.UserHelper;

public class RegistrasiConexa extends AppCompatActivity {

    private static final String CHANNEL_ID = "conexa_media_hotspot_channel";
    private static final String CHANNEL_NAME = "Conexa Media Hotspot Notifications";
    private static final String CHANNEL_DESCRIPTION = "Channel for Conexa Media Hotspot notifications";
    Button Register;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_conexa);
        createChannelNotif();
        EditText Enama = findViewById(R.id.nama);
        EditText Eemail = findViewById(R.id.email);
        EditText Eusername = findViewById(R.id.username);
        EditText Epassword = findViewById(R.id.password);
        Register = findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nama = Enama.getText().toString().trim();
                String Email = Eemail.getText().toString().trim();
                String Username = Eusername.getText().toString().trim();
                String Password = Epassword.getText().toString().trim();

                if (Nama.isEmpty() || Email.isEmpty() || Username.isEmpty() || Password.isEmpty()) {
                    if (Nama.isEmpty()) Enama.setError("Nama Tidak Boleh Kosong");
                    if (Email.isEmpty()) Eemail.setError("Email Tidak Boleh Kosong");
                    if (Username.isEmpty()) Eusername.setError("Username Tidak Boleh Kosong");
                    if (Password.isEmpty()) Epassword.setError("Password Tidak Boleh Kosong");
                } else {

                    auth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    String userId = user.getUid();

                                    UserHelper newUser = new UserHelper(Nama, Email, Username, Password, "", "");
                                    databaseReference.child(userId).setValue(newUser)
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    showDialogSucces();
                                                    showNotification();

                                                } else {
                                                    Toast.makeText(RegistrasiConexa.this, "Gagal menyimpan data pengguna", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                } else {
                                    String errorMessage = task.getException().getMessage() ;
                                    showDialogError(errorMessage);

                                }
                            });
                }
            }
        });
    }
    private void showDialogSucces(){

        LinearLayout layout = findViewById(R.id.succesdialog);
        View view = LayoutInflater.from(RegistrasiConexa.this).inflate(R.layout.alert_succes, layout);

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiConexa.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button nextLogin = view.findViewById(R.id.nextLogin);
        nextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrasiConexa.this, LoginActivity.class);
                startActivity(intent);
                alertDialog.dismiss();
                finish();
            }
        });

        alertDialog.show();
    }
    private void showDialogError(String errorMessage){
        LinearLayout layout = findViewById(R.id.erordialog);
        View view = LayoutInflater.from(RegistrasiConexa.this).inflate(R.layout.alert_error, layout);

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiConexa.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView content = view.findViewById(R.id.content);
        content.setText(errorMessage);
        alertDialog.show();
    }

    private void createChannelNotif(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);

            if (manager!= null){

                manager.createNotificationChannel(notificationChannel);
            }
        }
    }

    private void showNotification(){
        createChannelNotif();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_cnx)
                .setContentTitle("Register is Successfully")
                .setContentText("Silahkan login")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Nikmati Paket internet murah, luas, dan cepat tanpa batas di Conexa Media Hotspot"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        notificationManagerCompat.notify(1, builder.build());
    }
}
