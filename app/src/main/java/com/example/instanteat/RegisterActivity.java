package com.example.instanteat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText emailField, passwordField, repeatPasswordField, nameField, addressField, phoneNumberField;
    Button registerButton, ownerButton;
    CheckBox offersCheckBox;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        userType = "client";

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        repeatPasswordField = findViewById(R.id.repeatPasswordField);
        addressField = findViewById(R.id.addressField);
        nameField = findViewById(R.id.nameField);
        phoneNumberField = findViewById(R.id.phoneNumberField);

        offersCheckBox = findViewById(R.id.offersCheckBox);
        registerButton = findViewById(R.id.saveButton);
        ownerButton = findViewById(R.id.ownerButton);

        registerButton.setOnClickListener(v -> {
            if (checkEmail() && checkPassword() && checkName() && checkAddress() && checkPhoneNumber()) {
                registerUser();
            }
        });

        ownerButton.setOnClickListener(v -> {
            //setContentView(R.layout.register_owner);
            nameField.setHint("nombre del local");
            ((ViewGroup) ownerButton.getParent()).removeView(ownerButton);
            ((ViewGroup) offersCheckBox.getParent()).removeView(offersCheckBox);
            userType = "owner";
        });
    }

    private void registerUser() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilities.email, emailField.getText().toString());
        values.put(Utilities.password, passwordField.getText().toString());
        values.put(Utilities.userType, userType);
        values.put(Utilities.name, nameField.getText().toString());
        values.put(Utilities.address, addressField.getText().toString());
        values.put(Utilities.phoneNumber, phoneNumberField.getText().toString());

        long index = db.insert(Utilities.userTable, Utilities.email, values);

        if (index > 0) {
            Toast.makeText(getApplicationContext(), "Registrado nuevo usuario, inicie sesión", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Índice de " + userType + " " + index, Toast.LENGTH_SHORT).show();
            db.close();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "ERROR, NO SE PUDO REGISTRAR " + index, Toast.LENGTH_SHORT).show();
        }
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