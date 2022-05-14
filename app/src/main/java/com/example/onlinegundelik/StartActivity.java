package com.example.onlinegundelik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.onlinegundelik.fragments.HomeFragment;
import com.example.onlinegundelik.register.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    TextView textSanly, textGundelik;
    RelativeLayout relativ;
    Animation txtAnimation, layoutAnimation;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        textGundelik = findViewById(R.id.gundelik);
        textSanly = findViewById(R.id.sanly);
        relativ = findViewById(R.id.start_page);
        user = FirebaseAuth.getInstance().getCurrentUser();

        txtAnimation = AnimationUtils.loadAnimation(StartActivity.this,R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(StartActivity.this,R.anim.botom_to_top);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                relativ.setVisibility(View.VISIBLE);
//                relativ.setAnimation(layoutAnimation);
//
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        textSanly.setVisibility(View.VISIBLE);
////                        textGundelik.setVisibility(View.VISIBLE);
////
////                        textSanly.setAnimation(txtAnimation);
////                        textGundelik.setAnimation(txtAnimation);
////
////                    }
////                },800);
//
//
//            }
//        },500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user!=null){
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); }else{
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },1500);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (user!=null){
//            Intent intent = new Intent(StartActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }


}