package com.example.instanteat;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

import backend.AbstractFactoryPlato;
import backend.Pedido;
import backend.Plato;
import backend.User;

public class Utilities {
    //User table variables
    public static final String userTable = "userDB";
    public static final String email = "email";
    public static final String password = "user_password";
    public static final String userType = "user_type";
    public static final String name = "name";
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

    public static final String orderTable ="orderDB";
    public static final String id = "id";
    public static final String clientAddress = "client_address";
    public static final String restaurantAddress = "restaurant_address";
    public static final String state = "state";
    public static final String dishes = "dishes";
    public static final String totalPrice = "total_price";
    public static final String client = "client";
    public static final String paymentMethod = "payment_method";


    public static final String objectTable = "ObjectDB";
    public static final String object = "object";

    public static final String create_user_table = "create table " + userTable + "\n" +
            "(" + email + " varchar(40) primary key,\n" +
            password + " varchar(20) not null,\n" +
            userType + " varchar(8) check (user_type in ('client', 'owner', 'rider')),\n" +
            name + " varchar(40) not null,\n" +
            address + " varchar(50),\n" +
            phoneNumber + " varchar(9),\n" +
            object + " blob);";

    public static final String insert_dummy_user = "INSERT INTO " + userTable + " (dummy@email.com, dummy, client, dummy, dummy, 999999999);";

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

    public static final String create_order_table = "create table " + orderTable + "\n" +
            "(" + id + " int primary key,\n" +
            email + " varchar(40) not null,\n" +
            phoneNumber + " int not null,\n" +
            clientAddress + " varchar(40) not null,\n" +
            restaurant + " varchar(40) not null,\n" +
            restaurantAddress + " blob not null,\n" +
            dishes + " varchar(40) not null,\n" +
            totalPrice + " double not null,\n" +
            paymentMethod + " varchar(40) not null,\n" +
            state + " varchar(9) not null);";




    public static final User getUser(Context context, String parameter, Boolean byName) {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(context, Utilities.userTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String method;
        User user = null;
        if (byName) method = Utilities.name;
        else method = Utilities.email;
        String[] parameters = {parameter};
        String[] fields = {"*"};
        Cursor cursor;
        try {
            cursor = db.query(Utilities.userTable, fields, method + "=?", parameters, null, null, null);
            cursor.moveToFirst();
            user = new User(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5));
            cursor.close();
            db.close();
        }
        catch (Exception e){
            //showToast(context, "Email incorrecto");
            db.close();
            e.printStackTrace();
        }
        return user;
    }

