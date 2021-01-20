package com.example.instanteat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import backend.User;
import backend.ViewRemoverDecorator;

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

        emailField = findViewById(R.id.cardHolderNameField);
        passwordField = findViewById(R.id.passwordField);
        repeatPasswordField = findViewById(R.id.repeatPasswordField);
        addressField = findViewById(R.id.cardDateField);
        nameField = findViewById(R.id.cardCCVField);
        phoneNumberField = findViewById(R.id.cardNumberField);

        offersCheckBox = findViewById(R.id.offersCheckBox);
        registerButton = findViewById(R.id.saveButton);
        ownerButton = findViewById(R.id.ownerButton);

        registerButton.setOnClickListener(v -> {
            if (checkEmail() && checkPassword() && checkName() && checkAddress() && checkPhoneNumber()) {
                registerUser();
            }
        });

        ownerButton.setOnClickListener(v -> {
            nameField.setHint("nombre del local");
            ViewRemoverDecorator decorator = new ViewRemoverDecorator(getApplicationContext(), new View[]{ownerButton, offersCheckBox});
            decorator.decorate();
            userType = "owner";
        });
    }

    private void registerUser() {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(this, Utilities.userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = createUser();

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