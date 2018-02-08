package com.example.shaimaaderbaz.inventory.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shaimaaderbaz.inventory.R;
import com.example.shaimaaderbaz.inventory.adapters.ProductAdapter;
import com.example.shaimaaderbaz.inventory.data.ProductContract;
import com.example.shaimaaderbaz.inventory.data.ProductDbHelper;
import com.example.shaimaaderbaz.inventory.data.ProductProvider;

public class MainActivity extends AppCompatActivity {
    ProductDbHelper dbHelper;
    ProductAdapter mAdapter;
    int lastVisibleItem = 0;
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
        // Setup FAB to open EditorActivity
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
        //View emptyView = findViewById(R.id.empty_view);
       // listView.setEmptyView(emptyView);
       String [] projection=new String[]{
                ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_PRICE,
                ProductContract.ProductEntry.COLUMN_QUANTITY,
                ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME};

        Cursor cursor = getContentResolver().query(ProductContract.ProductEntry.CONTENT_URI, projection, null, null,null);

        mAdapter = new ProductAdapter(this, cursor);
        listView.setAdapter(mAdapter);

    }
}
