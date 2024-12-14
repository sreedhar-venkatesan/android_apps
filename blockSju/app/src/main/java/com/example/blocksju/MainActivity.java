package com.example.blocksju;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.NumberPicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    NumberPicker possibilities;
    WebView webview;
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
        possibilities = (NumberPicker) findViewById(R.id.numberPicker);
        webview = (WebView) findViewById(R.id.webView);
        String[] possibilitiesStrings = {
                "Android",
                "Checklist text-input field",
                "Coursera",
                "Suplec"
        };
        possibilities.setDisplayedValues(possibilitiesStrings);
        possibilities.setMinValue(0);
        possibilities.setMaxValue(possibilitiesStrings.length-1);
    }
    public void navigate(View v){
        int choice = possibilities.getValue();
        if (choice ==0) {
           // webview.setWebViewClient(new WebViewClient());
            webview.loadUrl("file:///android_asset/android.html");
        }
        else if (choice ==1)
        webview.loadUrl("file:///android_asset/checklist.html");
        else if (choice ==2)
        webview.loadUrl("http://www.coursera.org");
        else if (choice ==3)
        webview.loadUrl("file:///android_asset/supelec.html");

    }
}