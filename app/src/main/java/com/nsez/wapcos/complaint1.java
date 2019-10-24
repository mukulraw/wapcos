package com.nsez.wapcos;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nsez.wapcos.getConmplainPOJO.Datum;
import com.nsez.wapcos.getConmplainPOJO.getComplainBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class complaint1 extends Fragment {

    RecyclerView grid;

    ProgressBar progress;

    GridLayoutManager manager;
    FAQAdapter adapter;
    List<Datum> list;
    FloatingActionButton add;
    TextView date;
    String dd;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complaint1, container, false);

        list = new ArrayList<>();

        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progress);
        add = view.findViewById(R.id.add);
        date = view.findViewById(R.id.date);


        adapter = new FAQAdapter(getContext() , list);

        manager = new GridLayoutManager(getContext() , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);




        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.date_dialog);
                dialog.show();


                final DatePicker picker = dialog.findViewById(R.id.date);
                Button ok = dialog.findViewById(R.id.ok);

                long now = System.currentTimeMillis() - 1000;
                picker.setMaxDate(now);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int year = picker.getYear();
                        int month = picker.getMonth();
                        int day = picker.getDayOfMonth();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String strDate = format.format(calendar.getTime());

                        dialog.dismiss();

                        date.setText("Date - " + strDate + " (click to change)");

                        dd = strDate;

                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getActivity().getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<getComplainBean> call = cr.getComplain1(SharePreferenceUtils.getInstance().getString("id") , dd);

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
                });


            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        Log.d("dddd", formattedDate);

        date.setText("Date - " + formattedDate + " (click to change)");

        dd = formattedDate;





        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getActivity().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<getComplainBean> call = cr.getComplain1(SharePreferenceUtils.getInstance().getString("category") , dd);

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
            View view = inflater.inflate(R.layout.faq_item_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Datum item = list.get(position);

            holder.uid.setText(item.getUniqueId());
            holder.name.setText(item.getName());
            holder.category.setText(item.getCategory());
            holder.date.setText(item.getCreatedDate());
            holder.complain.setText(item.getComplain());
            holder.ack.setText(item.getAkdDate());
            holder.closure.setText(item.getExpClDate());
            holder.status.setText(item.getStatus());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , ComplaintDetailsVendors.class);
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

            TextView uid , name , category , date , complain , ack , closure , status;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                uid = itemView.findViewById(R.id.textView5);
                name = itemView.findViewById(R.id.textView13);
                category = itemView.findViewById(R.id.textView14);
                date = itemView.findViewById(R.id.textView15);
                complain = itemView.findViewById(R.id.textView16);
                ack = itemView.findViewById(R.id.textView17);
                closure = itemView.findViewById(R.id.textView18);
                status = itemView.findViewById(R.id.textView6);


            }
        }
    }



}
