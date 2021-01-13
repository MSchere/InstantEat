package com.example.instanteat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderEditorActivity extends AppCompatActivity {
    TextView totalPriceText, restaurantNameText;
    ListView dishList;
    ArrayAdapter<String> adapter;
    ArrayList<String> dishes;
    String restaurantName, dishName;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_editor);

        bundle = getIntent().getExtras();
        restaurantName = bundle.getString("restaurantName");

        restaurantNameText = findViewById(R.id.restaurantNameText);
        totalPriceText = findViewById(R.id.totalPriceText);

        restaurantNameText.setText(restaurantName);

        dishes = fillList();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dishes);

        dishList = findViewById(R.id.orderDishList);
        dishList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        dishList.setItemsCanFocus(false);
        dishList.setAdapter(adapter);
        dishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "Has pulsado: " + dishes.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<String> fillList() {
        //Establecemos la conexión con la db
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.dishTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] fields = {Utilities.dishName, Utilities.price};
        String[] parameters = {restaurantName};
        String price;
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = db.query(Utilities.dishTable, fields, Utilities.restaurant + "=?", parameters, null, null, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    dishName = cursor.getString(0);
                    price = cursor.getString(1);

                    list.add(dishName + "       " + price + " €");
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR EN LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
            db.close();
            e.printStackTrace();
        }
        db.close();
        return list;
    }
}