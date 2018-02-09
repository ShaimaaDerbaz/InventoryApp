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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
    public void clickOnViewItem(long id) {
        Intent i = new Intent(MainActivity.this, DetailsActivity.class);
        i.putExtra("productId", id);
        startActivity(i);
    }

    public void clickOnSale(long id, int quantity) {
        String []projection=new String[]{ProductContract.ProductEntry.COLUMN_QUANTITY};
        Cursor cursor=getContentResolver().query(ProductContract.ProductEntry.CONTENT_URI,projection,"_ID =?",new String[] {String.valueOf(id)},null);
        String quantityP="";
        if(cursor.moveToFirst())
        {
            quantityP=cursor.getString(1);
        }
        int pquantity=Integer.parseInt(quantityP);
        Uri uri = Uri.parse("content://" + "com.example.shaimaaderbaz.inventory" + "/products/" + id);
        ContentValues values =new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_QUANTITY,pquantity);
        // int res=getContentResolver().update(uri,values,"_ID=?",new String[] {String.valueOf(currentId)});
        cursor.close();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence recUpdated = "record updated successfully!";
        Toast toast =Toast.makeText(context,recUpdated,duration);
        toast.show();
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        startActivity(i);
        // dbHelper.sellOneItem(id, quantity);
        // adapter.swapCursor(dbHelper.readStock());
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final long  currentId=id;
             /*   FloatingActionButton fabSale = (FloatingActionButton) findViewById(R.id.fabSale);
                fabSale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                String[] projection = new String[]{ProductContract.ProductEntry.COLUMN_QUANTITY};
                Cursor cursor = getContentResolver().query(ProductContract.ProductEntry.CONTENT_URI, projection, "_ID =?", new String[]{String.valueOf(currentId)}, null);
                String quantityP = "";
                if (cursor.moveToFirst()) {
                    quantityP = cursor.getString(1);
                }
                int pquantity = Integer.parseInt(quantityP);
                Uri uri = Uri.parse("content://" + "com.example.shaimaaderbaz.inventory" + "/products/" + currentId);
                ContentValues values = new ContentValues();
                values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, pquantity);
                // int res=getContentResolver().update(uri,values,"_ID=?",new String[] {String.valueOf(currentId)});
                cursor.close();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence recUpdated = "record updated successfully!";
                Toast toast = Toast.makeText(context, recUpdated, duration);
                toast.show();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
            }});*/
                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                i.putExtra("productId", id);
                startActivity(i);

            }
        });

        FloatingActionButton fabDeleteAllProd = (FloatingActionButton) findViewById(R.id.fabDeleteAll);
        fabDeleteAllProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] projection=new String[]{
                        ProductContract.ProductEntry._ID,
                        ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                        ProductContract.ProductEntry.COLUMN_PRICE,
                        ProductContract.ProductEntry.COLUMN_QUANTITY,
                        ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME,
                        ProductContract.ProductEntry.COLUMN_IMAGE};
                int id=getContentResolver().delete(ProductContract.ProductEntry.CONTENT_URI,null,null);
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence dummyAdded = "all data is deleted !";
                Toast toast =Toast.makeText(context,dummyAdded,duration);
                toast.show();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);


            }
        });



    }

}
