package com.nsez.wapcos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nsez.wapcos.singleComplaintPOJO.Data;
import com.nsez.wapcos.singleComplaintPOJO.singleComplaintBean;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ComplaintDetailsVendors extends AppCompatActivity {
    Toolbar toolbar;
    TextView comment;

    TextView name, category, date, ack, closure, status, handled, complaint, vname, category1, email, phone, altemail, company, address , acknowledge , open;
    LinearLayout attachment;
    String cid, title , vid;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details_vendors);

        cid = getIntent().getStringExtra("cid");

        title = getIntent().getStringExtra("title");

        comment = findViewById(R.id.comment);
        toolbar = findViewById(R.id.toolbar);
        name = findViewById(R.id.name);
        category = findViewById(R.id.category);
        date = findViewById(R.id.date);
        ack = findViewById(R.id.ack);
        closure = findViewById(R.id.closure);
        status = findViewById(R.id.status);
        complaint = findViewById(R.id.complaint);
        vname = findViewById(R.id.vname);
        handled = findViewById(R.id.handled);
        category1 = findViewById(R.id.category1);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        altemail = findViewById(R.id.altemail);
        acknowledge = findViewById(R.id.acknowledge);
        company = findViewById(R.id.company);
        address = findViewById(R.id.address);
        attachment = findViewById(R.id.attachment);
        progress = findViewById(R.id.progress);
        open = findViewById(R.id.open);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(title);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ComplaintDetailsVendors.this, Comments2.class);
                intent.putExtra("cid" , cid);
                intent.putExtra("vid" , vid);
                intent.putExtra("title" , title);
                startActivity(intent);

            }
        });


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<singleComplaintBean> call = cr.getComplainById1(cid);

        call.enqueue(new Callback<singleComplaintBean>() {
            @Override
            public void onResponse(Call<singleComplaintBean> call, Response<singleComplaintBean> response) {

                if (response.body().getStatus().equals("1")) {

                    Data item = response.body().getData();

                    name.setText(item.getName());
                    category.setText(item.getCategory());
                    date.setText(item.getCreatedDate());
                    ack.setText(item.getAkdDate());
                    closure.setText(item.getExpClDate());
                    status.setText(item.getStatus());
                    handled.setText(item.getHandled());
                    complaint.setText(item.getComplain());
                    vname.setText(item.getVname());
                    category1.setText(item.getCategory());
                    email.setText(item.getEmail());
                    phone.setText(item.getPhone());
                    altemail.setText(item.getAlternateemail());

                    company.setText(item.getCompany());
                    address.setText(item.getAddress());

                    vid = item.getVid();

                    attachment.removeAllViews();

                    for (int i = 0; i < item.getImages().size(); i++) {

                        View view = View.inflate(getApplicationContext(), R.layout.attachment, null);
                        TextView tit = view.findViewById(R.id.title);

                        tit.setText(item.getImages().get(i).getImage());

                        attachment.addView(view);

                    }

                    if (item.getAkdDate().equals("")) {
                        acknowledge.setVisibility(View.VISIBLE);
                    } else {
                        acknowledge.setVisibility(View.GONE);
                    }

                    if (item.getStatus().equals("Open") || item.getStatus().equals("ReOpen"))
                    {
                        open.setText("CLOSE");
                    }
                    else
                    {
                        open.setText("OPEN");
                    }


                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<singleComplaintBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (open.getText().toString().equals("OPEN"))
                {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<String> call = cr.changeStatus("Open" , cid);

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {


                            progress.setVisibility(View.GONE);
                            Toast.makeText(ComplaintDetailsVendors.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

                            finish();


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {

                }

            }
        });


        acknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progress.setVisibility(View.VISIBLE);

                Bean b = (Bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                Call<String> call = cr.changeStatus("Acknowledge" , cid);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {


                        progress.setVisibility(View.GONE);
                        Toast.makeText(ComplaintDetailsVendors.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

                        finish();


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });


            }
        });

    }
}
