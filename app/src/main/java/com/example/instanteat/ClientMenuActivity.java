package com.example.instanteat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import backend.Pedido;

public class ClientMenuActivity extends AppCompatActivity {
    Button newOrderButton, newSuborderButton;
    AdapterOrder adapterOrder;
    ImageButton userPrefsButton;
    ImageView logo, searchButton;
    ListView clientOrderList;
    ArrayList<Pedido> orderList;
    ArrayList<String> IDs, restaurantNames, totalPrices, dishes, dishNames, prices, states;
    SharedPreferences prefs;
    String email, strDishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_menu);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        searchButton = findViewById(R.id.searchButton);
        newOrderButton = findViewById(R.id.newOrderButton);
        newSuborderButton = findViewById(R.id.newSuborderButton);
        userPrefsButton = findViewById(R.id.clientPrefsButton);
        logo = findViewById(R.id.menuLogo);
        logo.setImageAlpha(127);


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = prefs.getString("email", "NULL");
        orderList = Utilities.getOrders(this, new String[] {email}, false, false);
        fillLists(orderList);
        clientOrderList = findViewById(R.id.clientOrderList);
        adapterOrder = new AdapterOrder(this, IDs, restaurantNames, dishes, totalPrices, states);
        clientOrderList.setAdapter(adapterOrder);
        clientOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                Bundle bundle = new Bundle();
                prices = new ArrayList<>();
                dishNames = new ArrayList<>();
                if (states.get(i).equals("Subpedido")){
                    intent = new Intent(getApplicationContext(), SuborderSummary.class);
                }
                else{
                    intent = new Intent(getApplicationContext(), OrderSummary.class);
                }
                for (String strDish:Utilities.stringToArrayList(dishes.get(i))){
                    dishNames.add(strDish.substring(0, strDish.indexOf(":")));
                    prices.add(strDish.replaceAll("[^\\d.]", ""));
                    //Utilities.showToast(getApplicationContext(), dishNames.get(dishNames.size()-1) + prices.get(prices.size()-1));
                }
                bundle.putStringArrayList("dishesList", dishNames);
                bundle.putStringArrayList("pricesList", prices);
                bundle.putBoolean("isUpdate", true);
                bundle.putSerializable("order", orderList.get(i)); //Parámetro para la actividad
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        searchButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SearchActivity.class)));

        newOrderButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChooseRestaurantActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isSuborder", false);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        newSuborderButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChooseRestaurantActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isSuborder", true);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        userPrefsButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserPrefsActivity.class)));
    }

    private void fillLists(ArrayList<Pedido> orderList){
        IDs = new ArrayList<String>();
        restaurantNames = new ArrayList<String>();
        totalPrices = new ArrayList<String>();
        dishes = new ArrayList<String>();
        states = new ArrayList<String>();
        for (Pedido order:orderList){
            IDs.add(String.valueOf(order.getId()));
            restaurantNames.add(order.getRestaurante());
            totalPrices.add(String.valueOf(order.getPrecioTotal()));
            strDishes = Utilities.arrayListToString(order.getPlatos());
            dishes.add(Utilities.arrayListToString(order.getPlatos()));
            states.add(order.getEstado());
        }
    }

}