package com.nsez.wapcos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {
    BottomNavigationView bottom;
    ImageButton search , notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottom = findViewById(R.id.bottomNavigationView2);
        search = findViewById(R.id.imageButton);
        notification = findViewById(R.id.imageButton2);

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int iidd = menuItem.getItemId();

                if (iidd == R.id.complaint)
                {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    complaint1 test = new complaint1();
                    ft.replace(R.id.replace, test);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    //ft.addToBackStack(null);
                    ft.commit();
                }
                else if (iidd == R.id.profile){
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    profile test = new profile();
                    ft.replace(R.id.replace, test);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    //ft.addToBackStack(null);
                    ft.commit();
                }

                return true;
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity2.this , Search1.class);
                startActivity(intent);

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity2.this , Notification.class);
                startActivity(intent);

            }
        });


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        complaint1 test = new complaint1();
        ft.replace(R.id.replace, test);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        //ft.addToBackStack(null);
        ft.commit();
    }
}
