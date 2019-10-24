package com.nsez.wapcos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.nsez.wapcos.getConmplainPOJO.getComplainBean;
import com.nsez.wapcos.singleComplaintPOJO.Data;
import com.nsez.wapcos.singleComplaintPOJO.singleComplaintBean;

import org.w3c.dom.Text;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ComplaintDetailsUser extends AppCompatActivity {

    Toolbar toolbar;
    TextView comment;

    TextView name, category, date, ack, closure, status, handled, complaint, vname, category1, email, phone, altemail, altphone, company, address, closure1, reopen;
    LinearLayout attachment;
    String cid, title , vid;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details_user);

        PRDownloader.initialize(getApplicationContext());

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
        altphone = findViewById(R.id.altphone);
        company = findViewById(R.id.company);
        address = findViewById(R.id.address);
        closure1 = findViewById(R.id.closure1);
        attachment = findViewById(R.id.attachment);
        progress = findViewById(R.id.progress);
        reopen = findViewById(R.id.reopen);


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

                Intent intent = new Intent(ComplaintDetailsUser.this, Comments.class);
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

        Call<singleComplaintBean> call = cr.getComplainById(cid);

        call.enqueue(new Callback<singleComplaintBean>() {
            @Override
            public void onResponse(Call<singleComplaintBean> call, Response<singleComplaintBean> response) {

                if (response.body().getStatus().equals("1")) {

                    final Data item = response.body().getData();

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
                    altphone.setText(item.getAlternatephone());
                    company.setText(item.getCompany());
                    address.setText(item.getAddress());

                    vid = item.getVid();

                    attachment.removeAllViews();

                    for (int i = 0; i < item.getImages().size(); i++) {

                        View view = View.inflate(getApplicationContext(), R.layout.attachment, null);
                        TextView tit = view.findViewById(R.id.title);

                        tit.setText(item.getImages().get(i).getImage());


                        final int finalI = i;
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                int downloadId = PRDownloader.download("http://www.nsezwapcos.com/admin/upload/" + item.getImages().get(finalI).getImage(), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() , item.getImages().get(finalI).getImage())
                                        .build()
                                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                            @Override
                                            public void onStartOrResume() {

                                            }
                                        })
                                        .setOnPauseListener(new OnPauseListener() {
                                            @Override
                                            public void onPause() {

                                            }
                                        })
                                        .setOnCancelListener(new OnCancelListener() {
                                            @Override
                                            public void onCancel() {

                                            }
                                        })
                                        .setOnProgressListener(new OnProgressListener() {
                                            @Override
                                            public void onProgress(Progress progress) {

                                                Log.d("progress" , String.valueOf(progress.totalBytes));

                                            }
                                        })
                                        .start(new OnDownloadListener() {
                                            @Override
                                            public void onDownloadComplete() {

                                                Log.d("completed" , "completed");
                                                Toast.makeText(ComplaintDetailsUser.this, "Successfully downloaded in Downloads", Toast.LENGTH_SHORT).show();

                                            }

                                            @Override
                                            public void onError(Error error) {

                                                Log.d("error" , error.getConnectionException().toString());

                                            }

                                        });

                            }
                        });


                        attachment.addView(view);

                    }

                    if (item.getClosing().length() > 0) {
                        closure1.setText(item.getClosing());
                        closure1.setVisibility(View.VISIBLE);
                    } else {
                        closure1.setVisibility(View.GONE);
                    }

                    if (item.getStatus().equals("Close")) {
                        reopen.setVisibility(View.VISIBLE);
                    } else {
                        reopen.setVisibility(View.GONE);
                    }

                }


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<singleComplaintBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        closure1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int downloadId = PRDownloader.download("http://www.nsezwapcos.com/admin/upload/" + closure1.getText().toString(), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() , closure1.getText().toString())
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {

                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {

                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {

                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {

                                Log.d("progress" , String.valueOf(progress.totalBytes));

                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {

                                Log.d("completed" , "completed");
                                Toast.makeText(ComplaintDetailsUser.this, "Successfully downloaded in Downloads", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onError(Error error) {

                                Log.d("error" , error.getConnectionException().toString());

                            }

                        });

            }
        });

        reopen.setOnClickListener(new View.OnClickListener() {
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

                Call<String> call = cr.changeStatus("ReOpen" , cid);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {


                        progress.setVisibility(View.GONE);
                        Toast.makeText(ComplaintDetailsUser.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

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
