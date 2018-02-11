package com.example.shaimaaderbaz.inventory.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaimaaderbaz.inventory.R;
import com.example.shaimaaderbaz.inventory.adapters.ProductAdapter;
import com.example.shaimaaderbaz.inventory.data.ProductContract;
import com.example.shaimaaderbaz.inventory.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterListener {
    ProductDbHelper dbHelper;
    ProductAdapter mAdapter;
      public void insertDummy( ) {
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,"Test product");
        values.put(ProductContract.ProductEntry.COLUMN_PRICE,100);
        values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, 100);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME,"Test Supplier");
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL,"test@gmail.com");
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE, "012345678");


        Uri newUri = getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fabAddDummy = (FloatingActionButton) findViewById(R.id.fabAddDummy);
        fabAddDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDummy();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence dummyAdded = "dummy product data is added !";
                Toast toast =Toast.makeText(context,dummyAdded,duration);
                toast.show();


            }
        });


       final ListView listView = (ListView) findViewById(R.id.list_view);
       String [] projection=new String[]{
                ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_PRICE,
                ProductContract.ProductEntry.COLUMN_QUANTITY,
                ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME};

        Cursor cursor = getContentResolver().query(ProductContract.ProductEntry.CONTENT_URI, projection, null, null,null);
       if(cursor.getCount()!=0) {
           mAdapter = new ProductAdapter(MainActivity.this, cursor);
           listView.setAdapter(mAdapter);
       }
        else
       {
           TextView textVisable=(TextView)findViewById(R.id.text_visiable);
           textVisable.setVisibility(View.VISIBLE);
       }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure?");

        FloatingActionButton fabDeleteAllProd = (FloatingActionButton) findViewById(R.id.fabDeleteAll);
        fabDeleteAllProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AlertDialog alert = builder.create();
               alert.show();



            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                int id=getContentResolver().delete(ProductContract.ProductEntry.CONTENT_URI,null,null);
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence dummyAdded = "all products is deleted !";
                Toast toast =Toast.makeText(context,dummyAdded,duration);
                toast.show();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });



    }

    @Override
    public void onSaleButtonClicked(long id) {
        String[] projection = new String[]{
                ProductContract.ProductEntry.COLUMN_QUANTITY,
        };
        String quantity = "";
        Cursor cursor = getContentResolver().query(ProductContract.ProductEntry.CONTENT_URI, projection, "_ID =?", new String[]{String.valueOf(id)}, null);
        if (cursor.moveToFirst()) {
            quantity = cursor.getString(0);
        }
        int pquantity = Integer.parseInt(quantity);
        Uri uriUpd = Uri.parse("content://" + "com.example.shaimaaderbaz.inventory" + "/products/" + id);
        ContentValues values = new ContentValues();
        if (pquantity > 0) {
            values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, pquantity - 1);
            int res = getContentResolver().update(uriUpd, values, "_ID=?", new String[]{String.valueOf(id)});
            Toast.makeText(this, "Recorded Updated , one item saled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "your items is zero , no items to sale", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDetailsClicked(long id) {
        Intent i = new Intent(MainActivity.this, DetailsActivity.class);
        i.putExtra("productId", id);
        startActivity(i);
    }
}
