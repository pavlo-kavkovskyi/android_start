package com.kavkovskyi.helloapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Zadanie 6: formularz zamówienia w domenie "4A Sklep".
 * Pola: tekst, e-mail, liczba, lista (Spinner), radio, checkbox – każde z walidacją.
 * Stan po obrocie przywracany jest automatycznie, bo każde pole ma android:id.
 */
public class OrderActivity extends AppCompatActivity {

    private static final int MAX_QUANTITY = 20;

    private EditText editName;
    private EditText editEmail;
    private EditText editQuantity;
    private Spinner spinnerProduct;
    private TextView textProductError;
    private RadioGroup radioDelivery;
    private TextView textDeliveryError;
    private CheckBox checkTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        EdgeToEdgeHelper.apply(findViewById(R.id.main));

        editName = findViewById(R.id.edit_order_name);
        editEmail = findViewById(R.id.edit_order_email);
        editQuantity = findViewById(R.id.edit_order_quantity);
        spinnerProduct = findViewById(R.id.spinner_product);
        textProductError = findViewById(R.id.text_product_error);
        radioDelivery = findViewById(R.id.radio_delivery);
        textDeliveryError = findViewById(R.id.text_delivery_error);
        checkTerms = findViewById(R.id.check_terms);
        Button buttonSave = findViewById(R.id.button_save);
        Button buttonClear = findViewById(R.id.button_order_clear);

        // wybór sposobu dostawy / akceptacja regulaminu zdejmują swój błąd od razu
        radioDelivery.setOnCheckedChangeListener((group, checkedId) ->
                textDeliveryError.setVisibility(View.GONE));
        checkTerms.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkTerms.setError(null);
            }
        });

        buttonSave.setOnClickListener(v -> {
            if (validate()) {
                save();
            }
        });
        buttonClear.setOnClickListener(v -> clear());
    }

    // Sprawdzamy wszystkie pola naraz, żeby użytkownik zobaczył wszystkie błędy
    private boolean validate() {
        boolean valid = true;

        String name = editName.getText().toString().trim();
        if (name.isEmpty()) {
            editName.setError(getString(R.string.error_required));
            valid = false;
        } else if (name.length() < 3) {
            editName.setError(getString(R.string.error_name));
            valid = false;
        }

        String email = editEmail.getText().toString().trim();
        if (email.isEmpty()) {
            editEmail.setError(getString(R.string.error_required));
            valid = false;
        } else if (!looksLikeEmail(email)) {
            editEmail.setError(getString(R.string.error_email));
            valid = false;
        }

        String quantityText = editQuantity.getText().toString().trim();
        if (quantityText.isEmpty()) {
            editQuantity.setError(getString(R.string.error_required));
            valid = false;
        } else {
            try {
                int quantity = Integer.parseInt(quantityText);
                if (quantity < 1 || quantity > MAX_QUANTITY) {
                    editQuantity.setError(getString(R.string.error_quantity));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                editQuantity.setError(getString(R.string.error_quantity));
                valid = false;
            }
        }

        // pozycja 0 na liście to podpowiedź "Wybierz produkt…"
        if (spinnerProduct.getSelectedItemPosition() == 0) {
            textProductError.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            textProductError.setVisibility(View.GONE);
        }

        if (radioDelivery.getCheckedRadioButtonId() == View.NO_ID) {
            textDeliveryError.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            textDeliveryError.setVisibility(View.GONE);
        }

        if (!checkTerms.isChecked()) {
            checkTerms.setError(getString(R.string.error_terms));
            valid = false;
        }

        return valid;
    }

    // Prosta kontrola: coś@coś.coś, bez spacji i bez drugiego '@'
    private boolean looksLikeEmail(String email) {
        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        return at > 0
                && email.indexOf('@', at + 1) == -1
                && dot > at + 1
                && dot < email.length() - 1
                && !email.contains(" ");
    }

    private void save() {
        String product = (String) spinnerProduct.getSelectedItem();
        String delivery = radioDelivery.getCheckedRadioButtonId() == R.id.radio_courier
                ? getString(R.string.radio_courier)
                : getString(R.string.radio_pickup);

        String summary = getString(R.string.order_summary,
                product,
                Integer.parseInt(editQuantity.getText().toString().trim()),
                editName.getText().toString().trim(),
                editEmail.getText().toString().trim(),
                delivery);
        Toast.makeText(this, summary, Toast.LENGTH_LONG).show();
    }

    private void clear() {
        editName.setText("");
        editEmail.setText("");
        editQuantity.setText("");
        editName.setError(null);
        editEmail.setError(null);
        editQuantity.setError(null);
        spinnerProduct.setSelection(0);
        textProductError.setVisibility(View.GONE);
        radioDelivery.clearCheck();
        textDeliveryError.setVisibility(View.GONE);
        checkTerms.setChecked(false);
        checkTerms.setError(null);
        editName.requestFocus();
    }
}
