package com.example.listycity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button btnAddCity, btnDeleteCity, btnConfirm;
    LinearLayout addPanel;
    EditText editCity;

    int selectedIndex = -1; // tap list item to set this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        btnAddCity = findViewById(R.id.btn_add_city);
        btnDeleteCity = findViewById(R.id.btn_delete_city);
        btnConfirm = findViewById(R.id.btn_confirm);
        addPanel = findViewById(R.id.add_panel);
        editCity = findViewById(R.id.edit_city);

        String[] cities = {"Edmonton", "Montréal"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(
                this,
                R.layout.content,
                dataList
        );
        cityList.setAdapter(cityAdapter);

        // tap to select
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            Toast.makeText(this, "Selected: " + dataList.get(position), Toast.LENGTH_SHORT).show();
        });

        // ADD CITY -> show bottom panel
        btnAddCity.setOnClickListener(v -> {
            addPanel.setVisibility(View.VISIBLE);
            editCity.requestFocus();
        });

        // CONFIRM -> add item then hide panel
        btnConfirm.setOnClickListener(v -> {
            String city = editCity.getText().toString().trim();
            if (TextUtils.isEmpty(city)) {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.add(city);
            cityAdapter.notifyDataSetChanged();

            editCity.setText("");
            addPanel.setVisibility(View.GONE);
            selectedIndex = -1;
        });

        // DELETE CITY -> delete selected item
        btnDeleteCity.setOnClickListener(v -> {
            if (dataList.isEmpty()) {
                Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedIndex < 0 || selectedIndex >= dataList.size()) {
                Toast.makeText(this, "Tap a city first to select it", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedIndex);
            cityAdapter.notifyDataSetChanged();
            selectedIndex = -1;
        });
    }
}

