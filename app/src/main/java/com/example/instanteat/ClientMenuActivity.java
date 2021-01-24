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
import android.widget.SearchView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import backend.Buscador;
import backend.BuscadorConcreto;
import backend.ComandoDeshacer;
import backend.Deshacer;
import backend.Pedido;
import backend.Plato;
import backend.User;

public class ClientMenuActivity extends AppCompatActivity {
    Button searchButton, newOrderButton;
    AdapterOrder adapterOrder;
    ImageButton userPrefsButton;
    ImageView logo;
    ListView clientOrderList;
    ArrayList<Pedido> orderList;
    ArrayList<String> IDs, restaurantNames, totalPrices, dishes;
    SharedPreferences prefs;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_menu);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        searchButton = findViewById(R.id.searchButton);
        newOrderButton = findViewById(R.id.newOrderButton);
        userPrefsButton = findViewById(R.id.clientPrefsButton);
        logo = findViewById(R.id.menuLogo);
        logo.setImageAlpha(127);


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = prefs.getString("email", "NULL");
        orderList = Utilities.getOrders(this, email);
        fillLists(orderList);
        clientOrderList = findViewById(R.id.clientOrderList);
        adapterOrder = new AdapterOrder(this, IDs, restaurantNames, dishes, totalPrices);
        clientOrderList.setAdapter(adapterOrder);
        clientOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), OrderEditorActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putSerializable("restaurant", restaurant); //Parámetro para la actividad
                //intent.putExtras(bundle);
                //startActivity(intent);
            }
        });


        searchButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SearchActivity.class)));

        newOrderButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ChooseRestaurantActivity.class)));

        userPrefsButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserPrefsActivity.class)));


    }

    private void fillLists(ArrayList<Pedido> orderList){
        IDs = new ArrayList<String>();
        restaurantNames = new ArrayList<String>();
        totalPrices = new ArrayList<String>();
        dishes = new ArrayList<String>();
        for (Pedido order:orderList){
            IDs.add(String.valueOf(order.getId()));
            restaurantNames.add(order.getRestaurante());
            totalPrices.add(String.valueOf(order.getPrecioTotal()));
            dishes.add(Utilities.arrayListToString(order.getPlatos()));
        }
    }

}