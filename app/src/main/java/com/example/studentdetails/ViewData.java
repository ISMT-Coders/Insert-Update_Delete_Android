package com.example.studentdetails;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {
    RecyclerView recyclerView;
    Db_Helper mydb;
    FloatingActionButton add_button;
    ArrayList<String> roll_no, stdName, stdAddress;
    CustomAdapter customAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.addButton);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewData.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mydb = new Db_Helper(ViewData.this);
        roll_no = new ArrayList<>();
        stdName = new ArrayList<>();
        stdAddress = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(this,ViewData.this, roll_no, stdName, stdAddress);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewData.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = mydb.readAllData();
        if (cursor.getCount() ==0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                roll_no.add(cursor.getString(0));
                stdName.add(cursor.getString(1));
                stdAddress.add(cursor.getString(2));
            }
        }
    }
}
