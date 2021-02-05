package com.example.instantEat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
    ArrayList<String> list;
    String restaurantName, restaurantAddress;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_restaurant);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        bundle = getIntent().getExtras();
        list = fillList();
        restaurantList = findViewById(R.id.restaurantList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        restaurantList.setAdapter(adapter);
        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String address = list.get(i).substring(list.get(i).indexOf("en ") + 3);
                address.trim();
                Intent intent = new Intent(getApplicationContext(), OrderEditorActivity.class);
                //Nos quedamos solo con el nombre del restaurante
                bundle.putSerializable("restaurant", Utilities.getUser(getApplicationContext(), address, false, true)); //Parámetro para la actividad
                bundle.putSerializable("isSuborder", bundle.getBoolean("isSuborder"));
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
        String[] fields = {Utilities.email, Utilities.name, Utilities.address};
        String[] parameters = {"owner"};
        try {
            Cursor cursor = db.query(Utilities.userTable, fields, Utilities.userType+"=?", parameters, null, null, null);
            if(cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    restaurantName = cursor.getString(1);
                    restaurantAddress = cursor.getString(2);
                    list.add(restaurantName + " en " + restaurantAddress);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Utilities.showToast(getApplicationContext(), "ERROR EN LA BASE DE DATOS");
            db.close();
            e.printStackTrace();
        }
        db.close();
        return list;
    }
}