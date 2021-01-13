package com.example.instanteat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class ClientMenuActivity extends AppCompatActivity {
    Button repeatOrderButton, newOrderButton;
    ImageButton userPrefsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_menu);

        repeatOrderButton = findViewById(R.id.repeatOrderButton);
        newOrderButton = findViewById(R.id.newOrderButton);
        userPrefsButton = findViewById(R.id.clientPrefsButton);

        repeatOrderButton.setOnClickListener(v ->
        {

        });

        newOrderButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ChooseRestaurantActivity.class)));

        userPrefsButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserPrefsActivity.class)));
    }
}