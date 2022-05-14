package com.example.onlinegundelik.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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
import com.example.onlinegundelik.adapters.VAdapter;
import com.example.onlinegundelik.fragments.MugallymFragment;
import com.example.onlinegundelik.fragments.OkuwchyFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener  {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText edtPasswordreg, usernamereg,emailreg,locationreg, schooln, lesson;
    private Button btngirin;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private TextView girin;
    String saylawS, ulanyjy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lesson = findViewById(R.id.lesson);
        edtPasswordreg = findViewById(R.id.parol_reg);
        usernamereg = findViewById(R.id.adyfam_reg);
        emailreg = findViewById(R.id.ulanyjy_reg);
        btngirin = findViewById(R.id.btn_login_reg);
        locationreg = findViewById(R.id.location);
        schooln = findViewById(R.id.schoolN);
        btngirin.setOnClickListener((View.OnClickListener) this);
        progressBar = findViewById(R.id.progressbar_reg);
        mAuth = FirebaseAuth.getInstance();
        girin = findViewById(R.id.girin);


        girin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });

//        tabLayout.setupWithViewPager(viewPager);
//
//        VAdapter vAdapter = new VAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        vAdapter.addFragment(new MugallymFragment(),"Mugallym");
//        vAdapter.addFragment(new OkuwchyFragment(),"Okuwçy");
//        viewPager.setAdapter(vAdapter);


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
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            User user = new User(email, username,password,lessons,school,location);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull  Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Üstünlikli girildi!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Üstünliksiz 1. Täzeden barlaň!", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Üstünliksiz 2. Täzeden barlaň!", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

    }
    @Override
    public void onStart(){
        super.onStart();
        if (user!=null){
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();

        }
    }
}