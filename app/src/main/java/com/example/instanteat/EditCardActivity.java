package com.example.instanteat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import backend.Tarjeta;

public class EditCardActivity extends AppCompatActivity {
    SharedPreferences prefs;
    Button saveCardButton, deleteCardButton;
    EditText cardHolderNameField, cardNumberField, cardCCVField, cardDateField;
    DateFormat dateFormat = new SimpleDateFormat("MM/yy");
    Bundle bundle;
    String email, dishName;
    Boolean isUpdate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_card);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bundle = getIntent().getExtras();
        dishName = "null";
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = prefs.getString("email", "NULL");

        cardHolderNameField = findViewById(R.id.cardHolderNameField);
        cardNumberField = findViewById(R.id.cardNumberField);
        cardCCVField = findViewById(R.id.cardCCVField);
        cardDateField = findViewById(R.id.cardDateField);

        saveCardButton = findViewById(R.id.saveCardButton);
        deleteCardButton = findViewById(R.id.deleteCardButton);

        fillFields();

        saveCardButton.setOnClickListener(v -> {
            if (checkCardNumber() && checkCCV() && checkName() && checkDate()) {
                if (isUpdate) updateCard();
                else registerCard();
            }
        });

        deleteCardButton.setOnClickListener(v -> deleteCard());

        if(!isUpdate) {
            deleteCardButton.setEnabled(false);
        }
    }

    private void registerCard() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.cardTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = createCard();

        long index = db.insert(Utilities.cardTable, Utilities.cardNumber, values);
        if (index > 0) {
            Toast.makeText(getApplicationContext(), "Registrada nueva tarjeta", Toast.LENGTH_SHORT).show();
            db.close();
            finish();
        } else {
            db.close();
            Toast.makeText(getApplicationContext(), "ERROR, NO SE PUDO REGISTRAR " + index, Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void updateCard() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.cardTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {email};
        ContentValues values = createCard();
        int index = db.update(Utilities.cardTable, values, Utilities.email + "=?", parameters);
        if (index > 0) {
            Toast.makeText(getApplicationContext(), "Tarjeta actualizada", Toast.LENGTH_SHORT).show();
            db.close();
            finish();
        } else {
            db.close();
            Toast.makeText(getApplicationContext(), "ERROR, NO SE PUDO ACTUALIZAR " + index, Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCard() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.cardTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {email};
        db.delete(Utilities.cardTable, Utilities.email + "=?", parameters);

        Toast.makeText(getApplicationContext(), "Tarjeta eliminada", Toast.LENGTH_SHORT).show();
        db.close();
        finish();
    }

    private void fillFields() {
        //Establecemos la conexión con la db
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.cardTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {email};
        String[] fields = {"*"};
        try {
            Cursor cursor = db.query(Utilities.cardTable, fields, Utilities.email + "=?", parameters, null, null, null);
            cursor.moveToFirst();
            //number, email, holderName, ccv, date
            cardNumberField.setText(cursor.getString(0));
            cardHolderNameField.setText(cursor.getString(2));
            cardCCVField.setText(cursor.getString(3));
            cardDateField.setText(cursor.getString(4));
            isUpdate = true;
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Tarjeta no encontrada", Toast.LENGTH_SHORT).show();
            db.close();
            //e.printStackTrace();
            isUpdate = false;
        }
        db.close();
    }

    //Instancia la clase tarjeta y mete sus datos en un content values
    private ContentValues createCard() {
        Date date = processDate();
        Tarjeta card = new Tarjeta(Long.parseLong(cardNumberField.getText().toString()), email,
                cardHolderNameField.getText().toString(),
                Integer.parseInt(cardCCVField.getText().toString()), date);
        ContentValues values = new ContentValues();
        values.put(Utilities.cardNumber, card.getCardNumber());
        values.put(Utilities.email, card.getEmail());
        values.put(Utilities.cardHolderName, card.getCardHolderName());
        values.put(Utilities.ccv, card.getCcv());
        values.put(Utilities.date, cardDateField.getText().toString());
        return values;
    }

    private Date processDate(){
        Date date = new Date();
        try {
            date = dateFormat.parse(cardDateField.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private Boolean checkName() {
        if (cardHolderNameField.getText().toString().length() > 0) {
            return true;
        }
        else{
            Utilities.showToast(getApplicationContext(), "Nombre incorrecto");
            return false;
        }
    }

    private Boolean checkDate() {
        String date = cardDateField.getText().toString();
        //Regular expression for email E.g: john123@gmail.com
        String regex = "\\d{2}/\\d{2}";
        if (date.matches(regex)) {
            return true;
        }
        else {
            Utilities.showToast(getApplicationContext(), "Fecha incorrecta");
            return false;
        }
    }

    private Boolean checkCardNumber() {
        String number;
        number = cardNumberField.getText().toString();
        if (number.length() == 16) {
            return true;
        } else {
            Utilities.showToast(getApplicationContext(), "Número de tarjeta incorrecto");
            return false;
        }
    }

    private Boolean checkCCV() {
        String number = cardCCVField.getText().toString();
        if (number.length() == 3) {
            return true;
        } else {
            Utilities.showToast(getApplicationContext(), "CCV incorrecto");
            return false;
        }
    }
}