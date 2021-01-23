package com.example.instanteat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import backend.AbstractFactoryPlato;
import backend.Plato;

public class OrderEditorActivity extends AppCompatActivity {
    TextView totalPriceText;
    ListView dishList;
    Button finishOrderEditorButton;
    ArrayAdapter<String> adapter;
    MyAdapter myAdapter;
    ArrayList<String> selectedDishesPrices, selectedDishesNames, selectedDishesIngredients;
    ArrayList<String> dishNames, dishIngredients, dishPrices;
    String restaurantName, dishName;
    Double price;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_editor);

        bundle = getIntent().getExtras();
        restaurantName = bundle.getString("restaurantName");

        totalPriceText = findViewById(R.id.totalPriceText);
        finishOrderEditorButton = findViewById(R.id.finishOrderEditorButton);

        setTitle("Pedido de " + restaurantName);
        selectedDishesNames = new ArrayList<String>();
        selectedDishesIngredients = new ArrayList<String>();
        selectedDishesPrices = new ArrayList<String>();

        fillLists(Utilities.getDishList(this, restaurantName));

        adapter = new MyAdapter(this, dishNames, dishIngredients, dishPrices);
        dishList = findViewById(R.id.orderDishList);
        dishList.setItemsCanFocus(false);
        dishList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        dishList.setAdapter(adapter);

        //Contiene una lista auxiliar para los elementos seleccionados
        dishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedDish=dishNames.get(i);
                String selectedIngredients=dishIngredients.get(i);
                String selectedPrice=dishPrices.get(i);
                if(selectedDishesNames.contains(selectedDish)){ //deselecciona el objeto
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                    selectedDishesNames.remove(selectedDish);
                    selectedDishesIngredients.remove(selectedIngredients);
                    selectedDishesPrices.remove(selectedPrice);
                }
                else {
                    view.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    selectedDishesNames.add(selectedDish);
                    selectedDishesIngredients.add(selectedIngredients);
                    selectedDishesPrices.add(selectedPrice);
                }
                price = calculatePrice();
            }
        });

        finishOrderEditorButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OrderSummary.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("namesList", selectedDishesNames); //Parámetros para la actividad
            //bundle.putStringArrayList("ingredientsList", selectedDishesIngredients);
            bundle.putStringArrayList("pricesList", selectedDishesPrices);
            bundle.putDouble("totalPrice", price);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
    //Calcula el precio extrayendo los numeros de la string.
    private double calculatePrice() {
        double price = 0;
        String doubleValue;
        for(String item:selectedDishesPrices){
            doubleValue = item.replaceAll("[^\\d.]", "");
            //Utilities.showToast(getApplicationContext(), doubleValue);
            price = price + Double.parseDouble(doubleValue);
        }
        totalPriceText.setText(String.valueOf(price) + " €");
        return price;
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