package com.kavkovskyi.helloapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BmiActivity extends AppCompatActivity {

    private static final String KEY_RESULT = "result";

    private EditText editWeight;
    private EditText editHeight;
    private RadioGroup radioUnits;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi);
        EdgeToEdgeHelper.apply(findViewById(R.id.main));

        editWeight = findViewById(R.id.edit_weight);
        editHeight = findViewById(R.id.edit_height);
        radioUnits = findViewById(R.id.radio_units);
        textResult = findViewById(R.id.text_result);
        Button buttonCalc = findViewById(R.id.button_calc);
        Button buttonClear = findViewById(R.id.button_clear);

        buttonCalc.setOnClickListener(v -> {
            if (validate()) {
                calculate();
            }
        });
        buttonClear.setOnClickListener(v -> clear());

        // zmiana jednostek: nowe podpowiedzi, ponowne sprawdzenie wagi w nowym zakresie
        radioUnits.setOnCheckedChangeListener((group, checkedId) -> {
            updateHints();
            checkWeightLive();
            editHeight.setError(null);
        });
        updateHints();

        // TextWatcher ma trzy metody -> klasa anonimowa; walidacja wagi na żywo (zadanie 5)
        editWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                checkWeightLive();
            }
        });

        // EditText i CheckBox (mają id) odtworzą się same; wynik w TextView musimy odtworzyć sami
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

    // Puste pole nie jest błędem w trakcie pisania (na "wymagane" sprawdza dopiero validate());
    // poza zakresem -> setError od razu, z powrotem w zakresie -> setError(null)
    private void checkWeightLive() {
        if (editWeight.getText().toString().trim().isEmpty()) {
            editWeight.setError(null);
            return;
        }
        if (validateWeight()) {
            editWeight.setError(null);
        }
    }

    // Sprawdzamy wszystkie pola (bez przerywania na pierwszym), żeby użytkownik zobaczył wszystkie błędy naraz
    private boolean validate() {
        boolean weightOk = validateWeight();
        boolean heightOk = validateHeight();
        return weightOk && heightOk;
    }

    private boolean isMetric() {
        return radioUnits.getCheckedRadioButtonId() == R.id.radio_metric;
    }

    private void updateHints() {
        boolean metric = isMetric();
        editWeight.setHint(metric ? R.string.hint_weight : R.string.hint_weight_lb);
        editHeight.setHint(metric ? R.string.hint_height : R.string.hint_height_in);
    }

    // Zakresy w jednostkach, które widzi użytkownik (20–300 kg ≈ 45–660 lb, 50–250 cm ≈ 20–98 in)
    private boolean validateWeight() {
        return isMetric()
                ? validateField(editWeight, 20, 300, R.string.error_weight_range)
                : validateField(editWeight, 45, 660, R.string.error_weight_range_lb);
    }

    private boolean validateHeight() {
        return isMetric()
                ? validateField(editHeight, 50, 250, R.string.error_height_range)
                : validateField(editHeight, 20, 98, R.string.error_height_range_in);
    }

    private boolean validateField(EditText field, double min, double max, int rangeErrorRes) {
        String text = field.getText().toString().trim();
        if (text.isEmpty()) {
            field.setError(getString(R.string.error_required));
            return false;
        }

        double value;
        try {
            // klawiatura numeryczna w polskim języku może wstawić przecinek zamiast kropki
            value = Double.parseDouble(text.replace(',', '.'));
        } catch (NumberFormatException e) {
            // np. samo "." – numberDecimal je przepuszcza
            field.setError(getString(R.string.error_number));
            return false;
        }

        if (value < min || value > max) {
            field.setError(getString(rangeErrorRes));
            return false;
        }
        return true;
    }

    private double readNumber(EditText field) {
        return Double.parseDouble(field.getText().toString().trim().replace(',', '.'));
    }

    private void calculate() {
        double weight = readNumber(editWeight);
        double heightCm = readNumber(editHeight);

        if (!isMetric()) {
            weight = weight * 0.4536;     // funty -> kg
            heightCm = heightCm * 2.54;   // cale -> cm
        }

        double heightM = heightCm / 100;
        double bmi = weight / (heightM * heightM);

        textResult.setText(getString(R.string.result_format, bmi, categoryFor(bmi)));
        textResult.setVisibility(View.VISIBLE);
    }

    private String categoryFor(double bmi) {
        if (bmi < 18.5) return getString(R.string.bmi_under);
        if (bmi < 25) return getString(R.string.bmi_normal);
        if (bmi < 30) return getString(R.string.bmi_over);
        return getString(R.string.bmi_obese);
    }

    private void clear() {
        editWeight.setText("");
        editHeight.setText("");
        editWeight.setError(null);
        editHeight.setError(null);
        textResult.setVisibility(View.GONE);
        editWeight.requestFocus();
    }
}