    public static final ArrayList<Pedido> getOrders(Context context, String[] parameters, boolean byOpenOrders) {
        ArrayList<Pedido> list = new ArrayList<Pedido>();
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(context, orderTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String method;
        //name, address, state
        if (byOpenOrders) method = Utilities.restaurant + "=? AND " + Utilities.restaurantAddress + "=? AND " + Utilities.state + "=?";
        else method = Utilities.email + "=?";
        String[] fields = {"*"};
        try {
            Cursor cursor = db.query(orderTable, fields, method, parameters, null, null, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //id, email, telefono, dircliente, restaurante, dirrestaurante, platos, total, pago, estado
                    list.add(new Pedido(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            stringToArrayList(cursor.getString(6)), cursor.getDouble(7),
                            cursor.getString(8), cursor.getString(9)));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            db.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            showToast(context, "No tiene pedidos");
        }
        return list;
    }

    public static final void insertOrder(Context context, int id, String email, int phoneNumber,  String clientAddress,
                                         String restaurant, String restaurantAddress, ArrayList<String> dishes,
                                         double totalPrice, String paymentMethod, String state) {

        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(context, orderTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilities.id, String.valueOf(id));
        values.put(Utilities.email, email);
        values.put(Utilities.phoneNumber, String.valueOf(phoneNumber));
        values.put(Utilities.clientAddress, clientAddress);
        values.put(Utilities.restaurant, restaurant);
        values.put(Utilities.restaurantAddress, restaurantAddress);
        values.put(Utilities.dishes, arrayListToString(dishes));
        values.put(Utilities.totalPrice, totalPrice);
        values.put(Utilities.paymentMethod, paymentMethod);
        values.put(Utilities.state, state);
        try {
            long index = db.insert(Utilities.orderTable, Utilities.id, values);
            showToast(context, "Pedido creado");
        }
        catch (Exception e) {
            e.printStackTrace();
            showToast(context, "error creando pedido");
        }
        db.close();
    }

    public static final void updateOrderState(Context context, int orderId, String state) {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(context, Utilities.orderTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {String.valueOf(orderId)};
        ContentValues values = new ContentValues();
        values.put(Utilities.state, state);
        int index = db.update(Utilities.orderTable, values, Utilities.id + "=?", parameters);

        if (index > 0) {
            Toast.makeText(context, "Pedido actualizado", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            db.close();
            Toast.makeText(context, "Error, no se pudo actualizar " + index, Toast.LENGTH_SHORT).show();
        }
    }

    public static final void deleteOrders(Context context, String email) {
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(context, Utilities.orderTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parameters = {email};
        try {
            db.delete(Utilities.orderTable, Utilities.email + "=?", parameters);
            showToast(context, "Pedidos eliminados");
            db.close();
        }
        catch (Exception e){
            e.printStackTrace();
            showToast(context, "No hay pedidos");
        }
    }

    public static final ArrayList<Plato> getDishList(Context context, String restaurant) {
        ArrayList<Plato> list = new ArrayList<Plato>();
        AbstractFactoryPlato factoryPlato = new AbstractFactoryPlato();
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(context, Utilities.dishTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] fields = {"*"};
        String[] parameters = {restaurant};
        try {
            Cursor cursor = db.query(Utilities.dishTable, fields, Utilities.restaurant + "=?", parameters, null, null, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //nombre, restaurante, ingredientes, precio, vegano, gluten
                    list.add(factoryPlato.creaPlato(cursor.getString(0),
                            cursor.getString(1),
                            Utilities.stringToArrayList(cursor.getString(2)),
                            cursor.getDouble(3),
                            cursor.getInt(4) > 0,
                            cursor.getInt(5) > 0));
                    cursor.moveToNext();
                }
            }
            cursor.close();
            db.close();
        }
        catch (Exception e) {
            showToast(context, "ERROR EN LA BASE DE DATOS");
            db.close();
            e.printStackTrace();
        }
        return list;
    }

    public static final ArrayList<Plato> getDishList(Context context) {
        ArrayList<Plato> list = new ArrayList<Plato>();
        AbstractFactoryPlato factoryPlato = new AbstractFactoryPlato();
        ConnectSQLiteHelper conn = new ConnectSQLiteHelper(context, Utilities.dishTable, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] fields = {"*"};
        try {
            Cursor cursor = db.query(Utilities.dishTable, fields, null, null, null, null, null);
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
        }
        catch (Exception e) {
            showToast(context, "ERROR EN LA BASE DE DATOS");
            db.close();
            e.printStackTrace();
        }
        return list;
    }

    public static final String formatPrice(Double d){
        return new DecimalFormat("#.##").format(d) + "€";
    }

    public static final String arrayListToString(ArrayList<String> list){
        String myString = "";
        for (String item:list) {
            if (list.indexOf(item) == (list.size() -1)){
                myString = myString + item;
            }
            else myString = myString + item + ", ";
        }
        return myString;
    }

    public static final ArrayList<String> stringToArrayList(String str) {
        String[] arr = str.split(", ");
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(arr));
        return list;
    }

    public static final User getDefaultUser(){
        User user = new User("dummy@email.com", "dummy", "client", "dummy", "dummyAddress", 999999999);
        return user;
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