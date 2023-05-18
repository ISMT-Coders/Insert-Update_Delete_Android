package com.example.studentdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button insert_btn, btn_viewData;
    EditText txt_rollNum, txt_name, txt_address;
    Db_Helper mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findId();
        mdb = new Db_Helper(this);

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rollNum = txt_rollNum.getText().toString().trim();
                String name = txt_name.getText().toString().trim();
                String address = txt_address.getText().toString().trim();

                // Check if any of the fields are empty
                if (rollNum.isEmpty() || name.isEmpty() || address.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                // Check if the data already exists (based on your specific condition)
                else if (mdb.dataExists(rollNum)) {
                    Toast.makeText(MainActivity.this, "Data already exists", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                boolean inserted = mdb.insertData(rollNum, name, address);

                if (inserted) {
                    Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data Insertion Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewData.class);
                startActivity(intent);
            }
        });
    }

    private void findId() {
        insert_btn = findViewById(R.id.btn_insert);
        txt_rollNum = findViewById(R.id.rollNum);
        txt_name = findViewById(R.id.name);
        txt_address = findViewById(R.id.address);
        btn_viewData = findViewById(R.id.btn_ViewData);
    }
}
