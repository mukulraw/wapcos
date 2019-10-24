package com.nsez.wapcos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nsez.wapcos.feedbackPOJO.Datum;
import com.nsez.wapcos.feedbackPOJO.feedbackBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Comments extends AppCompatActivity {

    Toolbar toolbar;
    String cid, title , vid;
    ProgressBar progress;
    RecyclerView grid;
    GridLayoutManager manager;
    List<Datum> list;
    FAQAdapter adapter;
    EditText message;
    FloatingActionButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        list = new ArrayList<>();

        cid = getIntent().getStringExtra("cid");
        vid = getIntent().getStringExtra("vid");
        title = getIntent().getStringExtra("title");

        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progressBar2);
        grid = findViewById(R.id.grid);
        send = findViewById(R.id.floatingActionButton);
        message = findViewById(R.id.editText3);

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

        adapter = new FAQAdapter(this , list);
        manager = new GridLayoutManager(this , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String m = message.getText().toString();

                if (m.length() > 0)
                {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<String> call = cr.submitFeedback(m , cid , SharePreferenceUtils.getInstance().getString("id") , vid);

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {


                            message.setText("");
                            progress.setVisibility(View.GONE);

                            loadData();

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }

            }
        });

        loadData();


    }

    void loadData()
    {
        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<feedbackBean> call = cr.getFeedback(cid);

        call.enqueue(new Callback<feedbackBean>() {
            @Override
            public void onResponse(Call<feedbackBean> call, Response<feedbackBean> response) {



                adapter.setData(response.body().getData());
                progress.setVisibility(View.GONE);

                if (response.body().getData().size() > 0)
                {
                    grid.smoothScrollToPosition(response.body().getData().size() - 1);
                }




            }

            @Override
            public void onFailure(Call<feedbackBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {
        Context context;
        List<Datum> list = new ArrayList<>();


        public FAQAdapter(Context context , List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.feedback_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Datum item = list.get(position);

            if (item.getType().equals("user"))
            {
                holder.user.setVisibility(View.VISIBLE);
                holder.vendor.setVisibility(View.GONE);
                holder.mess1.setText(item.getMess());
                holder.date1.setText(item.getTime() + " | " + item.getDate());
            }
            else
            {
                holder.user.setVisibility(View.GONE);
                holder.vendor.setVisibility(View.VISIBLE);
                holder.mess2.setText(item.getMess());
                holder.date2.setText(item.getTime() + " | " + item.getDate());
            }



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            LinearLayout user , vendor;
            TextView mess1 , mess2 , date1 , date2;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                user = itemView.findViewById(R.id.user);
                vendor = itemView.findViewById(R.id.vendor);
                mess1 = itemView.findViewById(R.id.mess1);
                mess2 = itemView.findViewById(R.id.mess2);
                date1 = itemView.findViewById(R.id.date1);
                date2 = itemView.findViewById(R.id.date2);

            }
        }
    }

}
