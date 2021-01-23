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
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseRestaurantActivity extends AppCompatActivity {
    ListView restaurantList;
    ArrayAdapter<String> adapter;
    ArrayList<String> restaurants;
    String selection, restaurantName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_restaurant);

        restaurantList = findViewById(R.id.searchResults);
        restaurants = fillList();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurants);
        restaurantList.setAdapter(adapter);
        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "Has pulsado: " + dishes.get(i), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), OrderEditorActivity.class);
                Bundle bundle = new Bundle();
                //Nos quedamos solo con el nombre del restaurante
                selection = restaurants.get(i).substring(0, restaurants.get(i).indexOf(" en "));
                selection.trim();
                bundle.putString("restaurantName", selection); //Parámetro para la actividad
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private ArrayList<String> fillList() {
        //Establecemos la conexión con la db
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ArrayList<String> list = new ArrayList<String>();
        String[] fields = {Utilities.name, Utilities.address};
        String[] parameters = {"owner"};
        try {
            Cursor cursor = db.query(Utilities.userTable, fields, Utilities.userType+"=?", parameters, null, null, null);
            if(cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    restaurantName = cursor.getString(0);
                    address = cursor.getString(1);
                    list.add(restaurantName + " en " + address);
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