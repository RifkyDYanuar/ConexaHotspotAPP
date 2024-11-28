package com.media.conexahotspot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {



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
        TextView showName2 = view.findViewById(R.id.name);
        TextView showUsername2 = view.findViewById(R.id.username);
        TextView showEmail = view.findViewById(R.id.email);
        TextView showNohp = view.findViewById(R.id.nohp);
        TextView showAddres = view.findViewById(R.id.addres);

        if (getArguments() != null) {
            String Name = getArguments().getString("name");
            String Name2 = getArguments().getString("name");
            String Username = getArguments().getString("username");
            String Username2 = getArguments().getString("username");
            String Email = getArguments().getString("email");
            String Nohp = getArguments().getString("nohp");
            String Addres = getArguments().getString("address");


            showName.setText(Name);
            showUsername.setText(Username);
            showName2.setText(Name2);
            showUsername2.setText(Username2);
            showEmail.setText(Email);
            showNohp.setText(Nohp);
            showAddres.setText(Addres);
        }



        ImageView showProfil = view.findViewById(R.id.edit_profile);
        ImageView showLocation = view.findViewById(R.id.edit_location);
        ImageView showPassword = view.findViewById(R.id.edit_password);
        Button showLogout = view.findViewById(R.id.logout);
        ImageView showContact = view.findViewById(R.id.contact);
        ImageView showAbout = view.findViewById(R.id.about);


//        Goggle Options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                        .requestEmail()
                                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);



        showProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), UpdateProfil.class);
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
        showContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(),ContactPerson.class);
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