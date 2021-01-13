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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.("SET FOREIGN_KEY_CHECKS = 0;");
        db.execSQL("drop table if exists appUser;");
        db.execSQL("drop table if exists creditCard;");
        db.execSQL("drop table if exists hasCard;");
        db.execSQL("drop table if exists restaurant;");
        db.execSQL("drop table if exists dishDB;");
        db.execSQL("drop table if exists objectDB;");
        //db.execSQL("SET FOREIGN_KEY_CHECKS = 1;");
        onCreate(db);
    }
}
