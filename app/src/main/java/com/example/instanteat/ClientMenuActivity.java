package com.example.instanteat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Switch;

import java.util.ArrayList;

import backend.Buscador;
import backend.BuscadorConcreto;
import backend.ComandoDeshacer;
import backend.Deshacer;
import backend.Plato;

public class ClientMenuActivity extends AppCompatActivity {
    Button searchButton, newOrderButton;
    ImageButton userPrefsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_menu);

        searchButton = findViewById(R.id.searchButton);
        newOrderButton = findViewById(R.id.newOrderButton);
        userPrefsButton = findViewById(R.id.clientPrefsButton);


        searchButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SearchActivity.class)));

        newOrderButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ChooseRestaurantActivity.class)));

        userPrefsButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserPrefsActivity.class)));


    }

}