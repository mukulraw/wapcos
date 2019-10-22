package com.nsez.wapcos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Objects;

public class ChooseService extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView grid;
    FAQAdapter adapter;
    GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service);

        toolbar = findViewById(R.id.toolbar);
        grid = findViewById(R.id.grid);

        adapter = new FAQAdapter(this);
        manager = new GridLayoutManager(this , 3);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

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
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            /*Datum item = list.get(position);

            holder.ques.setText(item.getRelation());
            holder.answer.setText(item.getName() + " (" + item.getAge() + ")");*/

            holder.title.setText(titles[position]);

            String uri = "drawable://" + images[position];

            ImageLoader loader = ImageLoader.getInstance();

            loader.displayImage(uri , holder.image);

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context , ComplaintForm.class);
                    intent.putExtra("title" , titles[position]);
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


                image = itemView.findViewById(R.id.image);
                title = itemView.findViewById(R.id.title);


            }
        }
    }

}
