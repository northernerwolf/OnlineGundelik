package com.example.onlinegundelik.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinegundelik.MainActivity;
import com.example.onlinegundelik.R;
import com.example.onlinegundelik.register.RegisterActivity;
import com.example.onlinegundelik.register.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class MugallymFragment extends Fragment implements View.OnClickListener {


    private EditText edtPasswordreg, usernamereg,emailreg,locationreg, schooln, lesson;
    private Button btngirin;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressBar progressBar;
    String saylawS, ulanyjy;
    private TextView girin ,emailtxt;
    private Spinner editsection;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v =  inflater.inflate(R.layout.fragment_mugallym, container, false);

        girin = v.findViewById(R.id.girin);
        lesson = v.findViewById(R.id.lesson);
        edtPasswordreg = v.findViewById(R.id.parol_reg);
        usernamereg = v.findViewById(R.id.adyfam_reg);
        emailreg = v.findViewById(R.id.ulanyjy_reg);
        btngirin = v.findViewById(R.id.btn_login_reg);
        locationreg = v.findViewById(R.id.location);
        schooln = v.findViewById(R.id.schoolN);
        btngirin.setOnClickListener((View.OnClickListener) this);
        progressBar = v.findViewById(R.id.progressbar_reg);
        mAuth = FirebaseAuth.getInstance();




//        girin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setResult(2);
//                finish();
//            }
//        });

//        ArrayAdapter<String> adapterS = new ArrayAdapter(this,R.layout.
//                support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.lessons));
//        editsection.setAdapter(adapterS);
//        editsection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (parent.getItemAtPosition(position).equals("Bölüm saýla")){
//                }else {
//                    saylawS = parent.getSelectedItem().toString();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_reg:
                registerUser();
        }
    }

    private void registerUser() {



        String email = emailreg.getText().toString().trim();
        String password = edtPasswordreg.getText().toString().trim();
        String username = usernamereg.getText().toString().trim();
        String location = locationreg.getText().toString().trim();
        String school = schooln.getText().toString().trim();
        String lessons = lesson.getText().toString().trim();

        if (username.isEmpty()){
            usernamereg.setError("Ady famil giriziň");
            usernamereg.requestFocus();
            return;
        }
        if (school.isEmpty()){
            schooln.setError("Mekdep  № giriziň");
            schooln.requestFocus();
            return;
        }
        if (location.isEmpty()){
            usernamereg.setError("Ýerleşýän ýerini giriziň");
            usernamereg.requestFocus();
            return;
        }
        if (lessons.isEmpty()){
            lesson.setError("Okadýan sapagyňyzy giriziň");
            lesson.requestFocus();
            return;
        }
        if (email.length()<5){
            usernamereg.setError("Ulanyjy ady gysga");
            usernamereg.requestFocus();
            return;
        }
        if (password.isEmpty()){
            edtPasswordreg.setError("Gizlin belgini giriziň");
            edtPasswordreg.requestFocus();
            return;
        }
        if (password.length()<7){
            edtPasswordreg.setError("Gizlin belgiňiz gysga");
            edtPasswordreg.requestFocus();
            return;
        }

        if (email.isEmpty()){
            emailreg.setError("Ulanyjy ady giriziň");
            emailreg.requestFocus();
            return;
        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            emailreg.setError("Email salgyňyz nädogry");
//            emailreg.requestFocus();
//            return;
//        }

//        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            User user = new User(email, username,password,lessons,school,location);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull  Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Üstünlikli girildi!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
                                    }else {
                                        Toast.makeText(getActivity(), "Üstünliksiz 1. Täzeden barlaň!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getActivity(), "Üstünliksiz 2. Täzeden barlaň!", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

    }
    @Override
    public void onStart(){
        super.onStart();
        if (user!=null){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            onStop();

        }
    }

}