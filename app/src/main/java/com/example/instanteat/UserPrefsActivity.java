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

import androidx.appcompat.app.AppCompatActivity;

import backend.User;

public class UserPrefsActivity extends AppCompatActivity {
    EditText emailField, passwordField, nameField, addressField, phoneNumberField;
    Button saveButton, deleteAccountButton, logoffButton, addCardButton;
    CheckBox offersCheckBox;
    String userType, email;
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
        email = prefs.getString("email", "NULL");
        userType = prefs.getString("userType", "client");
        //Parámetros para hacer queries y actualizaciones en la db

        emailField = findViewById(R.id.cardHolderNameField);
        passwordField = findViewById(R.id.passwordField);
        addressField = findViewById(R.id.cardDateField);
        nameField = findViewById(R.id.cardCCVField);
        phoneNumberField = findViewById(R.id.cardNumberField);
        offersCheckBox = findViewById(R.id.offersCheckBox);

        saveButton = findViewById(R.id.saveButton);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);
        logoffButton = findViewById(R.id.logoffButton);
        addCardButton = findViewById(R.id.addCardButton);

        //Rellenamos los campos con sus datos
        fillFields();

        addCardButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), EditCardActivity.class));
        });

        saveButton.setOnClickListener(v -> {
                if (checkEmail() && checkPassword() && checkName() && checkAddress() && checkPhoneNumber()) {
                    updateUser();
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

        if (email.equals("dummy@email.com")) {
            emailField.setEnabled(false);
            passwordField.setEnabled(false);
            addressField.setText("");
            phoneNumberField.setText("");
            nameField.setEnabled(false);
            offersCheckBox.setEnabled(false);
            deleteAccountButton.setEnabled(false);
            addCardButton.setEnabled(false);
        }

        if (userType.equals("owner")){
            ((ViewGroup) addCardButton.getParent()).removeView(addCardButton);
            ((ViewGroup) offersCheckBox.getParent()).removeView(offersCheckBox);
        }

    }
    //Para rellenar los diferentes campos
    private void fillFields() {
        //Establecemos la conexión con la db
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {email};
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
            Utilities.showToast(getApplicationContext(), "Usuario no encontrado");
            db.close();
            e.printStackTrace();
        }
    }

    private void updateUser() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {prefs.getString("email", "NULL")};
        ContentValues values;
        if (email.equals("dummy@email.com")) {
            Utilities.showToast(getApplicationContext(), "Datos de usuario temporal actualizados");
            values = createDummyUser();
        }
        else {
            Utilities.showToast(getApplicationContext(), "Datos de usuario actualizados");
            values = createUser();
        }
        int index = db.update(Utilities.userTable, values, Utilities.email + "=?", parameters);
        editor.putString("email", emailField.getText().toString());
        editor.commit();
        startActivity(new Intent(UserPrefsActivity.this, ClientMenuActivity.class));
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

    private ContentValues createUser() {
        User user = new User(emailField.getText().toString(),
                passwordField.getText().toString(),
                userType,
                nameField.getText().toString(),
                addressField.getText().toString(),
                Integer.valueOf(phoneNumberField.getText().toString()));
        ContentValues values = new ContentValues();
        values.put(Utilities.email, user.getEmail());
        values.put(Utilities.password, user.getPassword());
        values.put(Utilities.userType, user.getUserType());
        values.put(Utilities.name, user.getName());
        values.put(Utilities.address, user.getAddress());
        values.put(Utilities.phoneNumber, user.getPhoneNumber());
        return values;
    }

    private ContentValues createDummyUser() {
        User user = (User) Utilities.getDefaultUser().copy();
        user.setAddress(addressField.getText().toString());
        user.setPhoneNumber(Integer.valueOf(phoneNumberField.getText().toString()));
        ContentValues values = new ContentValues();
        values.put(Utilities.email, user.getEmail());
        values.put(Utilities.password, user.getPassword());
        values.put(Utilities.userType, user.getUserType());
        values.put(Utilities.name, user.getName());
        values.put(Utilities.address, user.getAddress());
        values.put(Utilities.phoneNumber, user.getPhoneNumber());
        return values;
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