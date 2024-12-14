package com.example.blockfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void enter (View v){
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int dob_date = datePicker.getDayOfMonth();
        int dob_month = datePicker.getMonth();
        int dob_year = datePicker.getYear();
        Intent goToSecond = new Intent();
        goToSecond.setClass(this, SecondActivity.class);
        goToSecond.putExtra("dob_date", dob_date);
        goToSecond.putExtra("dob_month", dob_month);
        goToSecond.putExtra("dob_year", dob_year);
        startActivity(goToSecond);
        finish();
    }
}