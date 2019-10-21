package com.nsez.wapcos;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    TextView type;
    Button login;
    String ty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ty = getIntent().getStringExtra("ty");

        type = findViewById(R.id.textView);
        login = findViewById(R.id.button2);


        if (ty.equals("u"))
        {
            type.setText("USER LOGIN");
        }
        else
        {
            type.setText("VENDOR LOGIN");
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ty.equals("u"))
                {
                    Intent intent = new Intent(Login.this , MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
                else
                {
                    type.setText("VENDOR LOGIN");
                }

            }
        });


    }
}
