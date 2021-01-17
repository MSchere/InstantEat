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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderEditorActivity extends AppCompatActivity {
    TextView totalPriceText;
    ListView dishList;
    Button finishOrderEditorButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> dishDescriptions, selectedDishesprices, selectedDishes, selectedItems;
    String restaurantName, dishName;
    Double price;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_editor);

        bundle = getIntent().getExtras();
        restaurantName = bundle.getString("restaurantName");

        totalPriceText = findViewById(R.id.totalPriceText);
        finishOrderEditorButton = findViewById(R.id.finishOrderEditorButton);

        setTitle("Pedido de " + restaurantName);
        selectedItems = new ArrayList<String>();
        selectedDishes = new ArrayList<String>();
        dishDescriptions = fillList();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dishDescriptions);

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
                price = calculatePrice();
                selectedDishes = getDishesList();
                selectedDishesprices = getPricesList();
            }
        });

        finishOrderEditorButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OrderSummary.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("dishList", selectedDishes); //Parámetros para la actividad
            bundle.putStringArrayList("priceList", selectedDishesprices);
            bundle.putDouble("totalPrice", price);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
    //Calcula el precio extrayendo los numeros de la string.
    private double calculatePrice() {
        double price = 0;
        String doubleValue;
        for(String item:selectedItems){
            doubleValue = item.replaceAll("[^\\d.]", "");
            //Utilities.showToast(getApplicationContext(), doubleValue);
            price = price + Double.parseDouble(doubleValue);
        }
        totalPriceText.setText(String.valueOf(price) + " €");
        return price;
    }

    private ArrayList<String> getPricesList(){
        ArrayList<String> list = new ArrayList<String>();
        String priceStr = "";
        for(String item:selectedItems){
            priceStr = item.replaceAll("[^\\d.]", "");
            //Utilities.showToast(getApplicationContext(), dishName);
            list.add(priceStr);
        }
        return list;
    }

    private ArrayList<String> getDishesList(){
        ArrayList<String> list = new ArrayList<String>();
        String dishName = "";
        for(String item:selectedItems){
            dishName = item.substring(0, item.indexOf(": "));
            dishName.trim();
            //Utilities.showToast(getApplicationContext(), dishName);
            list.add(dishName);
        }
        return list;
    }

    private ArrayList<String> fillList() {
        //Establecemos la conexión con la db
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.dishTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] fields = {Utilities.dishName, Utilities.price, Utilities.ingredients};
        String[] parameters = {restaurantName};
        String price, ingredients;
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor cursor = db.query(Utilities.dishTable, fields, Utilities.restaurant + "=?", parameters, null, null, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    dishName = cursor.getString(0);
                    price = cursor.getString(1);
                    ingredients = cursor.getString(2);
                    list.add(dishName + ": " + price + " €\n" + ingredients);
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