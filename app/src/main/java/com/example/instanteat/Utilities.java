package com.example.instanteat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Utilities {
    //User table variables
    public static final String userTable = "userDB";
    public static final String email = "email";
    public static final String password = "user_password";
    public static final String userType = "user_type";
    public static final String name = "name";
    public static final String surname = "surname";
    public static final String address = "address";
    public static final String phoneNumber = "phone_number";

    public static final String dishTable = "dishDB";
    public static final String dishName = "name";
    public static final String restaurant = "restaurant";
    public static final String ingredients = "ingredients";
    public static final String isGlutenFree = "gluten_free";
    public static final String isVegan = "vegan";
    public static final String price = "price";

    public static final String cardTable = "cardDB";
    public static final String cardNumber = "card_number";
    public static final String cardHolderName = "holder_name";
    public static final String ccv = "ccv";
    public static final String date = "date";

    public static final String objectTable = "ObjectDB";
    public static final String object = "object";
    public static final String id = "id";

    public static final String create_user_table = "create table " + userTable + "\n" +
            "(" + email + " varchar(40) primary key,\n" +
            password + " varchar(20) not null,\n" +
            userType + " varchar(8) check (user_type in ('client', 'owner', 'rider')),\n" +
            name + " varchar(40) not null,\n" +
            address + " varchar(50),\n" +
            phoneNumber + " varchar(9),\n" +
            object + " blob);";

    public static final String create_dish_table = "create table " + dishTable + "\n" +
            "(" + dishName + " varchar(40) primary key,\n" +
            restaurant + " varchar(40) not null,\n" +
            ingredients + " varchar(40) not null,\n" +
            price + " double not null,\n" +
            isGlutenFree + " bit not null,\n" +
            isVegan + " bit not null);";

    public static final String create_card_table = "create table " + cardTable + "\n" +
            "(" + cardNumber + " long primary key,\n" +
            email + " varchar(40) not null,\n" +
            cardHolderName + " varchar(40) not null,\n" +
            ccv + " int not null,\n" +
            date + " varchar(40) not null);";

    public static final String create_object_table = "create table " + objectTable + "\n" +
            "(" + id + " int primary key,\n" +
            object + " blob not null);";

    public static final ArrayList<Plato> getObjects(Context context, String userEmail) {
        ArrayList<Plato> dishes = new ArrayList<Plato>();
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(context, userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] fields = {object};
        String[] parameters = {userEmail};

            Cursor cursor = db.query(userTable, fields, email + "=?", parameters, null, null, null);
            cursor.moveToFirst();
            byte[] blob = cursor.getBlob(0);
            String json = new String(blob);
            Gson gson = new Gson();
            dishes = gson.fromJson(json, new TypeToken<ArrayList<Plato>>() {}.getType());
            cursor.close();

        db.close();
        return dishes;
    }

    public static final ArrayList<Plato> getDishes(Context context) {
        ArrayList<Plato> list = new ArrayList<Plato>();
        AbstractFactoryPlato factoryPlato = new AbstractFactoryPlato();
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(context, dishTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] fields = {"*"};
        Cursor cursor = db.query(userTable, fields, "*", null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //nombre, restaurante, ingredientes, precio, vegano, gluten
                list.add(factoryPlato.creaPlato(cursor.getString(0),
                        cursor.getString(1),
                        stringToArrayList(cursor.getString(2)),
                        cursor.getDouble(3),
                        cursor.getInt(4) > 0,
                        cursor.getInt(5) > 0));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    public static final void addDishes(Context context, ArrayList<Plato> dishes, String userEmail) {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(context, userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {userEmail};
        Gson gson = new Gson();
        ContentValues values = new ContentValues();
        values.put(object, gson.toJson(dishes).getBytes());
        try {
            db.update(Utilities.userTable, values, Utilities.email+"=?", parameters);
            showToast(context, "PLATO ACTUALIZADO");
        }
        catch (Exception e) {
            e.printStackTrace();
            showToast(context, "ERROR ACTUALIZANDO PLATO");
        }
        db.close();
    }

    public static final String arrayListToString(ArrayList<String> list){
        String myString = "";
        for (int i = 0; i < list.size(); i++) {
            myString = myString+list.get(i)+", ";
        }
        return myString;
    }

    public static final ArrayList<String> stringToArrayList(String str) {
        ArrayList<String> list = new ArrayList<String>();
        StringTokenizer tokens=new StringTokenizer(str, ", ");
        while(tokens.hasMoreTokens()){
            list.add(tokens.nextToken());
        }
        return list;
    }

    public static final void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    public static final void deleteDb(Context context) {
        //context.deleteDatabase("userDB");
        context.deleteDatabase("dishDB");
        //context.deleteDatabase("objectDB");
    }
}