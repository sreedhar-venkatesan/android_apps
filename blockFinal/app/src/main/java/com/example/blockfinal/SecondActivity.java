package com.example.blockfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;
import java.util.Calendar;
import android.widget.Toast;
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent caller = getIntent();
        int dob_date = caller.getIntExtra("dob_date", 0);
        int dob_month = caller.getIntExtra("dob_month", 0);
        int dob_year = caller.getIntExtra("dob_year", 0);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Your age is: "+ (Calendar.getInstance().get(Calendar.YEAR)-dob_year) + " years, "
        + (Calendar.getInstance().get(Calendar.MONTH)-dob_month) + " months, " + (Calendar.getInstance().get(Calendar.DATE)-dob_date) + " days.");
    }
    public void back (View v){
        Intent goToFirst = new Intent();
        goToFirst.setClass(this, FirstActivity.class);
        startActivity(goToFirst);
        finish();
    }
}