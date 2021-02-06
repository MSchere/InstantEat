package com.example.instantEat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.DecimalFormat;
import java.util.ArrayList;

import backend.ObservadorConcretoEstado;
import backend.Pedido;
import backend.Sujeto;
import backend.SujetoConcreto;
import backend.Usuario;

public class OrderSummaryOwner extends AppCompatActivity {
    TextView orderIdText, totalPriceText, clientAddressText, phoneNumberText, paymentMethodText, subordersText, subordersTitleText;
    ListView orderDishList;
    Button orderDoneButton, orderCanceledButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> dishes, prices, fusedList;
    SharedPreferences prefs;
    Usuario client, restaurant;
    DecimalFormat df = new DecimalFormat("#.00");
    Pedido order;
    Sujeto subject;
    ObservadorConcretoEstado o1, o2;
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
        subordersText = findViewById(R.id.subordersText);
        subordersTitleText= findViewById(R.id.subordersTitleText);

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
            state = "Completado";
            observeState(state);
            Utilities.updateOrderState(getApplicationContext(), orderId, state);
            startActivity(new Intent(getApplicationContext(), OwnerMenuActivity.class).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK));
        });

        orderCanceledButton.setOnClickListener(v -> {
            state = "Cancelado";
            observeState(state);
            Utilities.updateOrderState(getApplicationContext(), orderId, state);
            startActivity(new Intent(getApplicationContext(), OwnerMenuActivity.class).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK));
        });
    }

    private ArrayList<String> mergeLists(ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> newList = new ArrayList<String>();
        for (int i = 0; i < list1.size(); i++){
            newList.add(list1.get(i) + ": " + df.format(Double.parseDouble(list2.get(i).replaceAll(",","."))) + " €");
        }
        return newList;
    }

    private void fillFields() {
        client = Utilities.getUser(getApplicationContext(), order.getEmail(), false, false);
        orderId = order.getId();
        subordersText.setText(Utilities.getSuborders(getApplicationContext(), orderId+""));
        if (subordersText.getText().equals("")){
            subordersText.setText("sin subpedidos");
        }
        restaurant = Utilities.getUser(getApplicationContext(), order.getDireccionRestaurante(), false, true);
        totalPrice = order.getPrecioTotal();
        orderIdText.setText(String.format("%04d", orderId));
        clientAddressText.setText(client.getAddress());
        phoneNumberText.setText(String.valueOf(client.getPhoneNumber()));
        totalPriceText.setText(totalPrice + " €");
        paymentMethodText.setText("Pagado con " + order.getMetodoPago());
    }

    private void observeState(String state){
        subject = new SujetoConcreto();
        subject.setPedido(order);
        o1 = new ObservadorConcretoEstado("Observador1", "Completado", subject);
        o2 = new ObservadorConcretoEstado("Observador2", "Cancelado", subject);
        subject.añadirObservador(o1);
        subject.añadirObservador(o2);
        order.setEstado(state);
        o1.actualizar();
        o2.actualizar();
    }
}