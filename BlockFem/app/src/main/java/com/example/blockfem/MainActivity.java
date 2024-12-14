package com.example.blockfem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences preferences = getSharedPreferences("ColOR_PREF", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
            if (preferences.contains(("colorID")))
                layout.setBackgroundColor(preferences.getInt("colorID",0));
        RadioGroup radioGroupColours = (RadioGroup) findViewById(R.id.radioGroupColours);
        radioGroupColours.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int colorcode = 0;
                switch (checkedId) {
                    case R.id.blueRadioButton:
                        colorcode = Color.BLUE;
                        break;
                    case R.id.magentaeRadioButton:
                        colorcode = Color.MAGENTA;
                        break;
                    case R.id.yellowRadioButton:
                        colorcode = Color.YELLOW;
                        break;
                }
                layout.setBackgroundColor(colorcode);
                editor.putInt("colorId", colorcode);
                editor.commit();
            }
        });
    }
}