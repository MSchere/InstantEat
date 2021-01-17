package com.example.instanteat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class OrderSummary extends AppCompatActivity {
    TextView orderIdText, totalPriceText, addressText, phoneNumberText, paymentTitleText;
    ListView orderDishList;
    Spinner paymentMethodSpinner;
    Button finishOrderButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> dishes, prices;
    String[] paymentMethods = {"Efectivo", "Tarjeta", "PayPal", "Bitcoin"};
    SharedPreferences prefs;
    String email, address, phoneNumber, cardNumber, paypalEmail;
    Random rand;
    int orderId;
    Double totalPrice;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);

        orderIdText = findViewById(R.id.orderIdText);
        totalPriceText = findViewById(R.id.totalPriceText);
        addressText = findViewById(R.id.addressText);
        phoneNumberText = findViewById(R.id.phoneNumberText);
        paymentTitleText= findViewById(R.id.paymentTitleText);

        finishOrderButton = findViewById(R.id.finishOrderButton);

        bundle = getIntent().getExtras();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        dishes = new ArrayList<String>();
        prices = new ArrayList<String>();

        dishes = bundle.getStringArrayList("dishList");
        prices = bundle.getStringArrayList("priceList");
        totalPrice = bundle.getDouble("totalPrice");

        email = prefs.getString("email", "NULL");

        fillFields();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mergeLists(dishes, prices));
        orderDishList = findViewById(R.id.orderDishList);
        orderDishList.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paymentMethods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner = findViewById(R.id.paymentMethodSpinner);
        paymentMethodSpinner.setAdapter(adapter);

        paymentMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                String selectedOption=((TextView)view).getText().toString();
                switch (selectedOption) {
                    case "Efectivo":
                        paymentTitleText.setText("Se pagará al repartidor en efectivo");
                        break;
                    case "Tarjeta":
                        getCard();
                        paymentTitleText.setText("Se pagará con la tarjeta:\n\n" + cardNumber);
                        break;
                    case "Paypal":
                        paymentTitleText.setText("Se pagará con la cuenta de PayPal asociada"); //No Funciona
                        break;
                    case "Bitcoin":
                        paymentTitleText.setText("Pague el importe a esta dirección:\n\n0x3a94cd13afd58ede00488735ca771fb3784272c1");
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
            Utilities.showToast(getApplicationContext(), "Pedido finalizado");
            startActivity(new Intent(getApplicationContext(), ClientMenuActivity.class).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK));
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
        //Establecemos la conexión con la db
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {email};
        String[] fields = {Utilities.address, Utilities.phoneNumber};
        try {
            Cursor cursor = db.query(Utilities.userTable, fields, Utilities.email + "=?", parameters, null, null, null);
            cursor.moveToFirst();
            address = cursor.getString(0);
            phoneNumber = cursor.getString(1);

            cursor.close();
            db.close();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error en la base de datos", Toast.LENGTH_SHORT).show();
            db.close();
            e.printStackTrace();
        }
        rand = new Random();
        orderId = rand.nextInt(10000);
        orderIdText.setText(String.format("%04d", orderId));
        addressText.setText(address);
        phoneNumberText.setText(phoneNumber);
        totalPriceText.setText(Double.toString(totalPrice) + " €");
    }

}