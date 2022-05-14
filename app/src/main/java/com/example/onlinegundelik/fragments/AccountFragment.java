package com.example.onlinegundelik.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinegundelik.R;
import com.example.onlinegundelik.register.LoginActivity;
import com.example.onlinegundelik.register.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;
    TextView logoutmug;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_account, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        logoutmug = v.findViewById(R.id.log_out);
        final TextView adyfam = v.findViewById(R.id.familya_ady);
        final TextView okadyansap = v.findViewById(R.id.okadyan_sap);
        final TextView yerleshyan = v.findViewById(R.id.yerleshyan_yer);

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfil = snapshot.getValue(User.class);

                if (userProfil != null){
                    String fullname = userProfil.username;
                    String locationm = userProfil.location;
                    String subject = userProfil.lessons;
                    adyfam.setText(fullname);
                    okadyansap.setText(subject);
                    yerleshyan.setText(locationm);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Ýalňyşlyk döredi", Toast.LENGTH_SHORT).show();

            }
        });

        logoutmug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                onStop();
            }
        });

        return v;
    }
}