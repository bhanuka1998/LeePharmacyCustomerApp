package com.example.leepharmacycustomerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager frag_man;
    private FragmentTransaction frag_trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.nav_home:
                        loadFragment(new HomeFragment());
                        break;
                   case R.id.nav_search:
                     loadFragment(new ViewDrugs());
                    break;
//                    case R.id.nav_cart:
//                        loadFragment(new PrescriptionsFragment(str));
//                        break;
//                    case R.id.nav_user:
//
//
//                        loadFragment(new ProfileFragment(str));
//                        break;
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        frag_man = getSupportFragmentManager();
        frag_trans = frag_man.beginTransaction();
        frag_trans.replace(R.id.frag_container, fragment);
        frag_trans.commit();

    }
}