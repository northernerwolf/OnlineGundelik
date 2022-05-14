package com.example.onlinegundelik.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinegundelik.R;
import com.example.onlinegundelik.register.OUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment2 extends Fragment {


    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home2, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("OUsers");
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();


        final TextView meknom = v.findViewById(R.id.meknom_ok);
        final TextView yerleshyanoku = v.findViewById(R.id.location_menu2);
        final TextView studentId = v.findViewById(R.id.student_id2);


        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OUser userProfilo = snapshot.getValue(OUser.class);

                if (userProfilo != null){
                    String locationo = userProfilo.location;
                    String schooln = userProfilo.school;
                    String studentid = userProfilo.studentID;
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

        return v;
    }
}