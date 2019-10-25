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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nsez.wapcos.getConmplainPOJO.Datum;
import com.nsez.wapcos.getConmplainPOJO.getComplainBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Notification extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView grid;
    GridLayoutManager manager;
    ProgressBar progress;
    FAQAdapter adapter;
    List<Datum> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar2);
        grid = findViewById(R.id.grid);
        progress = findViewById(R.id.progressBar3);


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
        toolbar.setTitle("Notifications");

        adapter = new FAQAdapter(this , list);
        manager = new GridLayoutManager(this , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);





        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<getComplainBean> call = cr.vendor_noti(SharePreferenceUtils.getInstance().getString("category"));

        call.enqueue(new Callback<getComplainBean>() {
            @Override
            public void onResponse(Call<getComplainBean> call, Response<getComplainBean> response) {

                if (response.body().getStatus().equals("1"))
                {
                    adapter.setData(response.body().getData());
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<getComplainBean> call, Throwable t) {
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
            View view = inflater.inflate(R.layout.noti_item_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Datum item = list.get(position);

            holder.uid.setText(item.getUniqueId());
            holder.name.setText(item.getName());
            holder.status.setText(item.getStatus());




        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView uid , name , status;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                uid = itemView.findViewById(R.id.textView5);
                name = itemView.findViewById(R.id.textView13);
                status = itemView.findViewById(R.id.textView6);


            }
        }
    }

}
