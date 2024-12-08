package com.media.conexahotspot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class ProfilFragment extends Fragment {
    ActivityResultLauncher<Intent> resultLauncher;



    public ProfilFragment() {
        // Required empty public constructor
    }

    public static ProfilFragment newInstance( String Name, String Username, String Email, String Nohp, String Addres) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString("name", Name);
        args.putString("username", Username);
        args.putString("email", Email);
        args.putString("nohp", Nohp);
        args.putString("address", Addres);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        TextView showName = view.findViewById(R.id.show_name);
        TextView showUsername = view.findViewById(R.id.show_username);
        ImageView showProfil = view.findViewById(R.id.edit_profile);
        ImageView showLocation = view.findViewById(R.id.edit_location);
        ImageView showPassword = view.findViewById(R.id.edit_password);
        Button showLogout = view.findViewById(R.id.logout);
        ImageView showContact = view.findViewById(R.id.contact);
        ImageView showAbout = view.findViewById(R.id.about);



        String Name = getArguments().getString("name");
        String Name2 = getArguments().getString("name");
        String Username = getArguments().getString("username");
        String Username2 = getArguments().getString("username");
        String Email = getArguments().getString("email");
        String Nohp = getArguments().getString("nohp");
        String Addres = getArguments().getString("address");
        String Password = getArguments().getString("password");

        showName.setText(Name2);
        showUsername.setText(Username2);

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String updatedName = data.getStringExtra("name");
                            String updatedUsername = data.getStringExtra("username");
                            String updatedEmail = data.getStringExtra("email");
                            String updatedNohp = data.getStringExtra("nohp");
                            String updatedAddress = data.getStringExtra("address");


                            showName.setText(updatedName);
                            showUsername.setText(updatedUsername);

                            Intent updatedDataIntent = new Intent();
                            updatedDataIntent.putExtra("name", updatedName);
                            updatedDataIntent.putExtra("username", updatedUsername);
                            updatedDataIntent.putExtra("email", updatedEmail);
                            updatedDataIntent.putExtra("nohp", updatedNohp);
                            updatedDataIntent.putExtra("address", updatedAddress);
                            requireActivity().setResult(Activity.RESULT_OK, updatedDataIntent);
                        }
                    }
                }
        );





        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

//        Goggle Options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                        .requestEmail()
                                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);



        showProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), AccountInfo.class);
                intent.putExtra("name",Name);
                intent.putExtra("username",Username);
                intent.putExtra("email",Email);
                intent.putExtra("nohp",Nohp);
                intent.putExtra("address",Addres);
                Log.d("ProfilFragment", "Launching AccountInfo with intent: " + intent.toString());
                startActivity(intent);
            }
        });

        showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), UpdateAdress.class);
                startActivity(intent);
            }
        });
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ChangePassword.class );
                startActivity(intent);
            }
        });
        showContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(),ContactPerson.class);
                intent.putExtra("name",Name);
                intent.putExtra("username",Username);
                intent.putExtra("email",Email);
                intent.putExtra("nohp",Nohp);
                intent.putExtra("address",Addres);
                startActivity(intent);
            }
        });

        showAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
        showLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Logout")
                        .setMessage("Apakah Anda Yakin Ingin Logout?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.signOut();
                        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Toast.makeText(requireActivity(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
                                requireActivity().finish();
                            }
                        });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(requireActivity(),  "Logout Dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });

               final  AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }



}