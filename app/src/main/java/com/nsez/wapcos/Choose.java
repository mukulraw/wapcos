package com.nsez.wapcos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose extends AppCompatActivity {

    Button user , vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        user = findViewById(R.id.button);
        vendor = findViewById(R.id.button2);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Choose.this , Login.class);
                intent.putExtra("ty" , "u");
                startActivity(intent);


            }
        });


        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Choose.this , Login.class);
                intent.putExtra("ty" , "v");
                startActivity(intent);


            }
        });

    }
}
