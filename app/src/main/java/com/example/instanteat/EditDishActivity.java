package com.example.instanteat;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.StringTokenizer;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditDishActivity extends AppCompatActivity {
    SharedPreferences prefs;
    Button addDishEditorButton, addIngredientButton, removeIngredientButton, deleteDishEditorButton;
    EditText dishNameField, ingredientField, priceField;
    TextView ingredientsListText;
    CheckBox isVeganCheckBox, isGlutenFreeCheckBox;
    AbstractFactoryPlato factoryPlato = new AbstractFactoryPlato();
    IAbstractFactoryPlato iAbstractFactoryPlato;
    ArrayList<String> ingredients = new ArrayList();
    Bundle bundle;
    String restaurantName, dishName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_dish);

        bundle = getIntent().getExtras();
        dishName = "null";
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        restaurantName = prefs.getString("name", "NULL");

        iAbstractFactoryPlato = factoryPlato;

        dishNameField = findViewById(R.id.dishNameField);
        ingredientField = findViewById(R.id.ingredientField);
        ingredientsListText = findViewById(R.id.ingredientsListText);
        priceField = findViewById(R.id.priceField);

        isVeganCheckBox = findViewById(R.id.isVeganTextBox);
        isGlutenFreeCheckBox = findViewById(R.id.isGlutenFreeCheckBox);

        addIngredientButton = findViewById(R.id.addIngredientButton);
        removeIngredientButton = findViewById(R.id.removeIngredientButton);
        addDishEditorButton = findViewById(R.id.saveDishEditorButton);
        deleteDishEditorButton = findViewById(R.id.deleteDishEditorButton);

        if(bundle != null) {
            dishName = bundle.getString("dishName");
            fillFields();
        }
        else {
            ((ViewGroup) deleteDishEditorButton.getParent()).removeView(deleteDishEditorButton);
        }

        addIngredientButton.setOnClickListener(v -> {
            ingredients.add(ingredientField.getText().toString());
            ingredientField.setText("");
            ingredientsListText.setText(arrayListToString(ingredients));
        });

        removeIngredientButton.setOnClickListener(v -> {
            ingredients.remove(ingredients.size()-1);
            ingredientsListText.setText(arrayListToString(ingredients));
        });

        addDishEditorButton.setOnClickListener(v -> {
            if (checkDishName() && checkPrice()) {
                if (bundle != null) updateDish();
                else registerDish();
            }
        });

        deleteDishEditorButton.setOnClickListener(v -> deleteDish());

    }

    private void registerDish() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.dishTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        Plato dish = factoryPlato.creaPlato(dishNameField.getText().toString(),ingredients,isVeganCheckBox.isChecked(),isGlutenFreeCheckBox.isChecked());

        values.put(Utilities.restaurant, restaurantName);
        values.put(Utilities.dishName, dish.getNombre());
        values.put(Utilities.ingredients, arrayListToString(dish.getIngredientes()));
        values.put(Utilities.price, priceField.getText().toString()); //ESTO HAY QUE CAMBIARLO
        values.put(Utilities.isVegan, dish.esVegano());
        values.put(Utilities.isGlutenFree, dish.tieneGluten());

        long index = db.insert(Utilities.dishTable, Utilities.dishName, values);
        if (index > 0) {
            Toast.makeText(getApplicationContext(), "Registrado nuevo plato", Toast.LENGTH_SHORT).show();
            db.close();
            startActivity(new Intent(getApplicationContext(), OwnerMenuActivity.class));
        } else {
            db.close();
            Toast.makeText(getApplicationContext(), "ERROR, NO SE PUDO REGISTRAR " + index, Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void updateDish() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.dishTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {restaurantName, dishName};
        Plato dish = factoryPlato.creaPlato(dishNameField.getText().toString(),ingredients,isVeganCheckBox.isChecked(),isGlutenFreeCheckBox .isChecked());

        ContentValues values = new ContentValues();
        values.put(Utilities.restaurant, restaurantName);
        values.put(Utilities.dishName, dish.getNombre());
        values.put(Utilities.ingredients, arrayListToString(dish.getIngredientes()));
        values.put(Utilities.price, priceField.getText().toString()); //ESTO HAY QUE CAMBIARLO
        values.put(Utilities.isVegan, dish.esVegano());
        values.put(Utilities.isGlutenFree, dish.tieneGluten());
        int index = db.update(Utilities.dishTable, values, Utilities.restaurant + "=? AND " + Utilities.dishName + "=?", parameters);

        if (index > 0) {
            Toast.makeText(getApplicationContext(), "Plato actualizado", Toast.LENGTH_SHORT).show();
            db.close();
            startActivity(new Intent(getApplicationContext(), OwnerMenuActivity.class));
        } else {
            db.close();
            Toast.makeText(getApplicationContext(), "ERROR, NO SE PUDO ACTUALIZAR " + index, Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteDish() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.dishTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {restaurantName, dishName};
        db.delete(Utilities.dishTable, Utilities.restaurant + "=? AND " + Utilities.dishName + "=?", parameters);

        Toast.makeText(getApplicationContext(), "Plato eliminado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), OwnerMenuActivity.class));
        db.close();
    }

    private void fillFields() {
        //Establecemos la conexión con la db
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.dishTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {restaurantName, dishName};
        //String[] fields = {Utilities.dishName, Utilities.ingredients, Utilities.isGlutenFree, Utilities.isVegan};
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + Utilities.dishTable + " WHERE restaurant=? AND name=?", parameters);
            cursor.moveToFirst();
            dishNameField.setText(cursor.getString(0));
            //El 1 es restaurant name
            String strIngredients = cursor.getString(2);
            priceField.setText(cursor.getString(3));
            isGlutenFreeCheckBox.setChecked(cursor.getInt(4) > 0);
            isVeganCheckBox.setChecked(cursor.getInt(5) > 0);
            ingredients = stringToArrayList(strIngredients);
            ingredientsListText.setText(strIngredients);
            cursor.close();
            db.close();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error en la base de datos", Toast.LENGTH_SHORT).show();
            db.close();
            e.printStackTrace();
        }
    }

    private String arrayListToString(ArrayList<String> list){
        String myString = "";
        for (int i = 0; i < list.size(); i++) {
            myString = myString+list.get(i)+", ";
        }
        return myString;
    }

    private ArrayList<String> stringToArrayList(String str) {
        ArrayList<String> list = new ArrayList<String>();
        StringTokenizer tokens=new StringTokenizer(str, ", ");
        while(tokens.hasMoreTokens()){
            list.add(tokens.nextToken());
        }
        return list;
    }

    private Boolean checkDishName() {
        String name;
        name = dishNameField.getText().toString();
        if (name.length() < 3) {
            Utilities.showToast(getApplicationContext(), "Nombre del plato incorrecto");
            return false;
        } else {
            return true;
        }
    }

    private Boolean checkPrice() {
        String price = priceField.getText().toString();
        if (price.length() < 1) {
            Utilities.showToast(getApplicationContext(), "El precio no puede estar vacío");
            return false;
        } else {
            return true;
        }
    }
}