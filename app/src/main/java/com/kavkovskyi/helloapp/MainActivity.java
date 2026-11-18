package com.kavkovskyi.helloapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_COUNT = "click_count";

    private TextView textGreeting;
    private Button buttonStart;
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

        textGreeting = findViewById(R.id.text_greeting);
        buttonStart = findViewById(R.id.button_start);
        buttonClick = findViewById(R.id.button_click);
        buttonReset = findViewById(R.id.button_reset);

        // savedInstanceState != null -> Activity wraca po zniszczeniu (np. po obrocie)
        if (savedInstanceState != null) {
            clickCount = savedInstanceState.getInt(KEY_COUNT, 0);
            if (clickCount > 0) {
                textGreeting.setText(getString(R.string.clicked_times, clickCount));
            }
        }

        Log.d(TAG, "onCreate: ekran utworzony, licznik = " + clickCount);

        buttonStart.setOnClickListener(v -> {
            Log.d(TAG, "Kliknięto Start");
            Toast.makeText(this, R.string.coming_soon, Toast.LENGTH_SHORT).show();
        });

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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNT, clickCount);
        Log.d(TAG, "onSaveInstanceState: zapisano licznik = " + clickCount);
    }

    // --- Cykl życia Activity (zadanie 3): każda metoda zaczyna od super.onXxx() ---

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
