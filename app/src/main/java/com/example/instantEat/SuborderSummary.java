package com.example.instantEat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import backend.Encargo;
import backend.Pedido;
import backend.Usuario;

public class SuborderSummary extends AppCompatActivity {
    TextView orderIdText, totalPriceText, restaurantAddressText, restaurantAddressTitleText;
    ListView orderDishList;
    Button finishSuborderButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> dishes, prices, fusedList;
    Pedido order;
    SharedPreferences prefs;
    Usuario client, restaurant;
    Boolean isUpdate;
    Encargo encargo = new Encargo();
    String email, state;
    int orderId;
    Double totalPrice;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suborder_summary);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        orderIdText = findViewById(R.id.orderIdText);
        totalPriceText = findViewById(R.id.totalPriceText);
        restaurantAddressText = findViewById(R.id.restaurantAddressText);
        restaurantAddressTitleText = findViewById(R.id.restaurantAddressTitleText);

        finishSuborderButton = findViewById(R.id.finishOrderButton);

        bundle = getIntent().getExtras();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        dishes = new ArrayList<String>();
        prices = new ArrayList<String>();

        isUpdate = bundle.getBoolean("isUpdate");
        dishes = bundle.getStringArrayList("dishesList");
        prices = bundle.getStringArrayList("pricesList");
        email = prefs.getString("email", "NULL");

        totalPrice = bundle.getDouble("totalPrice");
        restaurant = (Usuario) bundle.getSerializable("restaurant");

        if (isUpdate) {
            order = (Pedido) bundle.getSerializable("order");
            ViewRemoverDecorator decorator = new ViewRemoverDecorator(getApplicationContext(), new View[] {finishSuborderButton});
            decorator.decorate();
        }

        fillFields();

        fusedList = mergeLists(dishes, prices);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fusedList);
        orderDishList = findViewById(R.id.orderDishList);
        orderDishList.setAdapter(adapter);

        finishSuborderButton.setOnClickListener(v -> {
            encargo.crearPedido(orderId, client.getEmail(), 0, "",
                    restaurant.getName(), restaurant.getAddress(), fusedList, totalPrice, "", state);
            Utilities.insertOrder(getApplicationContext(), encargo.getPedido());

            startActivity(new Intent(getApplicationContext(), ClientMenuActivity.class).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK));
        });
    }

    private ArrayList<String> mergeLists(ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> newList = new ArrayList<String>();
        for (int i = 0; i < list1.size(); i++){
            newList.add(list1.get(i) + ": " + list2.get(i) + " €");
        }
        return newList;
    }

    private void fillFields() {
        client = Utilities.getUser(getApplicationContext(), email, false, false);
        if (isUpdate){
            orderId = order.getId();
            restaurant = Utilities.getUser(getApplicationContext(), order.getDireccionRestaurante(), false, true);
            totalPrice = order.getPrecioTotal();
        }
        orderId = new Random().nextInt(10000);
        orderIdText.setText(String.format("%04d", orderId));
        restaurantAddressTitleText.setText("Dirección de " + restaurant.getName() + ":");
        restaurantAddressText.setText(restaurant.getAddress());
        DecimalFormat df = new DecimalFormat("#.00");
        totalPriceText.setText(df.format(totalPrice) + " €");
        state = "Subpedido";
    }

}