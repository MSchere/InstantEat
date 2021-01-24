package com.example.instanteat;
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userDB;");
        db.execSQL("drop table if exists dishDB;");
        db.execSQL("drop table if exists cardDB;");
        db.execSQL("drop table if exists orderDB;");
        onCreate(db);
    }
}
