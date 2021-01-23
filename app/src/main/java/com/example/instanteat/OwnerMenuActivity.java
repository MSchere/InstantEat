package com.example.instanteat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import backend.Buscador;
import backend.BuscadorConcreto;
import backend.Plato;

public class OwnerMenuActivity extends AppCompatActivity {
    SharedPreferences prefs;
    TextView totalDishesText, totalVeganDishesText, totalGlutenFreeDishesText;
    Button addDishButton;
    ListView dishList;
    ImageButton ownerPrefsButton;
    String restaurantName;
    MyAdapter myAdapter;
    ArrayList<Plato> dishes;
    ArrayList<String> dishNames, dishIngredients, dishPrices;
    BuscadorConcreto buscador;
    Buscador iBuscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_menu);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        restaurantName = prefs.getString("name", "NULL");

        setTitle("Platos de " + restaurantName);
        dishList = findViewById(R.id.dishList);

        addDishButton = findViewById(R.id.addDishMenuButton);
        ownerPrefsButton = findViewById(R.id.ownerPrefsButton);
        totalDishesText = findViewById(R.id.totalDishesText);
        totalGlutenFreeDishesText = findViewById(R.id.totalGlutenFreeDishesText);
        totalVeganDishesText = findViewById(R.id.totalVeganDishesText);

        dishes = Utilities.getDishList(this, restaurantName);

        buscador = new BuscadorConcreto(dishes);
        iBuscador = buscador;
        fillLists(dishes);
        fillDashboard();
        myAdapter = new MyAdapter(this, dishNames, dishIngredients, dishPrices);
        dishList.setAdapter(myAdapter);
        dishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "Has pulsado: " + dishes.get(i), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), EditDishActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("dishName", dishNames.get(i)); //Parámetro para la actividad
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        addDishButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EditDishActivity.class)));

        ownerPrefsButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserPrefsActivity.class)));
    }

    private void fillLists(ArrayList<Plato> dishList) {
        dishNames = new ArrayList<String>();
        dishIngredients = new ArrayList<String>();
        dishPrices = new ArrayList<String>();
        for (Plato plato : dishList) {
            dishNames.add(plato.getNombre());
            dishIngredients.add(Utilities.arrayListToString(plato.getIngredientes()));
            dishPrices.add(String.valueOf(plato.getPrecio()));

        }
    }
        private void fillDashboard(){
            totalDishesText.setText(String.valueOf(dishes.size()));
            totalGlutenFreeDishesText.setText(String.valueOf(buscador.mostrarNoGluten().size()));
            buscador.resetLista();
            totalVeganDishesText.setText(String.valueOf(buscador.mostrarVeganos().size()));
        }

}