package com.media.conexahotspot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {
    DatabaseReference reference;
    TextInputEditText txtOldPassword,txtNewPassword;
    Button btnChange;
    FirebaseAuth mAuth;
    AuthCredential authCredential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        txtOldPassword = findViewById(R.id.old_password);
        txtNewPassword = findViewById(R.id.new_password);
        btnChange = findViewById(R.id.btnChange);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });



    }
    private void updatePassword() {
        String oldPassword = txtOldPassword.getText().toString().trim(); // Password lama
        String newPassword = txtNewPassword.getText().toString().trim(); // Password baru

        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            if (oldPassword.isEmpty()) txtOldPassword.setError("Password lama tidak boleh kosong");
            if (newPassword.isEmpty()) txtNewPassword.setError("Password baru tidak boleh kosong");
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String currentPassword = snapshot.child("password").getValue(String.class);
                        String name = snapshot.child("name").getValue(String.class);
                        String username = snapshot.child("username").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        String nohp = snapshot.child("nohp").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);

                        if (currentPassword != null && currentPassword.equals(oldPassword)) {
                            reference.child("password").setValue(newPassword).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    String Message = "Password berhasil diperbarui";
                                    showSuccesDialog(Message);
                                    Intent intent = new Intent(ChangePassword.this, MainActivity.class);
                                    intent.putExtra("fragment", "profil");
                                    intent.putExtra("name", name);
                                    intent.putExtra("username", username);
                                    intent.putExtra("email", email);
                                    intent.putExtra("nohp", nohp);
                                    intent.putExtra("address", address);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ChangePassword.this, "Gagal memperbarui password: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            String Message2 = "Update password gagal";
                            String Message = "Password lama tidak sesuai";
                            showLoginError(Message, Message2);
                        }
                    } else {
                        Toast.makeText(ChangePassword.this, "Data pengguna tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ChangePassword.this, "Terjadi kesalahan: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Pengguna tidak terautentikasi", Toast.LENGTH_SHORT).show();
        }
    }
    private void showLoginError(String Message, String Message2){
        LinearLayout layout = findViewById(R.id.erordialog);
        View view = LayoutInflater.from(ChangePassword.this).inflate(R.layout.alert_login_error,layout);
        TextView title = view.findViewById(R.id.title);
        TextView content = view.findViewById(R.id.content);
        content.setText(Message);
        title.setText(Message2);


        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setView(view);
        alertDialog.show();

    }
    private void showSuccesDialog(String Message){
        LinearLayout layout = findViewById(R.id.succesdialog);
        View view = LayoutInflater.from(ChangePassword.this).inflate(R.layout.alert_login_succes,layout);
        TextView title = view.findViewById(R.id.title);
        title.setText(Message);
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setView(view);
        alertDialog.show();

    }





}