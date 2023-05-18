package com.example.studentdetails;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class update extends AppCompatActivity {

    Button btn_Update;
    EditText txt_rollNum, txt_name, txt_address;
    String RollNum, name, address;
    Db_Helper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        btn_Update = findViewById(R.id.btn_update);
        txt_rollNum = findViewById(R.id.rollNum2);
        txt_address = findViewById(R.id.address2);
        txt_name = findViewById(R.id.name2);

        // Disable the roll number EditText field
        txt_rollNum.setEnabled(false);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Db_Helper myDB = new Db_Helper(update.this);
                RollNum = txt_rollNum.getText().toString().trim();
                name = txt_name.getText().toString().trim();
                address = txt_address.getText().toString().trim();
                myDB.updataData(RollNum, name, address);

                //starting new activity immediatly after executing update queries
                Intent intent = new Intent(update.this, ViewData.class);
                startActivity(intent);
            }
        });

    }
    void getAndSetIntentData() {
        if (getIntent().hasExtra("Roll_Num") && getIntent().hasExtra("name") && getIntent().hasExtra("address")) {
            RollNum = getIntent().getStringExtra("Roll_Num");
            name = getIntent().getStringExtra("name");
            address = getIntent().getStringExtra("address");

            txt_rollNum.setText(RollNum);
            txt_name.setText(name);
            txt_address.setText(address);

            Log.d("", RollNum + " " + name + " " + address);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

}