package com.example.instanteat;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserPrefsActivity extends AppCompatActivity {
    EditText emailField, passwordField, nameField, addressField, phoneNumberField;
    Button saveButton, deleteAccountButton, logoffButton, addCardButton;
    CheckBox offersCheckBox;
    String userType;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_prefs);
        //Importamos preferencias
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        //Recuperamos el tipo de usuario desde la caché
        userType = prefs.getString("userType", "client");
        //Parámetros para hacer queries y actualizaciones en la db

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        addressField = findViewById(R.id.addressField);
        nameField = findViewById(R.id.nameField);
        phoneNumberField = findViewById(R.id.phoneNumberField);
        offersCheckBox = findViewById(R.id.offersCheckBox);

        saveButton = findViewById(R.id.saveButton);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);
        logoffButton = findViewById(R.id.logoffButton);
        addCardButton = findViewById(R.id.addCardButton);

        //Rellenamos los campos con sus datos
        fillFields();

        if (userType.equals("owner")){
            ((ViewGroup) addCardButton.getParent()).removeView(addCardButton);
            ((ViewGroup) offersCheckBox.getParent()).removeView(offersCheckBox);
        }

        saveButton.setOnClickListener(v -> {
            if (userType.equals("client")) {
                if (checkEmail() && checkPassword() && checkName() && checkAddress() && checkPhoneNumber()) {
                    updateUser();
                }
            }
            else {
                if (checkEmail() && checkPassword() && checkName() && checkPhoneNumber()) {
                    updateUser();
                }
            }
        });

        logoffButton.setOnClickListener(v -> {
            //Borramos todas las actividades anteriores
            startActivity(new Intent(getApplicationContext(), LoginActivity.class).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK));
        });

        deleteAccountButton.setOnClickListener(v -> deleteUser());
    }
    //Para rellenar los diferentes campos
    private void fillFields() {
        //Establecemos la conexión con la db
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {prefs.getString("email", "NULL")};
        String[] fields = {Utilities.email, Utilities.password, Utilities.name, Utilities.address, Utilities.phoneNumber};
        try {
            Cursor cursor = db.query(Utilities.userTable, fields, Utilities.email + "=?", parameters, null, null, null);
            cursor.moveToFirst();
            emailField.setText(cursor.getString(0));
            passwordField.setText(cursor.getString(1));
            nameField.setText(cursor.getString(2));
            addressField.setText(cursor.getString(3));
            phoneNumberField.setText(cursor.getString(4));
            cursor.close();
            db.close();
        }
        catch (Exception e){
            Utilities.showToast(getApplicationContext(), "Error en la base de datos");
            db.close();
            e.printStackTrace();
        }
    }

    private void updateUser() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {prefs.getString("email", "NULL")};

        ContentValues values = new ContentValues();
        values.put(Utilities.email, emailField.getText().toString());
        values.put(Utilities.password, passwordField.getText().toString());
        values.put(Utilities.name, nameField.getText().toString());
        values.put(Utilities.address, addressField.getText().toString());
        values.put(Utilities.phoneNumber, phoneNumberField.getText().toString());
        db.update(Utilities.userTable, values, Utilities.email+"=?", parameters);

        editor.putString("name", nameField.getText().toString());
        editor.putString("email", emailField.getText().toString());
        editor.commit();
        Utilities.showToast(getApplicationContext(), "Datos de usuario actualizados");
        //startActivity(new Intent(UserPrefsActivity.this, MenuActivity.class));
        db.close();
    }

    private void deleteUser() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {prefs.getString("email", "NULL")};
        db.delete(Utilities.userTable, Utilities.email+"=?", parameters);

        Utilities.showToast(getApplicationContext(), "Usuario eliminado");

        startActivity(new Intent(UserPrefsActivity.this, LoginActivity.class));
        db.close();
    }

    private Boolean checkPassword() {
        String password;
        password = passwordField.getText().toString();
        if (password.length() < 5) {
            Utilities.showToast(getApplicationContext(), "La contraseña debe tener como mínimo 5 caracteres");
            return false;
        } else {
            return true;
        }
    }

    private Boolean checkPhoneNumber() {
        String phoneNumber = phoneNumberField.getText().toString();
        if (phoneNumber.length() == 9) {
            return true;
        } else {
            Utilities.showToast(getApplicationContext(), "Teléfono incorrecto");
            return false;
        }
    }

    private Boolean checkEmail() {
        String email = emailField.getText().toString();
        //Regular expression for email E.g: john123@gmail.com
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.matches(regex)) {
            return true;
        }
        else {
            Utilities.showToast(getApplicationContext(), "Email incorrecto");
            return false;
        }
    }

    private Boolean checkName() {
        if (nameField.getText().toString().length() > 0) {
            return true;
        }
        else{
            Utilities.showToast(getApplicationContext(), "Nombre incorrecto");
                return false;
            }
    }

    private Boolean checkAddress() {
        if (addressField.getText().toString().length() > 0) {
            return true;
        }
        else {
            Utilities.showToast(getApplicationContext(), "Dirección incorrecta");
            return false;
        }
    }
}