package com.example.instanteat;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;

import backend.Pedido;
import backend.User;

public class OwnerMenuActivity extends AppCompatActivity {
    Button dashBoardButton;
    AdapterOrder adapterOrder;
    ImageButton ownerPrefsButton;
    User restaurant;
    ImageView logo;
    ListView clientOrderList;
    ArrayList<Pedido> orderList;
    ArrayList<String> IDs, clientAddresses, totalPrices, dishes, dishNames, prices, states;
    SharedPreferences prefs;
    String email, strDishes, restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_menu);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        dashBoardButton = findViewById(R.id.dashboardButton);
        ownerPrefsButton = findViewById(R.id.ownerPrefsButton);
        logo = findViewById(R.id.ownerMenuLogo);
        logo.setImageAlpha(127);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = prefs.getString("email", "NULL");
        restaurantName = prefs.getString("name", "NULL");

        setTitle("Menú del local: " + restaurantName);

        restaurant = Utilities.getUser(getApplicationContext(), email, false);

        orderList = Utilities.getOrders(this, new String[] {restaurantName, restaurant.getAddress(), "Preparando"}, true);
        fillLists(orderList);
        clientOrderList = findViewById(R.id.ownerOrderList);
        adapterOrder = new AdapterOrder(this, IDs, clientAddresses, dishes, totalPrices, states);
        clientOrderList.setAdapter(adapterOrder);
        clientOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), OrderSummaryOwner.class);
                Bundle bundle = new Bundle();
                prices = new ArrayList<>();
                dishNames = new ArrayList<>();
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

        dashBoardButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), OwnerDashboardActivity.class)));

        ownerPrefsButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserPrefsActivity.class)));


    }

    private void fillLists(ArrayList<Pedido> orderList){
        IDs = new ArrayList<String>();
        clientAddresses = new ArrayList<String>();
        totalPrices = new ArrayList<String>();
        dishes = new ArrayList<String>();
        states = new ArrayList<String>();
        for (Pedido order:orderList){
            IDs.add(String.valueOf(order.getId()));
            clientAddresses.add(order.getDireccionCliente());
            totalPrices.add(String.valueOf(order.getPrecioTotal()));
            strDishes = Utilities.arrayListToString(order.getPlatos());
            dishes.add(Utilities.arrayListToString(order.getPlatos()));
            states.add(order.getEstado());
        }
    }

}