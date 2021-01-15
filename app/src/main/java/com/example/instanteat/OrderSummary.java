package com.example.instanteat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderSummary extends AppCompatActivity {
    TextView orderIdText, totalPriceText, addressText, phoneNumberText;
    ListView orderDishList;
    Spinner paymentMethodSpinner;
    Button finishOrderButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> dishes, prices;
    SharedPreferences prefs;
    String email, address, phoneNumber;
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
        paymentMethodSpinner = findViewById(R.id.paymentMethodSpinner);
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

        finishOrderButton.setOnClickListener(v -> {
            Utilities.showToast(getApplicationContext(), "Pedido finalizado");
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

    public void fillFields() {
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
        addressText.setText(address);
        phoneNumberText.setText(phoneNumber);
        totalPriceText.setText(Double.toString(totalPrice) + " €");
    }
}