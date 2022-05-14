package com.example.onlinegundelik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.onlinegundelik.Add.AddActivity;
import com.example.onlinegundelik.fragments.AccountFragment;
import com.example.onlinegundelik.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navview = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();

        BottomNavigationItemView add = (BottomNavigationItemView) findViewById(R.id.nav_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
                        Fragment selecctedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                selecctedFragment = new HomeFragment();
                                break;

                            case R.id.nav_people:
                                selecctedFragment = new AccountFragment();
                                break;
                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selecctedFragment).commit();
                        return true;
                    }
                };
        navview.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }
}