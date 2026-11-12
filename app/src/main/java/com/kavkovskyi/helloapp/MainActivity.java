package com.kavkovskyi.helloapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String  TAG = "MainActivity";
    private TextView textGreeting;
    private  Button buttonClick;
    private int clickCount = 0;
    Log.d(TAG, "Kliknięto, licznik = " + clickCount);

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

        textGreeting = findViewById(R.id.text_greeting);
        buttonClick = findViewById(R.id.button_click);

        buttonClick.setOnClickListener(v -> {
            clickCount++;
            textGreeting.setText("Kliknięto " + clickCount + " razy");
            Toast.makeText(this, R.string.clicked, Toast.LENGTH_SHORT).show();
        });
    }
}