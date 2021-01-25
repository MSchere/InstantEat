package com.example.instanteat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import backend.Plato;
import backend.User;

public class OrderEditorActivity extends AppCompatActivity {
    TextView totalPriceText;
    ListView dishList;
    Button finishOrderEditorButton;
    AdapterDish adapterDish;
    User restaurant;
    ArrayList<String> selectedDishesPrices, selectedDishesNames, selectedDishesIngredients;
    ArrayList<Integer> selectedPositions;
    ArrayList<String> dishNames, dishIngredients, dishPrices;
    String restaurantName;
    Double price;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_editor);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bundle = getIntent().getExtras();
        restaurant = (User) bundle.getSerializable("restaurant");
        restaurantName = restaurant.getName();

        totalPriceText = findViewById(R.id.totalPriceText);
        finishOrderEditorButton = findViewById(R.id.finishOrderEditorButton);

        setTitle("Pedido de " + restaurantName);
        selectedDishesNames = new ArrayList<String>();
        selectedDishesIngredients = new ArrayList<String>();
        selectedDishesPrices = new ArrayList<String>();
        selectedPositions = new ArrayList<Integer>();

        fillLists(Utilities.getDishList(this, restaurantName));

        adapterDish = new AdapterDish(this, dishNames, dishIngredients, dishPrices);
        dishList = findViewById(R.id.orderDishList);
        dishList.setItemsCanFocus(false);
        dishList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        dishList.setAdapter(adapterDish);

        //Contiene una lista auxiliar para los elementos seleccionados
        dishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String selectedDish = dishNames.get(i);
                    String selectedIngredients = dishIngredients.get(i);
                    String selectedPrice = dishPrices.get(i);
                    Integer pos = i;
                    if (selectedDishesNames.contains(selectedDish)) { //deselecciona el objeto
                        view.setBackgroundColor(getResources().getColor(R.color.white));
                        selectedDishesNames.remove(selectedDish);
                        selectedDishesIngredients.remove(selectedIngredients);
                        selectedDishesPrices.remove(selectedPrice);
                        selectedPositions.remove(pos);
                    } else {
                        view.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        selectedDishesNames.add(selectedDish);
                        selectedDishesIngredients.add(selectedIngredients);
                        selectedDishesPrices.add(selectedPrice);
                        selectedPositions.add(pos);
                    }
                    //Utilities.showToast(getApplicationContext(), selectedPositions.get(i) + "");
                    price = calculatePrice();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Utilities.showToast(getApplicationContext(), "Error en posición: " + i);
                }
            }
        });
        dishList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for(int pos:selectedPositions){
                    getViewByPosition(pos, dishList).setBackgroundColor(getResources().getColor(R.color.purple_200));
                }
            }
        });

        finishOrderEditorButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OrderSummary.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isUpdate", false);
            bundle.putStringArrayList("dishesList", selectedDishesNames); //Parámetros para la actividad
            bundle.putStringArrayList("pricesList", selectedDishesPrices);
            bundle.putDouble("totalPrice", price);
            bundle.putSerializable("restaurant", restaurant);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
    //Encontrado en stack overflow, créditos al usuario VVB
    //Devuelve la view deseada dada la listView y una posición
    public View getViewByPosition(int pos, ListView listView) {
        int firstPos = listView.getFirstVisiblePosition();
        int lastPos = firstPos + listView.getChildCount() - 1;
        if (pos < firstPos || pos > lastPos ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            int childIndex = pos - firstPos;
            return listView.getChildAt(childIndex);
        }
    }

    //Calcula el precio extrayendo los numeros de la string.
    private double calculatePrice() {
        double price = 0;
        String doubleValue;
        for(String item:selectedDishesPrices){
            doubleValue = item.replaceAll("[^\\d.]", "");
            price = price + Double.parseDouble(doubleValue);
        }
        totalPriceText.setText(price + " €");
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