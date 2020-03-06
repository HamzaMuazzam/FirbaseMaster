package com.example.firbasemaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<PojoClasss> list;

    public MyAdapter(Context context, List<PojoClasss> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PojoClasss model = list.get(position);
        holder.tv_name.setText(model.getName());
        holder.tv_address.setText(model.getAddress());
        holder.tv_age.setText(model.getAge());




        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecyclerActivity.class);
                intent.putExtra("KEY", model.getKey());
                context.startActivity(intent);
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//to remove user
                Task<Void> voidTask = MyUtils.removeValue(model.getKey());
                voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "value Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
