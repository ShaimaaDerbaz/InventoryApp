package com.example.shaimaaderbaz.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Shaimaa Derbaz on 2/7/2018.
 */

public class ProductProvider extends ContentProvider {
    private ProductDbHelper mObHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

        sUriMatcher.addURI("com.example.shaimaaderbaz.inventory", "products", 100);
        sUriMatcher.addURI("com.example.shaimaaderbaz.inventory", "products/#", 101);
    }
    @Override
    public boolean onCreate()
    {
        mObHelper=new ProductDbHelper(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase database=mObHelper.getReadableDatabase();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case 100:
                if (TextUtils.isEmpty(sortOrder))
                    sortOrder = "_ID ASC";
                cursor=database.query(ProductContract.ProductEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case 101:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor=database.query(ProductContract.ProductEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            default:
              cursor=null;
        }
        return cursor;
    }
    public Uri insertProduct(Uri uri, ContentValues contentValues)
    {
        SQLiteDatabase database = mObHelper.getWritableDatabase();

        //validation
        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME)) {
            String name = contentValues.getAsString(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }

        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_PRICE)) {
            Integer price = contentValues.getAsInteger(ProductContract.ProductEntry.COLUMN_PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("products requires price");
            }
        }

        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_QUANTITY)) {
            Integer quantity = contentValues.getAsInteger(ProductContract.ProductEntry.COLUMN_QUANTITY);
            if (quantity != null && quantity < 0) {
                throw new IllegalArgumentException("product requires valid quantity");
            }
        }
        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME)) {
        String supplierName = contentValues.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME);
        if (supplierName == null) {
            throw new IllegalArgumentException("Product requires a name");
        }
        }
        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL)) {
            String supplierMail = contentValues.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL);
            if (supplierMail == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }
        if (contentValues.containsKey(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE)) {
            String supplierPhone = contentValues.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE);
            if (supplierPhone == null) {
                throw new IllegalArgumentException("Product requires a supplier phoe");
            }
        }
        // Insert the new product with the given values
        long id = database.insert(ProductContract.ProductEntry.TABLE_NAME, null, contentValues);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(" ", "Failed to insert row for " + uri);
            return null;
        }


        return ContentUris.withAppendedId(uri,id);
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME)) {
            String name = values.getAsString(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }

        if (values.containsKey(ProductContract.ProductEntry.COLUMN_PRICE)) {
            Integer price = values.getAsInteger(ProductContract.ProductEntry.COLUMN_PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("products requires price");
            }
        }

        if (values.containsKey(ProductContract.ProductEntry.COLUMN_QUANTITY)) {
            Integer quantity = values.getAsInteger(ProductContract.ProductEntry.COLUMN_QUANTITY);
            if (quantity != null && quantity < 0) {
                throw new IllegalArgumentException("Pet requires valid weight");
            }
        }
        if (values.containsKey(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME)) {
            String supplierName = values.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME);
            if (supplierName == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }
        if (values.containsKey(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL)) {
            String supplierMail = values.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL);
            if (supplierMail == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }
        if (values.containsKey(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE)) {
            String supplierPhone = values.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE);
            if (supplierPhone == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mObHelper.getWritableDatabase();
        return database.update(ProductContract.ProductEntry.TABLE_NAME, values, selection, selectionArgs);
    }
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (sUriMatcher.match(uri)) {
            case 100:
               return insertProduct(uri,contentValues);
            default:
                throw new IllegalArgumentException("not supported "+uri);
        }

    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case 100:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case 10:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mObHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case 100:
                // Delete all rows that match the selection and selection args
                return database.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
            case 101:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case 100:
                return ProductContract.ProductEntry.CONTENT_LIST_TYPE;
            case 10:
                return ProductContract.ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
