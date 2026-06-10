package com.example.kalkulatorwycieczki;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editDays = findViewById(R.id.editDays);
        RadioGroup radioGroupTransport = findViewById(R.id.radioGroupTransport);
        CheckBox checkBreakfast = findViewById(R.id.checkBreakfast);
        CheckBox checkGuide = findViewById(R.id.checkGuide);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> {
            radioGroupTransport.setSelected(false);
            checkBreakfast.setChecked(false);
            checkGuide.setChecked(false);
            editDays.setText("");
                });
        buttonCalculate.setOnClickListener(v -> {
            String daysStr = editDays.getText().toString();
            if (daysStr.isEmpty()) {
                Toast.makeText(this, "Wpisz liczbę dni!", Toast.LENGTH_SHORT).show();
                return;
            }else if(Integer.parseInt(daysStr)>30 ){
                Toast.makeText(this, "Wpisz liczbę dni mniejszą od 30!", Toast.LENGTH_SHORT).show();
                return;
            }else{
                Toast.makeText(this, "Wpisz liczbę dni większą od 1: " + daysStr, Toast.LENGTH_SHORT).show();
            }



            int days = Integer.parseInt(daysStr);
            int totalCost = days * 120;

            int selectedTransportId = radioGroupTransport.getCheckedRadioButtonId();
            if (selectedTransportId == R.id.radioOwn) {
                totalCost += 0;
            } else if (selectedTransportId == R.id.radioBus) {
                totalCost += 100;
            } else if (selectedTransportId == R.id.radioPlane) {
                totalCost += 500;
            } else {
                Toast.makeText(this, "Wybierz rodzaj transportu!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkBreakfast.isChecked()) {
                totalCost += days * 30;
            }

            if (checkGuide.isChecked()) {
                totalCost += 150;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Podsumowanie wycieczki");
            builder.setMessage("Liczba dni: " + days + "\nCałkowity koszt: " + totalCost + " zł");
            builder.setPositiveButton("OK", null);
            builder.show();
        });
    }
}
