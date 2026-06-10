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
            radioGroupTransport.clearCheck();
            checkBreakfast.setChecked(false);
            checkGuide.setChecked(false);
            editDays.setText("");
            Toast.makeText(this, "Formularz został wyczyszczony!", Toast.LENGTH_SHORT).show();
                });
        buttonCalculate.setOnClickListener(v -> {
            String daysStr = editDays.getText().toString();
            if (daysStr.isEmpty()) {
                Toast.makeText(this, "Wpisz liczbę dni!", Toast.LENGTH_SHORT).show();
                return;
            }else if(Integer.parseInt(daysStr)>30 ){
                Toast.makeText(this, "Wpisz liczbę dni mniejszą od 30!", Toast.LENGTH_SHORT).show();
                return;
            }else if(Integer.parseInt(daysStr)<1){
                Toast.makeText(this, "Wpisz liczbę dni większą od 1: " + daysStr, Toast.LENGTH_SHORT).show();
            }



            int days = Integer.parseInt(daysStr);
            int baseCost = days * 120;
            int totalCost = baseCost;
            int transportCost = 0;
            String selectedTransport;
            int selectedTransportId = radioGroupTransport.getCheckedRadioButtonId();
            String additional = "";
            if (selectedTransportId == R.id.radioOwn) {

                selectedTransport = "Dojazd własny";
                transportCost = 0;
            } else if (selectedTransportId == R.id.radioBus) {
                transportCost = 100;
                selectedTransport = "Autokar";
            } else if (selectedTransportId == R.id.radioPlane) {
                transportCost = 500;
                selectedTransport = "Samolot";
            } else {
                Toast.makeText(this, "Wybierz rodzaj transportu!", Toast.LENGTH_SHORT).show();
                return;
            }
            totalCost += transportCost;
            if (checkGuide.isChecked()) {
                totalCost += 150;
                additional += "Przewodnik: (+ 150 zł) \n";
            }
            if (checkBreakfast.isChecked()) {
                totalCost += days * 30;
                additional += "Śniadania: (+ 150 zł) \n";
            }

            StringBuilder summary = new StringBuilder();
            String summary1 = "Liczba dni: " + days + "\n" +
                    "Koszt bazowy: "+ baseCost + " zł \n" +
                    "Transport:  "+ selectedTransport + " (+ " + transportCost + " zł )\n" ;
            String totalText = "------------------------------------------------- \n Łącznie: " + totalCost + " zł";
            summary.append(summary1);
            summary.append(additional);
            summary.append(totalText);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Podsumowanie wycieczki");
            builder.setMessage(summary);
            builder.setPositiveButton("OK", null);
            builder.show();
        });
    }
}
