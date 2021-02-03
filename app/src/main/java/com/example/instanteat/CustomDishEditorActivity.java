package com.example.instanteat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import backend.AbstractFactoryPlato;
import backend.IAbstractFactoryPlato;
import backend.Plato;
import backend.User;

public class CustomDishEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView totalPriceText;
    Button finishOrderEditorButton, finishDishButton;
    Spinner spinner0, spinner1, spinner2, spinner3, spinner4, spinner5, spinner6;
    SpinnerAdapter adapter0, adapter1, adapter2, adapter3, adapter4, adapter5, adapter6;
    ArrayList<String> ingredients0, ingredients1, ingredients2, ingredients3, ingredients4, ingredients5, ingredients6;
    int[] options = new int[7];
    Boolean initialized = false; //Si no crashea porque ejecuta onItemSelected sin preguntar
    Boolean isSuborder;
    RadioButton radioButton, burgerRadioButton, pizzaRadioButton, saladRadioButton;
    RadioGroup radioGroup;
    DecimalFormat df = new DecimalFormat("#.00");
    AbstractFactoryPlato factoryPlato = new AbstractFactoryPlato();
    IAbstractFactoryPlato iAbstractFactoryPlato;
    User restaurant;
    ArrayList<String> dishes, prices;
    String restaurantName, option;
    Double totalPrice;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dish_editor);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bundle = getIntent().getExtras();
        restaurant = (User) bundle.getSerializable("restaurant");
        isSuborder = bundle.getBoolean("isSuborder");
        restaurantName = restaurant.getName();

        totalPriceText = findViewById(R.id.totalPriceText);
        finishOrderEditorButton = findViewById(R.id.finishOrderEditorButton);
        finishDishButton = findViewById(R.id.finishDishButton);

        dishes = bundle.getStringArrayList("dishesList");
        prices = bundle.getStringArrayList("pricesList");
        totalPrice = bundle.getDouble("totalPrice");
        restaurant = (User) bundle.getSerializable("restaurant");

        radioGroup = findViewById(R.id.radioGroup);
        burgerRadioButton = findViewById(R.id.burgerRadioButton);
        pizzaRadioButton = findViewById(R.id.pizzaRadioButton);
        saladRadioButton = findViewById(R.id.saladRadioButton);

        initializeSpinners();

        iAbstractFactoryPlato = factoryPlato;

        option = "Ensalada";
        checkButton(saladRadioButton);
        setTitle("Pedido de " + restaurantName);
        totalPriceText.setText(df.format(totalPrice)+" €");

        finishDishButton.setOnClickListener(v -> {
            Plato plato = null;
            switch (option){
                case "Hamburguesa":
                    plato = factoryPlato.creaHamburguesa("Hamburguesa personalizada", options);
                    prices.add("5.50");
                    break;
                case "Pizza":
                    plato = factoryPlato.creaPizza("Pizza personalizada",options);
                    prices.add("7");
                    break;
                case "Ensalada":
                    plato = factoryPlato.creaEnsalada("Ensalada personalizada", options);
                    prices.add("4.50");
                    break;
            }
            totalPrice = Double.valueOf(prices.get(prices.size()-1))+totalPrice;
            totalPriceText.setText(df.format(totalPrice) + " €");
            dishes.add(plato.getNombre());
        });

        finishOrderEditorButton.setOnClickListener(v -> {
            Intent intent;
            if (isSuborder) {
                 intent = new Intent(getApplicationContext(), SuborderSummary.class);
            }
            else {
                 intent = new Intent(getApplicationContext(), OrderSummary.class);
            }
            bundle.putBoolean("isUpdate", false);
            bundle.putStringArrayList("dishesList", dishes); //Parámetros para la actividad
            bundle.putStringArrayList("pricesList", prices);
            bundle.putDouble("totalPrice", totalPrice);
            bundle.putSerializable("restaurant", restaurant);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    public void checkButton(View v){
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        option = radioButton.getText().toString();
        switch (option){
            case "Hamburguesa":
                ingredients0 = new ArrayList<>(Arrays.asList("Sin pan","Pan normal","Pan de masa madre","Pan sin gluten"));
                ingredients1 = new ArrayList<>(Arrays.asList("Sin lechuga", "Lechuga"));
                ingredients2 = new ArrayList<>(Arrays.asList("Sin tomate", "Tomate"));
                ingredients3 = new ArrayList<>(Arrays.asList("Sin cebolla", "Cebolla cruda","Cebolla caramelizada"));
                ingredients4 = new ArrayList<>(Arrays.asList("Sin bacon", "Bacon"));
                ingredients5 = new ArrayList<>(Arrays.asList("Sin carne", "Carne de ternera","Carne de pollo","Carne vegana"));
                ingredients6 = new ArrayList<>(Arrays.asList("Cualquier punto", "Poco hecha","Al punto","Muy hecha"));
                break;
            case "Pizza":
                ingredients0 = new ArrayList<>(Arrays.asList("Cualquier masa", "Masa normal","Masa fina","Masa sin gluten"));
                ingredients1 = new ArrayList<>(Arrays.asList("Borde sin relleno", "Borde relleno de queso"));
                ingredients2 = new ArrayList<>(Arrays.asList("Sin primera base", "Tomate", "Salsa barbacoa"));
                ingredients3 = new ArrayList<>(Arrays.asList("Sin segunda base", "Queso"));
                ingredients4 = new ArrayList<>(Arrays.asList("Sin primer ingrediente", "Bacon","Pollo","Espinacas"));
                ingredients5 = new ArrayList<>(Arrays.asList("Sin segundo ingrediente", "Anchoas","Maiz","Salami"));
                ingredients6 = new ArrayList<>(Arrays.asList("Sin tercer ingrediente", "Champiñones","Pimiento","Aceitunas"));
                break;
            case "Ensalada":
                ingredients0 = new ArrayList<>(Arrays.asList("Sin base","Lechuga","Canonigos","Rúcula"));
                ingredients1 = new ArrayList<>(Arrays.asList("Sin carne", "Pollo", "Pavo"));
                ingredients2 = new ArrayList<>(Arrays.asList("Sin queso", "Queso chedar","Queso azul"));
                ingredients3 = new ArrayList<>(Arrays.asList("Sin maiz", "Maiz"));
                ingredients4 = new ArrayList<>(Arrays.asList("Sin tomate", "Tomate", "Tomates cherry"));
                ingredients5 = new ArrayList<>(Arrays.asList("Sin aceitunas", "Aceitunas negras","Aceitunas verdes"));
                ingredients6 = new ArrayList<>(Arrays.asList("Sin salsa", "Mayonesa", "Alioli", "Cesar"));
                break;
        }
        initialized = true;
        setAdapters();
        updateSpinners();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        Spinner spinner = (Spinner) parent;
        if (initialized) {
            switch (spinner.getId()) {
                case R.id.spinner0:
                    options[0] = ingredients0.indexOf(((TextView) view).getText().toString());
                    break;
                case R.id.spinner1:
                    options[1] = ingredients1.indexOf(((TextView) view).getText().toString());
                    break;
                case R.id.spinner2:
                    options[2] = ingredients2.indexOf(((TextView) view).getText().toString());
                    break;
                case R.id.spinner3:
                    options[3] = ingredients3.indexOf(((TextView) view).getText().toString());
                    break;
                case R.id.spinner4:
                    options[4] = ingredients4.indexOf(((TextView) view).getText().toString());
                    break;
                case R.id.spinner5:
                    options[5] = ingredients5.indexOf(((TextView) view).getText().toString());
                    break;
                case R.id.spinner6:
                    options[6] = ingredients6.indexOf(((TextView) view).getText().toString());
                    break;
            }
        }
    }

    private void initializeSpinners(){
        spinner0 = findViewById(R.id.spinner0);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);
        spinner6 = findViewById(R.id.spinner6);

        spinner0.setOnItemSelectedListener(this);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(this);
        spinner5.setOnItemSelectedListener(this);
        spinner6.setOnItemSelectedListener(this);

        updateSpinners();
    }

    private void updateSpinners(){
        spinner0.setSelection(1);
        spinner1.setSelection(1);
        spinner2.setSelection(1);
        spinner3.setSelection(1);
        spinner4.setSelection(1);
        spinner5.setSelection(1);
        spinner6.setSelection(1);
    }

    private void setAdapters(){
        adapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingredients0);
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingredients1);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingredients2);
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingredients3);
        adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingredients4);
        adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingredients5);
        adapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingredients6);

        spinner0.setAdapter(adapter0);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);
        spinner5.setAdapter(adapter5);
        spinner6.setAdapter(adapter6);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}