package com.example.shaimaaderbaz.inventory.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        final long currentItemId = getIntent().getLongExtra("productId", 0);

        if (currentItemId != 0)
        {
            FloatingActionButton fabAddnew = (FloatingActionButton) findViewById(R.id.fabDetailsOk);
            fabAddnew.setEnabled(false);
            String [] projection=new String[]{
                    ProductContract.ProductEntry._ID,
                    ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                    ProductContract.ProductEntry.COLUMN_PRICE,
                    ProductContract.ProductEntry.COLUMN_QUANTITY,
                    ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME,
                    ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL,
                    ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE,
            };
            String pName="";
            String price="";
            String quantity="";
            String sName="";
            String sMail="";
            String sPhone="";
            Cursor cursor=getContentResolver().query(ProductContract.ProductEntry.CONTENT_URI,projection,"_ID =?",new String[] {String.valueOf(currentItemId)},null);
            if(cursor.moveToFirst())
            {
                pName=cursor.getString(1);
                price=cursor.getString(2);
                quantity=cursor.getString(3);
                sName=cursor.getString(4);
                sMail=cursor.getString(5);
                sPhone=cursor.getString(6);
                EditText editPName=(EditText)findViewById(R.id.edit_product_name);
                editPName.setText(pName);
                EditText editPrice=(EditText)findViewById(R.id.edit_price);
                editPrice.setText(price);
                EditText editQuantity=(EditText)findViewById(R.id.edit_quantity);
                editQuantity.setText(quantity);
                EditText editSName=(EditText)findViewById(R.id.edit_supplier_name);
                editSName.setText(sName);
                EditText editSMail=(EditText)findViewById(R.id.edit_supplier_mail);
                editSMail.setText(sMail);
                EditText editSPhone=(EditText)findViewById(R.id.edit_supplier_phone);
                editSPhone.setText(sPhone);


            }
            FloatingActionButton fabUpdate = (FloatingActionButton) findViewById(R.id.fabDetailsUpdate);
            fabUpdate.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    EditText editPName=(EditText)findViewById(R.id.edit_product_name);
                    String pname=editPName.getText().toString();
                    EditText editPrice=(EditText)findViewById(R.id.edit_price);
                    int pprice=Integer.parseInt(editPrice.getText().toString());
                    EditText editQuantity=(EditText)findViewById(R.id.edit_quantity);
                    int pquantity=Integer.parseInt(editQuantity.getText().toString());
                    EditText editSName=(EditText)findViewById(R.id.edit_supplier_name);
                    String sname=editSName.getText().toString();
                    EditText editSMail=(EditText)findViewById(R.id.edit_supplier_mail);
                    String smail=editSMail.getText().toString();
                    EditText editSPhone=(EditText)findViewById(R.id.edit_supplier_phone);
                    String sphone=editSPhone.getText().toString();
                    Uri uri=ProductContract.ProductEntry.BASE_CONTENT_URI.withAppendedPath(ProductContract.ProductEntry.BASE_CONTENT_URI,"/"+currentItemId);
                    ContentValues values =new ContentValues();
                    values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,pname);
                    values.put(ProductContract.ProductEntry.COLUMN_PRICE,pprice);
                    values.put(ProductContract.ProductEntry.COLUMN_QUANTITY,pquantity);
                    values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME,sname);
                    values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL,smail);
                    values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE,sphone);
                    getContentResolver().update(uri,values,"_ID=?",new String[] {String.valueOf(currentItemId)});
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence recUpdated = "record updated successfully!";
                    Toast toast =Toast.makeText(context,recUpdated,duration);
                    toast.show();
                    Intent i = new Intent(DetailsActivity.this, MainActivity.class);
                    startActivity(i);


                }
            });


        } else
        {
           // setTitle(getString(R.string.editor_activity_title_edit_item));
           // addValuesToEditItem(currentItemId);
        }

        final EditText productNameEdit=(EditText)findViewById(R.id.edit_product_name);
        final EditText productPriceEdit=(EditText)findViewById(R.id.edit_price);
        final EditText productQuantityEdit=(EditText)findViewById(R.id.edit_quantity);
        final EditText supplierNameEdit=(EditText)findViewById(R.id.edit_supplier_name);
        final EditText supplierMailEdit=(EditText)findViewById(R.id.edit_supplier_mail);
        final EditText supplierPhoneEdit=(EditText)findViewById(R.id.edit_supplier_phone);
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabDetailsOk);
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
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence recAdded = "record added successfully!";
                Toast toast =Toast.makeText(context,recAdded,duration);
                toast.show();
                Intent i = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(i);


            }
        });

    }
}
