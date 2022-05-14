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
import com.example.onlinegundelik.register.OUser;
import com.example.onlinegundelik.register.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountFragment2 extends Fragment {


    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;
    TextView logoutokuw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_account2, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("OUsers");
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        final TextView adyfamoku = v.findViewById(R.id.okuw_adyfam);
        final TextView meknom = v.findViewById(R.id.oku_meknom);
        final TextView yerleshyanoku = v.findViewById(R.id.oku_yerleshyanyer);
        final TextView studentId = v.findViewById(R.id.studentid);

        logoutokuw = v.findViewById(R.id.log_out_oku);

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OUser userProfilo = snapshot.getValue(OUser.class);

                if (userProfilo != null){
                    String fullnameo = userProfilo.username;
                    String locationo = userProfilo.location;
                    String schooln = userProfilo.school;
                    String studentid = userProfilo.studentID;
                    adyfamoku.setText(fullnameo);
                    meknom.setText(schooln);
                    yerleshyanoku.setText(locationo);
                    studentId.setText(studentid);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Ýalňyşlyk döredi", Toast.LENGTH_SHORT).show();

            }
        });

        logoutokuw.setOnClickListener(new View.OnClickListener() {
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