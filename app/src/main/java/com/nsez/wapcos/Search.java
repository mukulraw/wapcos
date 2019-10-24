package com.nsez.wapcos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nsez.wapcos.searchPOJO.Datum;
import com.nsez.wapcos.searchPOJO.searchBean;

import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Search extends AppCompatActivity {

    EditText search;
    RecyclerView grid;
    GridLayoutManager manager;
    ProgressBar progress;
    List<Datum> list;
    FAQAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        list = new ArrayList<>();

        search = findViewById(R.id.search);
        grid = findViewById(R.id.grid);
        progress = findViewById(R.id.progress);


        adapter = new FAQAdapter(this , list);
        manager = new GridLayoutManager(this , 1);


        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0)
                {


                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Log.d("uid" , SharePreferenceUtils.getInstance().getString("id"));
                    Log.d("data" , s.toString());


                    Call<searchBean> call = cr.search(SharePreferenceUtils.getInstance().getString("id") , s.toString());

                    call.enqueue(new Callback<searchBean>() {
                        @Override
                        public void onResponse(Call<searchBean> call, Response<searchBean> response) {

                            adapter.setData(response.body().getData());

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<searchBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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
            View view = inflater.inflate(R.layout.search_item_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Datum item = list.get(position);

            holder.uid.setText(item.getUniqueId());
            holder.name.setText(item.getName());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , ComplaintDetailsUser.class);
                    intent.putExtra("cid" , item.getId());
                    intent.putExtra("title" , item.getUniqueId());
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView uid , name;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                uid = itemView.findViewById(R.id.textView5);
                name = itemView.findViewById(R.id.textView13);


            }
        }
    }

}
