package com.nsez.wapcos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Objects;

public class addComp extends Fragment {

    Toolbar toolbar;
    RecyclerView grid;
    FAQAdapter adapter;
    GridLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_choose_service , container , false);

        toolbar = view.findViewById(R.id.toolbar);
        grid = view.findViewById(R.id.grid);

        adapter = new FAQAdapter(getContext());
        manager = new GridLayoutManager(getContext(), 3);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        return view;
    }

    class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {
        Context context;

        int[] images = {
                R.drawable.security2,
                R.drawable.lifts,
                R.drawable.garden,
                R.drawable.water,
                R.drawable.drain,
                R.drawable.lighting,
                0,
                R.drawable.networking
        };

        String[] titles = {
                "Security",
                "Lifts",
                "Garden/ Horticulture",
                "Water Supply Lines",
                "Sewer/ Drains",
                "Common Area Electricity/ Firesystem/ DG Set",
                "",
                "IT Network Support"
        };

        String[] ids = {
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "",
                "7"
        };

        String[] tags = {
                "SC00",
                "LT00",
                "GH00",
                "WS00",
                "SD00",
                "CA00",
                "",
                "IT00"
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



            if (ids[position].length() > 0) {
                holder.title.setText(titles[position]);

            }

            if (ids[position].length() > 0)
            {
                String uri = "drawable://" + images[position];
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(uri, holder.image);
            }




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ids[position].length() > 0)
                    {
                        Intent intent = new Intent(context, ComplaintForm.class);
                        intent.putExtra("title", titles[position]);
                        intent.putExtra("id", ids[position]);
                        intent.putExtra("tag", tags[position]);
                        startActivity(intent);
                    }



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
