package com.example.instanteat;

import android.content.ContentValues;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Random;

import backend.Pedido;
import backend.User;

public class OrderSummaryOwner extends AppCompatActivity {
    TextView orderIdText, totalPriceText, clientAddressText, phoneNumberText, paymentMethodText;
    ListView orderDishList;
    Button orderDoneButton, orderCanceledButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> dishes, prices, fusedList;
    SharedPreferences prefs;
    User client, restaurant;
    Pedido order;
    String email, cardNumber, state;
    int orderId;
    Double totalPrice;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary_owner);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        orderIdText = findViewById(R.id.orderIdText);
        totalPriceText = findViewById(R.id.totalPriceText);
        clientAddressText = findViewById(R.id.clientAddressText);

        phoneNumberText = findViewById(R.id.phoneNumberText);
        paymentMethodText = findViewById(R.id.paymentMethodText);

        orderDoneButton = findViewById(R.id.orderDoneButton);
        orderCanceledButton = findViewById(R.id.orderCanceledButton);

        bundle = getIntent().getExtras();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        dishes = new ArrayList<String>();
        prices = new ArrayList<String>();

        dishes = bundle.getStringArrayList("dishesList");
        prices = bundle.getStringArrayList("pricesList");
        email = prefs.getString("email", "NULL");
        order = (Pedido) bundle.getSerializable("order");

        fillFields();

        fusedList = mergeLists(dishes, prices);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fusedList);
        orderDishList = findViewById(R.id.orderDishList);
        orderDishList.setAdapter(adapter);

        orderDoneButton.setOnClickListener(v -> {
            Utilities.updateOrderState(getApplicationContext(), orderId, "Completado");
            startActivity(new Intent(getApplicationContext(), OwnerMenuActivity.class).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK));
        });

        orderCanceledButton.setOnClickListener(v -> {
            Utilities.updateOrderState(getApplicationContext(), orderId, "Cancelado");
            startActivity(new Intent(getApplicationContext(), OwnerMenuActivity.class).addFlags(
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
        client = Utilities.getUser(getApplicationContext(), email, false);
        orderId = order.getId();
        restaurant = Utilities.getUser(getApplicationContext(), order.getRestaurante(), true);
        totalPrice = order.getPrecioTotal();
        orderIdText.setText(String.format("%06d", orderId));
        clientAddressText.setText(client.getAddress());
        phoneNumberText.setText(String.valueOf(client.getPhoneNumber()));
        totalPriceText.setText(totalPrice + " €");
        paymentMethodText.setText("Pagado con " + order.getMetodoPago());
    }
}