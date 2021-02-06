package com.example.instantEat;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConnectSQLiteHelper extends SQLiteOpenHelper{


    public ConnectSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilities.create_user_table);
        db.execSQL(Utilities.create_dish_table);
        db.execSQL(Utilities.create_card_table);
        db.execSQL(Utilities.create_order_table);
        db.execSQL(Utilities.insert_dummy_user);
        insertDemoData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userDB;");
        db.execSQL("drop table if exists dishDB;");
        db.execSQL("drop table if exists cardDB;");
        db.execSQL("drop table if exists orderDB;");
        onCreate(db);
    }

    public void insertDemoData(SQLiteDatabase db){
        db.execSQL("INSERT INTO userDB VALUES ('jRuiz@gmail.com', 'contraseña', 'client', 'Javier Ruíz Hidalgo', 'Calle los Perales 28b, 7ºa', 645865412);");
        db.execSQL("INSERT INTO userDB VALUES ('pGarcia@gmail.com', 'contraseña', 'client', 'Paloma García Montero', 'Calle Virgen de la Antigua 17, 2ºc', 646465234);");
        db.execSQL("INSERT INTO userDB VALUES ('ePerez@gmail.com', 'contraseña', 'client', 'Ernesto Pérez de Diego', 'Calle el Embajador 12a, 4ºb', 644146738);");
        db.execSQL("INSERT INTO userDB VALUES ('mcDonalds1@gmail.com', 'contraseña', 'owner', 'McDonalds', 'Centro Comercial Isla Azul', 946164316);");
        db.execSQL("INSERT INTO userDB VALUES ('mcDonalds2@gmail.com', 'contraseña', 'owner', 'McDonalds', 'Calle Martin Fierro 26', 946154673);");
        db.execSQL("INSERT INTO userDB VALUES ('telepizza@gmail.com', 'contraseña', 'owner', 'Telepizza', 'Plaza de Bejanque 22', 946431654);");
        db.execSQL("INSERT INTO userDB VALUES ('kfc@gmail.com', 'contraseña', 'owner', 'KFC', 'Centro Comercial Ferial Plaza', 946134576);");
        db.execSQL("INSERT INTO userDB VALUES ('istanbul@gmail.com', 'contraseña', 'owner', 'Istanbul', 'Calle Colón 15', 946154372);");

        db.execSQL("INSERT INTO cardDB VALUES (1546134245164219, 'jRuiz@gmail.com', 'Vanesa García Jiménez', 240, '08/22');");
        db.execSQL("INSERT INTO cardDB VALUES (4616841324978164, 'pGarcia@gmail.com', 'Paloma García Montero', 461, '05/23');");
        db.execSQL("INSERT INTO cardDB VALUES (9467364676451674, 'ePerez@gmail.com', 'Ernesto Pérez de Diego', 654, '05/22');");

        db.execSQL("INSERT INTO dishDB VALUES ('McPollo', 'McDonalds', 'pan, pollo, mayonesa, lechuga, tomate', 6.25, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('McVegan', 'McDonalds', 'pan, carne vegana, salsa ecológica, lechuga, tomate, cebolla', 8.20, 0, 1);");
        db.execSQL("INSERT INTO dishDB VALUES ('BigMac', 'McDonalds', 'pan doble, carne, mostaza, lechuga, cebolla', 6.45, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Cuarto de Libra', 'McDonalds', 'pan, carne, ketchup, cebolla, pepinillo', 6.25, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('BigMac sin gluten', 'McDonalds', 'pan doble sin gluten, carne, mostaza, lechuga, cebolla', 6.45, 1, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('McPollo sin gluten', 'McDonalds', 'pan sin gluten, pollo, mayonesa, lechuga, tomate', 6.25, 1, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Patatas fritas', 'McDonalds', 'patatas, aceite, sal', 1.00, 1, 1);");
        db.execSQL("INSERT INTO dishDB VALUES ('Patatas deluxe', 'McDonalds', 'patatas, aceite, sal', 1.00, 1, 1);");
        db.execSQL("INSERT INTO dishDB VALUES ('Refresco McDonalds', 'McDonalds', 'refresco', 1.50, 1, 1);");
        db.execSQL("INSERT INTO dishDB VALUES ('Mcafé', 'McDonalds', 'agua, leche, azucar, café', 1.25, 1, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('McFlurry KitKat', 'McDonalds', 'Crema helada, sirope de chocolate, kitkat', 4.50, 1, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('McFlurry Oreo', 'McDonalds', 'Crema helada, sirope de chocolate, oreo', 4.50, 1, 0);");

        db.execSQL("INSERT INTO dishDB VALUES ('Pizza Margarita', 'Telepizza', 'masa, queso, tomate', 5.25, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Pizza Carbonara', 'Telepizza', 'masa, queso, salsa carbonara, jamón', 6.75, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Pizza Veggie', 'Telepizza', 'masa, queso vegano, tomate, lechuga, canónigos, tomate natural, espinaca, cebolla', 7.25, 0, 1);");
        db.execSQL("INSERT INTO dishDB VALUES ('Pizza Barbacoa', 'Telepizza', 'masa, queso, salsa barbacoa, maíz, carne, bacon, jamón', 7.25, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Pizza Especial', 'Telepizza', 'masa, queso, tomate, carne, pimiento verde, cebolla, maíz', 6.75, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Pizza Hawaiana', 'Telepizza', 'masa, queso, tomate, piña, jamón', 6.75, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Pizza Margarita sin gluten', 'Telepizza', 'masa sin gluten, queso, tomate', 5.25, 1, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Pizza Margarita vegana sin gluten', 'Telepizza', 'masa sin gluten, queso vegano, tomate', 5.25, 1, 1);");
        db.execSQL("INSERT INTO dishDB VALUES ('Refresco Telepizza', 'Telepizza', 'refresco', 1.20, 1, 1);");

        db.execSQL("INSERT INTO dishDB VALUES ('Piezas de Pollo', 'KFC', 'pollo rebozado', 7.25, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Double Krunch', 'KFC', 'pan, pollo rebozado, lechuga, salsa', 6.15, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Double Krunch sin gluten', 'KFC', 'pan sin gluten, pollo rebozado, lechuga, salsa', 6.15, 1, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Wrap de Pollo', 'KFC', 'tortilla de trigo, pollo rebozado, lechuga, tomate, salsa', 6.50, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Wrap de Pollo sin gluten', 'KFC', 'tortilla sin gluten, pollo rebozado, lechuga, tomate, salsa', 6.50, 1, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Alitas Picantes', 'KFC', 'alitas fritas, especias picantes', 5.00, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Cubo pequeño de Pollo', 'KFC', 'pollo rebozado, alitas picantes, salsas', 12.95, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Cubo mediano de Pollo', 'KFC', 'pollo rebozado, alitas picantes, nuggets, salsas', 16.95, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Mega Cubo de Pollo', 'KFC', 'pollo rebozado, alitas, alitas picantes, nuggets, salsas', 25.95, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Refresco KFD', 'KFC', 'refresco', 1.50, 1, 1);");

        db.execSQL("INSERT INTO dishDB VALUES ('Kebab de Ternera', 'Istanbul', 'pan durum, carne kebab, salsa, lechuga, tomate, cebolla', 6.20, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Kebab de Pollo', 'Istanbul', 'pan durum, pollo kebab, salsa, lechuga, tomate, cebolla', 6.20, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Kebab Mixto', 'Istanbul', 'pan durum, carne kebab, pollo kebab, salsa, lechuga, tomate, cebolla', 6.20, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Kebab de Ternera sin gluten', 'Istanbul', 'pan durum sin gluten, carne kebab, salsa, lechuga, tomate, cebolla', 6.50, 1, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Kebab de Faláfel', 'Istanbul', 'pan durum, faláfel, salsa, lechuga, tomate, cebolla', 6.20, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Durum de Ternera', 'Istanbul', 'tortilla durum, carne kebab, salsa, lechuga, tomate, cebolla', 7.20, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Durum de Pollo', 'Istanbul', 'tortilla durum, pollo kebab, salsa, lechuga, tomate, cebolla', 7.20, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Durum Mixto', 'Istanbul', 'tortilla durum, carne kebab, pollo kebab, salsa, lechuga, tomate, cebolla', 7.20, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Durum de Ternera sin gluten', 'Istanbul', 'tortilla durum sin gluten, carne kebab, salsa, lechuga, tomate, cebolla', 7.50, 1, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Durum de Faláfel', 'Istanbul', 'tortilla durum, faláfel, salsa, lechuga, tomate, cebolla', 7.20, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Kebab de Ternera al Plato', 'Istanbul', 'carne kebab, salsa, lechuga, tomate, cebolla', 5.75, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Faláfel al Plato', 'Istanbul', 'faláfel, salsa, lechuga, tomate, cebolla', 5.75, 0, 0);");
        db.execSQL("INSERT INTO dishDB VALUES ('Refresco Istanbul', 'KFC', 'refresco', 1.25, 1, 1);");

        db.execSQL("INSERT INTO orderDB VALUES (4516, 'jRuiz@gmail.com', 645865412, 'Calle los Perales 28b, 7ºa', 'McDonalds', 'Centro Comercial Isla Azul'" +
                ", 'BigMac: 6,45€, Refresco Mconalds: 1,5€, Patatas deluxe: 1,00€, McPollo: 6,25€', 15.2, 'tarjeta', 'Preparando', '');");
        db.execSQL("INSERT INTO orderDB VALUES (1645, 'jRuiz@gmail.com', 0, '', 'Telepizza', 'Plaza de Bejanque 22'" +
                ", 'Pizza Barbacoa: 7,25€', 7.25, '', 'Subpedido', '');");

        db.execSQL("INSERT INTO orderDB VALUES (4172, 'pGarcia@gmail.com', 646465234, 'Calle Virgen de la Antigua 17, 2ºc', 'McDonalds', 'Centro Comercial Isla Azul'" +
                ", 'BigMac: 6,45€, Refresco Mconalds: 1,5€, Patatas deluxe: 1,00€, McPollo: 6,25€', 15.2, 'tarjeta', 'Preparando', '');");
        db.execSQL("INSERT INTO orderDB VALUES (2467, 'pGarcia@gmail.com', 0, '', 'Telepizza', 'Plaza de Bejanque 22'" +
                ", 'Pizza Barbacoa: 7,25€', 7.25, '', 'Subpedido', '');");

        db.execSQL("INSERT INTO orderDB VALUES (7975, 'ePerez@gmail.com', 644146738, 'Calle el Embajador 12a, 4ºb', 'McDonalds', 'Centro Comercial Isla Azul'" +
                ", 'BigMac: 6,45€, Refresco Mconalds: 1,5€, Patatas deluxe: 1,00€, McPollo: 6,25€', 15.2, 'tarjeta', 'Preparando', '');");
        db.execSQL("INSERT INTO orderDB VALUES (6543, 'ePerez@gmail.com', 0, '', 'Telepizza', 'Plaza de Bejanque 22'" +
                ", 'Pizza Barbacoa: 7,25€', 7.25, '', 'Subpedido', '');");

    }
}
