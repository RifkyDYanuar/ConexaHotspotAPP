package com.media.conexahotspot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    ImageView goggle ;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    // Autentikasi menggunakan Akun Goggle

    private final ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mAuth = FirebaseAuth.getInstance();
                                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
                                String email = account.getEmail();

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                Query query = reference.orderByChild("email").equalTo(email);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                                String usernameFromDb = userSnapshot.child("username").getValue(String.class);
                                                String nameFromDb = userSnapshot.child("name").getValue(String.class);


                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("name", nameFromDb);
                                                intent.putExtra("email", email);
                                                intent.putExtra("username", usernameFromDb);
                                                startActivity(intent);
                                                Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Email tidak terdaftar. Silakan daftar terlebih dahulu.", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(LoginActivity.this, "Terjadi kesalahan saat memeriksa email", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(LoginActivity.this, "Failed login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSheet();
            }
        });

        FirebaseApp.initializeApp(this);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient =GoogleSignIn.getClient(LoginActivity.this,options);
        mAuth = FirebaseAuth.getInstance();

        goggle = findViewById(R.id.goggle);
        goggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = mGoogleSignInClient.getSignInIntent();
                mStartForResult.launch(intent);


            }
        });

    }

    private void showSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.login_sheet, null);

        mAuth = FirebaseAuth.getInstance();

        TextInputEditText Username = view.findViewById(R.id.username);
        TextInputEditText Password = view.findViewById(R.id.password);
        Button btnLogin = view.findViewById(R.id.login);
        TextView GotoRegister = view.findViewById(R.id.gotoregister);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Username.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (username.isEmpty()) {
                    Username.setError("Eror");
                    Username.requestFocus();

                }if(password.isEmpty()) {
                    Password.setError("Password tidak boleh kosong");
                    Password.requestFocus();
                }else{
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    Query chekUser = reference.orderByChild("username").equalTo(username);
                    chekUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Username.setError(null);
                                boolean isPasswordCorrect = false;

                                // Ambil password dari database
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    String passwordFromDb = userSnapshot.child("password").getValue(String.class);
                                    if (passwordFromDb != null && passwordFromDb.equals(password)) {

                                        isPasswordCorrect = true;

                                        String nameFromDB = userSnapshot.child("name").getValue(String.class);
                                        String emailFromDB = userSnapshot.child("email").getValue(String.class);
                                        String usernameFromDB = userSnapshot.child("username").getValue(String.class);
                                        String noHpFromDB = userSnapshot.child("nohp").getValue(Long.class).toString();
                                        String addresFromDB = userSnapshot.child("address").getValue(String.class);


                                        showSuccesDialog(nameFromDB,emailFromDB,usernameFromDB,passwordFromDb,noHpFromDB,addresFromDB);

                                        return;

                                    }
                                }
                                if (!isPasswordCorrect) {

                                    showDialogError(null,"Password salah, Periksa lagi!");
                                    Password.requestFocus();
                                }
                            } else  {
                                Query queryByEmail = reference.orderByChild("email").equalTo(username);
                                queryByEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            boolean isPasswordCorrect = false;
                                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                                String passwordFromDb = userSnapshot.child("password").getValue(String.class);
                                                if (passwordFromDb != null && passwordFromDb.equals(password)) {
                                                    String nameFromDB = userSnapshot.child("name").getValue(String.class);
                                                    String emailFromDB = userSnapshot.child("email").getValue(String.class);
                                                    String usernameFromDB = userSnapshot.child("username").getValue(String.class);
                                                    String nohpFromDB = userSnapshot.child("nohp").getValue(Long.class).toString();
                                                    String addresFromDB = userSnapshot.child("address").getValue(String.class);

                                                    showSuccesDialog(nameFromDB,emailFromDB,usernameFromDB,passwordFromDb,nohpFromDB,addresFromDB);

                                                    isPasswordCorrect = true;
                                                    break;
                                                }
                                            }
                                            if(!isPasswordCorrect){
                                                showDialogError(null,"Password salah, Periksa lagi!");
                                                Password.requestFocus();
                                            }
                                        }else {

                                            String usernameErrorMessage = "Username atau email tidak ditemukan";
                                            showDialogError(usernameErrorMessage,"");

                                            Username.requestFocus();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });





                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }




            }

        });






        GotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrasiConexa.class);
                startActivity(intent);
                bottomSheetDialog.dismiss();

            }
        });
        bottomSheetDialog.setContentView(view);
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        bottomSheetDialog.show();
    }

    private void showSuccesDialog(String nameFromDB, String emailFromDB, String usernameFromDB, String passwordFromDb, String nohpFromDB, String addresFromDB){
        LinearLayout layout = findViewById(R.id.succesdialog);
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.alert_login_succes,layout);

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setView(view);
        alertDialog.show();

        new Handler().postDelayed(()-> {
            alertDialog.dismiss();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("name", nameFromDB);
            intent.putExtra("email", emailFromDB);
            intent.putExtra("username", usernameFromDB);
            intent.putExtra("password", passwordFromDb);
            intent.putExtra("nohp", nohpFromDB);
            intent.putExtra("address", addresFromDB);
            startActivity(intent);

        },2000);
    }

    private void showDialogError(String usernameErrorMessage , String passwordErrorMessage){
        LinearLayout layout = findViewById(R.id.erordialog);
        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.alert_error,layout);

        TextView content = view.findViewById(R.id.content);
        TextView content2 = view.findViewById(R.id.content2);
        if (usernameErrorMessage == null || usernameErrorMessage.isEmpty()) {
            content.setVisibility(View.GONE);
        } else {
            content.setText(usernameErrorMessage);
            content.setVisibility(View.VISIBLE);
        }

        if (passwordErrorMessage == null || passwordErrorMessage.isEmpty()) {
            content2.setVisibility(View.GONE);
        } else {
            content.setText(passwordErrorMessage);
            content.setVisibility(View.VISIBLE);
        }

        AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setView(view);
        alertDialog.show();
    }
}
