package com.example.firbasemaster;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tv_name, tv_address, tv_age;
    View view;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_address = itemView.findViewById(R.id.tv_address);
        tv_age = itemView.findViewById(R.id.tv_age);
        view = itemView;
    }
}
