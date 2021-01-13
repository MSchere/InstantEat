package com.example.instanteat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderEditorActivity extends AppCompatActivity {
    TextView totalPriceText, restaurantNameText;
    ListView dishList;
    ArrayAdapter<String> adapter;
    ArrayList<String> dishes, selectedItems;
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
        selectedItems = new ArrayList<String>();
        dishes = fillList();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dishes);

        dishList = findViewById(R.id.orderDishList);
        dishList.setItemsCanFocus(false);
        dishList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        dishList.setAdapter(adapter);
        //Contiene una lista auxiliar para los elementos seleccionados
        dishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedDish=((TextView)view).getText().toString();
                if(selectedItems.contains(selectedDish))selectedItems.remove(selectedDish); //deselecciona el objeto
                else selectedItems.add(selectedDish);
                calculatePrice();
            }
        });
    }
    //Calcula el precio extrayendo los numeros de la string.
    public void calculatePrice() {
        int price = 0;
        String intValue;
        for(String item:selectedItems){
            intValue = item.replaceAll("[^0-9]", "");
            //Utilities.showToast(getApplicationContext(), intValue);
            price = price + Integer.parseInt(intValue);
        }
        totalPriceText.setText(String.valueOf(price));
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
                    list.add(dishName + ": " + price + " €");
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