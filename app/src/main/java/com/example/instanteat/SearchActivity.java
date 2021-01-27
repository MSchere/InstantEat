package com.example.instanteat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import backend.Buscador;
import backend.BuscadorConcreto;
import backend.Plato;
import backend.User;

public class SearchActivity extends AppCompatActivity {
    SearchView searcher;
    Switch veganSwitch, glutenFreeSwitch, restaurantSearchSwitch;
    Spinner orderPriceSpinner;
    String[] order = {"Precio ascendente", "Precio descendente"};
    String previousQuery = "";
    ListView searchResults;
    ArrayAdapter<String> adapter;
    ArrayList<Plato> dishList;
    AdapterDish adapterDish;
    ArrayList<String> dishNames, dishIngredients, dishPrices;
    BuscadorConcreto buscador;
    Buscador iBuscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        veganSwitch = findViewById(R.id.veganSwitch);
        glutenFreeSwitch = findViewById(R.id.glutenFreeSwitch);
        restaurantSearchSwitch = findViewById(R.id.restaurantSearchSwitch);
        searcher = findViewById(R.id.searcher);
        orderPriceSpinner = findViewById(R.id.orderPriceSpinner);

        dishList = Utilities.getDishList(this);
        buscador = new BuscadorConcreto(dishList);
        iBuscador = buscador;
        fillLists(dishList);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, order);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderPriceSpinner = findViewById(R.id.orderPriceSpinner);
        orderPriceSpinner.setAdapter(adapter);

        searchResults = findViewById(R.id.searchResults);
        adapterDish = new AdapterDish(this, dishNames, dishIngredients, dishPrices);
        searchResults.setAdapter(adapterDish);
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = buscador.getLista().get(i).getRestaurante();
                Intent intent = new Intent(getApplicationContext(), OrderEditorActivity.class);
                Bundle bundle = new Bundle();
                User restaurant = (User) Utilities.getUser(getApplicationContext(), selection, true);
                bundle.putSerializable("restaurant", restaurant); //Parámetro para la actividad
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        searcher.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscador.resetLista();
                fillLists(buscador.buscarNombrePlato(query));
                updateResult();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() < previousQuery.length()) {
                    buscador.deshacerNombre();
                }
                else buscador.resetLista();

                fillLists(buscador.buscarNombrePlato(newText));
                updateResult();
                previousQuery = newText;
                return false;
            }
        });

        orderPriceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                String selectedOption = ((TextView) view).getText().toString();
                switch (selectedOption) {
                    case "Precio ascendente":
                        buscador.ordenarPrecioMenMay();
                        break;
                    case "Precio descendente":
                        buscador.ordenarPrecioMayMen();
                        break;
                }
                fillLists(buscador.getLista());
                updateResult();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.veganSwitch:
                if (veganSwitch.isChecked()) {
                    fillLists(buscador.mostrarVeganos());
                }
                else {
                    buscador.deshacerVegano();
                    fillLists(buscador.getLista());
                }
                break;
            case R.id.glutenFreeSwitch:
                if (glutenFreeSwitch.isChecked()) {
                    fillLists(buscador.mostrarGlutenFree());
                }
                else {
                    buscador.deshacerGlutenFree();
                    fillLists(buscador.getLista());
                }
                break;
            case R.id.restaurantSearchSwitch:
                if (restaurantSearchSwitch.isChecked()){
                    buscador = new BuscadorConcreto(Utilities.getDishList(getApplicationContext()));
                    iBuscador = buscador;
                }
                else buscador.deshacerRestaurante();
                break;
        }
        updateResult();
    }

    private void updateResult(){
        adapterDish = new AdapterDish(this, dishNames, dishIngredients, dishPrices);
        searchResults.setAdapter(adapterDish);
    }

    private void fillLists(ArrayList<Plato> dishList){
        dishNames = new ArrayList<String>();
        dishIngredients = new ArrayList<String>();
        dishPrices = new ArrayList<String>();
            for (Plato plato:dishList){
                dishNames.add(plato.getNombre());
                dishIngredients.add(Utilities.arrayListToString(plato.getIngredientes()));
                dishPrices.add(String.valueOf(plato.getPrecio()));

            }
    }
}