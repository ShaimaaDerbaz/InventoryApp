package com.example.shaimaaderbaz.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Shaimaa Derbaz on 2/6/2018.
 */

public final class ProductContract {

    private ProductContract() {}

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";

        public static final String _ID= BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME="productname";
        public static final String COLUMN_PRICE ="price";
        public static final String  COLUMN_QUANTITY="quantity";
        public static final String COLUMN_SUPPLIER_NAME="suppliername";
        public static final String COLUMN_SUPPLIER_PHONE="supplierphone";
        public static final String COLUMN_SUPPLIER_EMAIL="supplieremail";
        public static final String COLUMN_IMAGE="image";


        public static final String CONTENT_AUTHORITY = "com.example.shaimaaderbaz.inventory";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);//the basic to use and mke append to it
        public static final String PATH_PRODUCTS = "products";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact";
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;




    }
    }
