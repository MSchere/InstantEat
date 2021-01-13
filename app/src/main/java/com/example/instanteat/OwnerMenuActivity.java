package com.example.instanteat;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OwnerMenuActivity extends AppCompatActivity {
    SharedPreferences prefs;
    Button addDishButton;
    ListView dishList;
    ImageButton ownerPrefsButton;
    TextView myLocalText;
    String restaurantName;
    ArrayAdapter<String> adapter;
    ArrayList<String> dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_menu);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        restaurantName = prefs.getString("name", "NULL");

        myLocalText = findViewById(R.id.myLocalText);
        myLocalText.setText(restaurantName);
        dishList = findViewById(R.id.dishList);

        addDishButton = findViewById(R.id.addDishMenuButton);
        ownerPrefsButton = findViewById(R.id.ownerPrefsButton);

        dishes = fillList();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dishes);
        dishList.setAdapter(adapter);
        dishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "Has pulsado: " + dishes.get(i), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), EditDishActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("dishName", dishes.get(i)); //Parámetro para la actividad
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        addDishButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EditDishActivity.class)));

        ownerPrefsButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserPrefsActivity.class)));
    }

    private ArrayList<String> fillList() {
        //Establecemos la conexión con la db
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.dishTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ArrayList<String> list = new ArrayList<String>();
        String[] fields = {Utilities.dishName, Utilities.price};
        String[] parameters = {restaurantName};
        String dishName, price;
        try {
            Cursor cursor = db.query(Utilities.dishTable, fields, Utilities.restaurant+"=?", parameters, null, null, null);
            if(cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    dishName = cursor.getString(0);
                    price = cursor.getString(1);
                    list.add(dishName);
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