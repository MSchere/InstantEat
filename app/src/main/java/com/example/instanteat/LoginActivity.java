package com.example.instanteat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.*;

import backend.User;

public class LoginActivity extends AppCompatActivity {
    //Variable declaration
    EditText emailField, passwordField;
    Button loginButton, registerButton, loginNoAccountButton;
    CheckBox rememberCheckBox;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String[] cacheData = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Importamos preferencias
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        //getApplicationContext().deleteDatabase("orderDB");

        emailField = findViewById(R.id.emailFieldLogin);
        passwordField = findViewById(R.id.passwordFieldLogin);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        loginNoAccountButton = findViewById(R.id.loginNoAccountButton);

        rememberCheckBox = findViewById(R.id.rememberCheckBox);

        //Ponemos las credenciales recordadas si existen
        cacheData[0] = prefs.getString("savedEmail", "");
        cacheData[1] = prefs.getString("savedPassword", "");
        emailField.setText(cacheData[0]);
        passwordField.setText(cacheData[1]);

        //Listeners
        loginButton.setOnClickListener(v -> validate());
        registerButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
        loginNoAccountButton.setOnClickListener(v -> loginNoAccount());
    }

    private void validate() {
        String email = emailField.getText().toString();
        try {
            User user = Utilities.getUser(getApplicationContext(), email, false);
            String password = user.getPassword();
            String userType = user.getUserType();
            String name = user.getName();
            String inputPassword = passwordField.getText().toString();
            if(password.equals(inputPassword)) {
                //SI EL LOGIN ES EXITOSO:
                editor.putString("email", email);
                editor.putString("name", name);
                if (rememberCheckBox.isChecked()) {
                    editor.putString("savedEmail", email);
                    editor.putString("savedPassword", inputPassword);
                }
                Utilities.showToast(getApplicationContext(), "Inicio de sesión correcto\nBienvenido " + name);
                if (userType.equals("client")) {
                    editor.putString("userType", "client");
                    //Vamos al menú
                    finish();
                    startActivity(new Intent(getApplicationContext(), ClientMenuActivity.class));
                }
                else {
                    editor.putString("userType", "owner");
                    finish();
                    startActivity(new Intent(getApplicationContext(), OwnerMenuActivity.class));
                }
                editor.commit();
            }
            else{
                Utilities.showToast(getApplicationContext(), "Contraseña incorrecta");
            }
        } catch (Exception e) {
            //Una excepción significa que no se ha encontrado el email en la db
            e.printStackTrace();
            Utilities.showToast(getApplicationContext(), "Email incorrecto");
        }
    }
    private void loginNoAccount(){
        User user = (User) Utilities.getDefaultUser().copy();
        editor.putString("email", user.getEmail());
        editor.commit();
        finish();
        startActivity(new Intent(getApplicationContext(), UserPrefsActivity.class));
    }
}