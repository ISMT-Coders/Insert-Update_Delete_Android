package com.example.studentdetails;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList roll_no, stdName, stdAddress;

    CustomAdapter(Context context,
                  Activity activity,
                  ArrayList roll_no,
                  ArrayList stdName,
                  ArrayList stdAddress){
        this.activity = activity;
        this.context = context;
        this.roll_no = roll_no;
        this.stdName = stdName;
        this.stdAddress = stdAddress;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.rollNumber.setText(String.valueOf(roll_no.get(position)));
        holder.name.setText(String.valueOf(stdName.get(position)));
        holder.address.setText(String.valueOf(stdAddress.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, update.class);
                intent.putExtra("Roll_Num",String.valueOf(roll_no.get(position)));
                intent.putExtra("name",String.valueOf(stdName.get(position)));
                intent.putExtra("address",String.valueOf(stdAddress.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roll_no.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rollNumber, name, address;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rollNumber = itemView.findViewById(R.id.rollnumbertxt);
            name = itemView.findViewById(R.id.textView2);
            address = itemView.findViewById(R.id.textView3);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
