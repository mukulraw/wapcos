package com.nsez.wapcos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        t = new Timer();

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                startApp();
            }
        } , 1200);

    }

    void startApp()
    {

        String id = SharePreferenceUtils.getInstance().getString("id");
        String type = SharePreferenceUtils.getInstance().getString("type");

        if (id.length() > 0)
        {
            if (type.equals("user"))
            {
                Intent intent = new Intent(Splash.this , MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
            else
            {
                Intent intent = new Intent(Splash.this , MainActivity2.class);
                startActivity(intent);
                finishAffinity();
            }
        }
        else
        {
            Intent intent = new Intent(Splash.this , Choose.class);
            startActivity(intent);
            finish();
        }

    }

}
