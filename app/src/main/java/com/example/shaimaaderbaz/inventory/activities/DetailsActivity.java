package com.example.shaimaaderbaz.inventory.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.shaimaaderbaz.inventory.R;
import com.example.shaimaaderbaz.inventory.data.ProductContract;

public class DetailsActivity extends AppCompatActivity {
    public void insertProduct(String productName ,int price , int quantity,String supplierName,String supplierMail,String supplierPhone ) {
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,productName);
        values.put(ProductContract.ProductEntry.COLUMN_PRICE,price);
        values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, quantity);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME,supplierName);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL,supplierMail);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE, supplierPhone);


        Uri newUri = getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);
    }

    public  void displayData(String [] projection)
    {
           projection=new String[]{
                ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_PRICE,
                ProductContract.ProductEntry.COLUMN_QUANTITY,
                ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME};
        Uri uri;
        Cursor cursor=getContentResolver().query(ProductContract.ProductEntry.CONTENT_URI,projection,null,null,null);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final EditText productNameEdit=(EditText)findViewById(R.id.edit_product_name);
        final EditText productPriceEdit=(EditText)findViewById(R.id.edit_price);
        final EditText productQuantityEdit=(EditText)findViewById(R.id.edit_quantity);
        final EditText supplierNameEdit=(EditText)findViewById(R.id.edit_supplier_name);
        final EditText supplierMailEdit=(EditText)findViewById(R.id.edit_supplier_mail);
        final EditText supplierPhoneEdit=(EditText)findViewById(R.id.edit_supplier_phone);
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabDetails);
        fabAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final String productName =productNameEdit.getText().toString();
                final int productPrice =Integer.parseInt(productPriceEdit.getText().toString());
                final int productQuantity =Integer.parseInt(productQuantityEdit.getText().toString());
                final String supplierName =supplierNameEdit.getText().toString();
                final String supplierMail =supplierMailEdit.getText().toString();
                final String supplierPhone =supplierPhoneEdit.getText().toString();
                insertProduct(productName,productPrice,productQuantity,supplierName,supplierMail,supplierPhone);

            }
        });

    }
}
