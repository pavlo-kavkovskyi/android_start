package com.kavkovskyi.helloapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CatalogActivity extends AppCompatActivity {

    private static final String KEY_RESULT = "result";

    private EditText editName;
    private CheckBox checkConsent;
    // CompoundButton = wspólna klasa bazowa CheckBox i Switch (motyw Material podmienia Switch na własną wersję)
    private CompoundButton switchNotifications;
    private RadioGroup radioSize;
    private SeekBar seekVolume;
    private TextView textVolume;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_catalog);
        EdgeToEdgeHelper.apply(findViewById(R.id.main));

        editName = findViewById(R.id.edit_name);
        checkConsent = findViewById(R.id.check_consent);
        switchNotifications = findViewById(R.id.switch_notifications);
        radioSize = findViewById(R.id.radio_size);
        seekVolume = findViewById(R.id.seek_volume);
        textVolume = findViewById(R.id.text_volume);
        textResult = findViewById(R.id.text_result);
        Button buttonCollect = findViewById(R.id.button_collect);

        showVolume(seekVolume.getProgress());

        // SeekBar ma trzy metody w interfejsie -> klasa anonimowa, nie lambda
        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
                showVolume(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb) { }

            @Override
            public void onStopTrackingTouch(SeekBar sb) { }
        });

        buttonCollect.setOnClickListener(v -> collect());

        if (savedInstanceState != null) {
            String result = savedInstanceState.getString(KEY_RESULT);
            if (result != null) {
                textResult.setText(result);
                textResult.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (textResult.getVisibility() == View.VISIBLE) {
            outState.putString(KEY_RESULT, textResult.getText().toString());
        }
    }

    private void showVolume(int progress) {
        textVolume.setText(getString(R.string.label_volume, progress));
    }

    private void collect() {
        String name = editName.getText().toString().trim();
        if (name.isEmpty()) {
            name = getString(R.string.answer_none);
        }

        String size;
        int checkedId = radioSize.getCheckedRadioButtonId();
        if (checkedId == R.id.radio_s) {
            size = getString(R.string.size_s);
        } else if (checkedId == R.id.radio_m) {
            size = getString(R.string.size_m);
        } else if (checkedId == R.id.radio_l) {
            size = getString(R.string.size_l);
        } else {
            size = getString(R.string.answer_none);
        }

        textResult.setText(getString(R.string.result_catalog,
                name,
                yesNo(checkConsent.isChecked()),
                yesNo(switchNotifications.isChecked()),
                size,
                seekVolume.getProgress()));
        textResult.setVisibility(View.VISIBLE);
    }

    private String yesNo(boolean value) {
        return getString(value ? R.string.answer_yes : R.string.answer_no);
    }
}
