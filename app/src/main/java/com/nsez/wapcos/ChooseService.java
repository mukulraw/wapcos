package com.nsez.wapcos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class ChooseService extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service);

        toolbar = findViewById(R.id.toolbar);
        grid = findViewById(R.id.grid);

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
        toolbar.setTitle("Add Complaint");

    }

    class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {
        Context context;

        int[] images = {
                R.drawable.security,
                R.drawable.lifts,
                R.drawable.garden,
                R.drawable.water,
                R.drawable.drain,
                R.drawable.lighting,
                R.drawable.networking
        };

        String[] titles = {
          "Security",
          "Lifts",
          "Garden/ Horticulture",
          "Water Supply Lines",
          "Sewer/ Drains",
          "Common Area Electricity/ Firesystem/ DG Set",
          "IT Network Support"
        };

        public FAQAdapter(Context context) {
            this.context = context;
        }

      /*  public void setData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }*/

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.service_item_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            /*Datum item = list.get(position);

            holder.ques.setText(item.getRelation());
            holder.answer.setText(item.getName() + " (" + item.getAge() + ")");*/


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , ComplaintDetailsUser.class);
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return titles.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView title;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);




            }
        }
    }

}
