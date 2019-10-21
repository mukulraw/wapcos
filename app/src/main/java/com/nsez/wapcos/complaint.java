package com.nsez.wapcos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class complaint extends Fragment {

    RecyclerView grid;

    ProgressBar progress;

    GridLayoutManager manager;
    FAQAdapter adapter;

    FloatingActionButton add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complaint, container, false);



        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progress);
        add = view.findViewById(R.id.add);



        adapter = new FAQAdapter(getContext());

        manager = new GridLayoutManager(getContext() , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        




        return view;
    }

    class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {
        Context context;


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
            View view = inflater.inflate(R.layout.faq_item_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            /*Datum item = list.get(position);

            holder.ques.setText(item.getRelation());
            holder.answer.setText(item.getName() + " (" + item.getAge() + ")");*/
        }

        @Override
        public int getItemCount() {
            return 12;
        }

        class ViewHolder extends RecyclerView.ViewHolder {



            public ViewHolder(@NonNull View itemView) {
                super(itemView);



            }
        }
    }



}
