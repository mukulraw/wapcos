package com.nsez.wapcos;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nsez.wapcos.loginPOJO.Data;
import com.nsez.wapcos.loginPOJO.loginBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {

    TextView type;
    Button login;
    String ty;
    ProgressBar progress;
    EditText username , password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ty = getIntent().getStringExtra("ty");

        type = findViewById(R.id.textView);
        login = findViewById(R.id.button2);
        progress = findViewById(R.id.progressBar);
        username = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);


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

                Log.d("ry" , ty);

                if (ty.equals("u"))
                {

                    String u = username.getText().toString();
                    String p = password.getText().toString();

                    if (u.length() > 0)
                    {

                        if (p.length() > 0)
                        {

                            progress.setVisibility(View.VISIBLE);

                            Bean b = (Bean) getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                            Call<loginBean> call = cr.login(u , p , SharePreferenceUtils.getInstance().getString("token"));

                            call.enqueue(new Callback<loginBean>() {
                                @Override
                                public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                                    if (response.body().getStatus().equals("1")) {


                                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        Data item = response.body().getData();

                                        SharePreferenceUtils.getInstance().saveString("id" , item.getId());
                                        SharePreferenceUtils.getInstance().saveString("name" , item.getName());
                                        SharePreferenceUtils.getInstance().saveString("email" , item.getEmail());
                                        SharePreferenceUtils.getInstance().saveString("address" , item.getAddress());
                                        SharePreferenceUtils.getInstance().saveString("mobile" , item.getMobile());
                                        SharePreferenceUtils.getInstance().saveString("company" , item.getCompany());
                                        SharePreferenceUtils.getInstance().saveString("type" , "user");

                                        Intent intent = new Intent(Login.this , MainActivity.class);
                                        startActivity(intent);
                                        finishAffinity();

                                    }
                                    else
                                    {
                                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<loginBean> call, Throwable t) {

                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(Login.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(Login.this, "Invalid username", Toast.LENGTH_SHORT).show();
                    }



                }
                else
                {

                    String u = username.getText().toString();
                    String p = password.getText().toString();

                    if (u.length() > 0)
                    {

                        if (p.length() > 0)
                        {

                            progress.setVisibility(View.VISIBLE);

                            Bean b = (Bean) getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                            Call<loginBean> call = cr.login2(u , p , SharePreferenceUtils.getInstance().getString("token"));

                            call.enqueue(new Callback<loginBean>() {
                                @Override
                                public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                                    if (response.body().getStatus().equals("1")) {


                                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        Data item = response.body().getData();

                                        SharePreferenceUtils.getInstance().saveString("id" , item.getId());
                                        SharePreferenceUtils.getInstance().saveString("name" , item.getName());
                                        SharePreferenceUtils.getInstance().saveString("email" , item.getEmail());
                                        SharePreferenceUtils.getInstance().saveString("address" , item.getAddress());
                                        SharePreferenceUtils.getInstance().saveString("mobile" , item.getMobile());
                                        SharePreferenceUtils.getInstance().saveString("company" , item.getCompany());
                                        SharePreferenceUtils.getInstance().saveString("category" , item.getCategory());
                                        SharePreferenceUtils.getInstance().saveString("type" , "vendor");

                                        Intent intent = new Intent(Login.this , MainActivity2.class);
                                        startActivity(intent);
                                        finishAffinity();

                                    }
                                    else
                                    {
                                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<loginBean> call, Throwable t) {

                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(Login.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(Login.this, "Invalid username", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });


    }
}
