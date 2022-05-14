package com.example.onlinegundelik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.onlinegundelik.fragments.AccountFragment;
import com.example.onlinegundelik.fragments.AccountFragment2;
import com.example.onlinegundelik.fragments.HomeFragment;
import com.example.onlinegundelik.fragments.HomeFragment2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView navview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        navview2 = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();

        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
                        Fragment selecctedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.oku_home:
                                selecctedFragment = new HomeFragment2();
                                break;

                            case R.id.oku_people:
                                selecctedFragment = new AccountFragment2();
                                break;
                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selecctedFragment).commit();
                        return true;
                    }
                };
        navview2.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }
}