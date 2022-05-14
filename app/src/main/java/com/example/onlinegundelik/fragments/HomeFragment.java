package com.example.onlinegundelik.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinegundelik.R;
import com.example.onlinegundelik.adapters.ClasssAdapter;
import com.example.onlinegundelik.models.Classs;
import com.example.onlinegundelik.register.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;
    RecyclerView recyclerViewRek;
    ImageView sortimg;
    private FirebaseFirestore db;
    List<Classs> classsList;
    ClasssAdapter classsAdapter;
    ProgressBar progressBar2;
    SwipeRefreshLayout swip_menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        recyclerViewRek = v.findViewById(R.id.recycler_main);
        progressBar2 = v.findViewById(R.id.progressbar2);
        swip_menu = v.findViewById(R.id.swip_main);

        db = FirebaseFirestore.getInstance();



        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        recyclerViewRek.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false ));
        classsList = new ArrayList<>();
        classsAdapter = new ClasssAdapter(getActivity(),classsList);
        recyclerViewRek.setAdapter(classsAdapter);
        db.collection("AllClass").whereEqualTo("userid",UserID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                    String documentid = documentSnapshot.getId();

                                    Classs classs = documentSnapshot.toObject(Classs.class);

                                    classs.setDocumentid(documentid);
                                    classsList.add(classs);

                                    classsAdapter.notifyDataSetChanged();

                                }
                            } else {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                            }
                        }catch (IllegalAccessError e) {
                            Log.e("2error", "ConnectivityManager.NetworkCallback.onAvailable: ", e);
                        }
                    }
                });
//        swip_menu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//
//
//            }
//        });



        final TextView yerleshyan = v.findViewById(R.id.yerleshyan_menu);
        final TextView mekdepnom = v.findViewById(R.id.school_number);

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfil = snapshot.getValue(User.class);

                if (userProfil != null){
                    String locationM = userProfil.location;
                    String schollnM = userProfil.school;
                    yerleshyan.setText(locationM);
                    mekdepnom.setText(schollnM);

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