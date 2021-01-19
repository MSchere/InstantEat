package com.example.instanteat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import backend.Buscador;
import backend.BuscadorConcreto;

public class ClientMenuActivity extends AppCompatActivity {
    Button repeatOrderButton, newOrderButton;
    SearchView search;
    ImageButton userPrefsButton;
    Buscador iBuscador;
    BuscadorConcreto buscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_menu);

        repeatOrderButton = findViewById(R.id.repeatOrderButton);
        newOrderButton = findViewById(R.id.newOrderButton);
        userPrefsButton = findViewById(R.id.clientPrefsButton);
        search = findViewById(R.id.search);

        repeatOrderButton.setOnClickListener(v ->
        {

        });

        newOrderButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ChooseRestaurantActivity.class)));

        userPrefsButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserPrefsActivity.class)));
    }
}