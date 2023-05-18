package com.example.studentdetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {
    RecyclerView recyclerView;
    Db_Helper mydb;
    FloatingActionButton add_button;
    ArrayList<String> roll_no, stdName, stdAddress;
    CustomAdapter customAdapter;
    ImageView empty_imageview;
    TextView no_data;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.addButton);
        empty_imageview = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.no_Data_txt);

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
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                roll_no.add(cursor.getString(0));
                stdName.add(cursor.getString(1));
                stdAddress.add(cursor.getString(2));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Data?");
        builder.setMessage("Are you sure to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Db_Helper myDB = new Db_Helper(ViewData.this);
                myDB.deleteAllData();

                //refresh activity
                Intent intent = new Intent(ViewData.this, ViewData.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
