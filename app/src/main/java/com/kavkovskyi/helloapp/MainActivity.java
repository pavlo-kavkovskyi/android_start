package com.kavkovskyi.helloapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView textGreeting;
    private Button buttonClick;
    private Button buttonReset;
    private int clickCount = 0;

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

        Log.d(TAG, "onCreate: ekran utworzony, licznik = " + clickCount);

        textGreeting = findViewById(R.id.text_greeting);
        buttonClick = findViewById(R.id.button_click);
        buttonReset = findViewById(R.id.button_reset);

        buttonClick.setOnClickListener(v -> {
            clickCount++;
            textGreeting.setText(getString(R.string.clicked_times, clickCount));
            Toast.makeText(this, R.string.clicked, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Kliknięto, licznik = " + clickCount);
        });

        buttonReset.setOnClickListener(v -> {
            clickCount = 0;
            textGreeting.setText(R.string.greeting);
            Log.d(TAG, "Reset, licznik = " + clickCount);
        });
    }
}
