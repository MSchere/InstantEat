package com.example.instanteat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import backend.Encargo;
import backend.Observador;
import backend.ObservadorConcretoEstado;
import backend.Pedido;
import backend.Sujeto;
import backend.SujetoConcreto;
import backend.Usuario;

public class OrderSummary extends AppCompatActivity {
    TextView orderIdText, totalPriceText, restaurantAddressText, restaurantAddressTitleText, clientAddressText, phoneNumberText, paymentTitleText, subordersText, subordersTitleText;
    ListView orderDishList;
    Spinner paymentMethodSpinner;
    Button finishOrderButton, addSuborderButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> dishes, prices, fusedList;
    SharedPreferences prefs;
    Boolean isUpdate, hasSuborders = false;
    String[] paymentMethods = {"Efectivo", "Tarjeta", "PayPal", "Bitcoin"};
    Usuario client, restaurant;
    SujetoConcreto subject;
    ObservadorConcretoEstado o1, o2;
    Encargo encargo = new Encargo();
    Pedido order;
    String email, cardNumber, selectedMethod, state;
    int orderId;
    Double totalPrice;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        orderIdText = findViewById(R.id.orderIdText);
        totalPriceText = findViewById(R.id.totalPriceText);
        clientAddressText = findViewById(R.id.clientAddressText);
        restaurantAddressText = findViewById(R.id.restaurantAddressText);
        restaurantAddressTitleText = findViewById(R.id.restaurantAddressTitleText);
        phoneNumberText = findViewById(R.id.phoneNumberText);
        paymentTitleText = findViewById(R.id.paymentTitleText);
        subordersText = findViewById(R.id.subordersText);
        subordersTitleText= findViewById(R.id.subordersTitleText);

        finishOrderButton = findViewById(R.id.finishOrderButton);
        addSuborderButton = findViewById(R.id.addSuborderButton);

        bundle = getIntent().getExtras();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        dishes = new ArrayList<String>();
        prices = new ArrayList<String>();

        isUpdate = bundle.getBoolean("isUpdate");
        dishes = bundle.getStringArrayList("dishesList");
        prices = bundle.getStringArrayList("pricesList");
        email = prefs.getString("email", "NULL");
        if (isUpdate) {
            order = (Pedido) bundle.getSerializable("order");
            finishOrderButton.setText("Repetir pedido");
        }
        else {
            totalPrice = bundle.getDouble("totalPrice");
            restaurant = (Usuario) bundle.getSerializable("restaurant");
        }
        fillFields();

        if (email.equals("dummy@email.com")) {
             paymentMethods = new String[]{"Efectivo"};
        }

        fusedList = mergeLists(dishes, prices);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fusedList);
        orderDishList = findViewById(R.id.orderDishList);
        orderDishList.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paymentMethods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner = findViewById(R.id.paymentMethodSpinner);
        paymentMethodSpinner.setAdapter(adapter);

        paymentMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                selectedMethod=((TextView)view).getText().toString();
                switch (selectedMethod) {
                    case "Efectivo":
                        paymentTitleText.setText("Se pagará al repartidor en efectivo");
                        break;
                    case "Tarjeta":
                        getCard();
                        paymentTitleText.setText("Se pagará con la tarjeta:\n" + cardNumber);
                        break;
                    case "PayPal":
                        paymentTitleText.setText("Se pagará con la cuenta de PayPal asociada"); //No hace nada
                        break;
                    case "Bitcoin":
                        paymentTitleText.setText("Pague el importe a esta dirección:\n0x3a94cd13afd58ede00488735ca771fb3784272c1");
                        break;
                    default:
                        paymentTitleText.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        finishOrderButton.setOnClickListener(v -> {
            if (hasSuborders) {
                state = "Preparando con subpedidos";
            }
            if (!isUpdate) {
                encargo.crearPedido(orderId, client.getEmail(), client.getPhoneNumber(), client.getAddress(),
                        restaurant.getName(), restaurant.getAddress(), fusedList, totalPrice, selectedMethod, state);
                Utilities.insertOrder(getApplicationContext(), encargo.getPedido());
            }
            else {
                observeState(state);
                Utilities.updateOrderState(getApplicationContext(), orderId, state);
            }
            startActivity(new Intent(getApplicationContext(), ClientMenuActivity.class).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK));
        });

        addSuborderButton.setOnClickListener(v -> {
            if (!isUpdate) {
                encargo.crearPedido(orderId, client.getEmail(), client.getPhoneNumber(), client.getAddress(),
                        restaurant.getName(), restaurant.getAddress(), fusedList, totalPrice, selectedMethod, state);
                order = encargo.getPedido();
                Utilities.insertOrder(getApplicationContext(), order);
            }
            else {
                observeState(state);
                Utilities.updateOrderState(getApplicationContext(), orderId, state);
            }
            Intent intent = new Intent(getApplicationContext(), ChooseSubordersActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", order);
            bundle.putDouble("totalPrice", totalPrice);
            bundle.putInt("orderId", orderId); //Parámetros para la actividad
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void getCard(){
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.cardTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {email};
        String[] fields = {Utilities.cardNumber};
        try {
            Cursor cursor = db.query(Utilities.cardTable, fields, Utilities.email + "=?", parameters, null, null, null);
            cursor.moveToFirst();
            cardNumber = cursor.getString(0);
        }
        catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "Tarjeta no encontrada", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), EditCardActivity.class));
            db.close();
            //e.printStackTrace();
        }
        db.close();
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
        if (isUpdate){
            orderId = order.getId();
            subordersText.setText(Utilities.getSuborders(getApplicationContext(), orderId+""));
            if (subordersText.getText().equals("")){
                subordersText.setText("sin subpedidos");
                hasSuborders = false;
            }
            else hasSuborders = true;
            restaurant = Utilities.getUser(getApplicationContext(), order.getRestaurante(), true);
            totalPrice = order.getPrecioTotal();
        }
        else {
            orderId = new Random().nextInt(10000);
        }
        orderIdText.setText(String.format("%04d", orderId));
        clientAddressText.setText(client.getAddress());
        restaurantAddressTitleText.setText("Dirección de " + restaurant.getName() + ":");
        restaurantAddressText.setText(restaurant.getAddress());
        phoneNumberText.setText(String.valueOf(client.getPhoneNumber()));
        DecimalFormat df = new DecimalFormat("#.00");
        totalPriceText.setText(df.format(totalPrice) + " €");
        state = "Preparando";

    }

    private void observeState(String state){
        subject = new SujetoConcreto();
        subject.setPedido(order);
        o1 = new ObservadorConcretoEstado("Observador1", "Preparando", subject);
        o2 = new ObservadorConcretoEstado("Observador2", "Preparando con subpedidos", subject);
        subject.añadirObservador(o1);
        subject.añadirObservador(o2);
        order.setEstado(state);
        o1.actualizar();
        o2.actualizar();
    }

}