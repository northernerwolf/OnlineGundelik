package com.example.onlinegundelik.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinegundelik.MainActivity;
import com.example.onlinegundelik.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText edtPassword, edtEmail;
    private Button btnlogin;
    private TextView yazyl, forgetpassword;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPassword = findViewById(R.id.password_log);
        edtEmail = findViewById(R.id.email_log);
        btnlogin = findViewById(R.id.btn_login);
        yazyl= findViewById(R.id.yazyl);
        progressBar = findViewById(R.id.progresbarlogin);
        btnlogin.setOnClickListener(this);

        yazyl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_login:
                userLogin();

        }
    }

    private void userLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty()){
            edtEmail.setError("Ulanyjy ady boş");
            edtEmail.requestFocus();
        }
        if (email.length()<5){
            edtEmail.setError("Ulanyjy ady gysga");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            edtPassword.setError("Gizlin belgini giriziň");
            edtPassword.requestFocus();
            return;
        }
        if (password.length()<7){
            edtPassword.setError("Gizlin belgiňiz gysga");
            edtPassword.requestFocus();
            return;
        }
        mAuth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(LoginActivity.this, "Giriş şowly !", Toast.LENGTH_SHORT).show();


                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(LoginActivity.this, "Giriş şowsuz! Täzeden synanşyň!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user!=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}