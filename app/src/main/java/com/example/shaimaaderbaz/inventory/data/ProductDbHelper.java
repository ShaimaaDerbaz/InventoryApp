package com.example.shaimaaderbaz.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shaimaa Derbaz on 2/6/2018.
 */

public class ProductDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProductContract.ProductEntry.TABLE_NAME + " (" +
                    ProductContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ProductContract.ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
                    ProductContract.ProductEntry.COLUMN_PRICE + " INTEGER NOT NULL," +
                    ProductContract.ProductEntry.COLUMN_QUANTITY + " INTEGER NOT NULL," +
                    ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL,"+
                    ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL + " TEXT NOT NULL,"+
                    ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE + " TEXT NOT NULL,"+
                    ProductContract.ProductEntry.COLUMN_IMAGE + " TEXT NOT NULL);";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductContract.ProductEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "ProductsInv.db";

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}