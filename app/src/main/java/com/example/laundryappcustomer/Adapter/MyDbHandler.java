package com.example.laundryappcustomer.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.laundryappcustomer.NotificationPOJO;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=3;
    private static final String DATABASE_NAME = "Laundry_App";
    private static final String TABLE_LAUNDRY = "Laundry_App_Table";
    private static final String KEY_NOTIFICATIONTITLE = "NOTIFICATION_TITLE";
    private static final String KEY_NOTIFICATIONBODY = "NOTIFICATION_BODY";
    public MyDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    public MyDbHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String LAUNDRY_APPTABLE = "CREATE TABLE " + TABLE_LAUNDRY + "("+" id INTEGER primary key autoincrement," + KEY_NOTIFICATIONTITLE + " TEXT," + KEY_NOTIFICATIONBODY+ " TEXT" +")";
        db.execSQL(LAUNDRY_APPTABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAUNDRY);
        onCreate(db);

    }
    public void addNotification(NotificationPOJO notificationPOJO)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTIFICATIONTITLE,notificationPOJO.getNotification_POJO_Body());
        values.put(KEY_NOTIFICATIONBODY,notificationPOJO.getNotification_POJO_Title());
        db.insert(TABLE_LAUNDRY,null,values);
        db.close();


    }
    public List<NotificationPOJO> getAllNotifications()
    {

        List<NotificationPOJO> notificationPOJOList = new ArrayList<NotificationPOJO>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LAUNDRY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NotificationPOJO notificationPOJO = new NotificationPOJO();
                notificationPOJO.setId(Integer.parseInt(cursor.getString(0)));
                notificationPOJO.setNotification_POJO_Title(cursor.getString(1));
                notificationPOJO.setNotification_POJO_Body(cursor.getString(2));


                // Adding contact to list
                notificationPOJOList.add(notificationPOJO);
            } while (cursor.moveToNext());
        }

        // return contact list
        return notificationPOJOList;
    }
}
