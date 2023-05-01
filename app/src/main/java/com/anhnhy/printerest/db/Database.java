package com.anhnhy.printerest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.anhnhy.printerest.model.User;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "pinterest_test.db";
    private final static int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table user
        String sql = "create table user(" +
                "id integer primary key autoincrement," +
                "name text," +
                "email text," +
                "password text," +
                "unique (email))";
        db.execSQL(sql);
//        // create table favorite
         sql = "create table favorite(" +
                "id integer primary key autoincrement," +
                "user_id integer, " +
                "image_id integer, " +
                "foreign key(user_id) references user(id))";
//        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**********************************************************************************************
     ***************************************** QUERY USER *****************************************
     **********************************************************************************************/

    // create super user
    public long createSuperUser(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("user", null, values);
    }

    // search User by email
    public User getUserByEmail(String email) {
        String where = "email=?";
        String[] args = {email};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("user", null, where, args, null,
                null, null);
        if (rs != null && rs.moveToNext()) {
            return new User(rs.getInt(0), rs.getString(1),
                    rs.getString(2), rs.getString(3));
        }
        return null;
    }

    public int updatePassword(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        String where = "id=?";
        String[] args = {Integer.toString(user.getId())};
        SQLiteDatabase st = getWritableDatabase();
        return st.update("user", values, where, args);
    }
}
