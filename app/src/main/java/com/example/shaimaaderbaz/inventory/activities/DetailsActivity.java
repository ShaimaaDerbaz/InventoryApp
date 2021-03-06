package com.example.shaimaaderbaz.inventory.activities;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shaimaaderbaz.inventory.R;
import com.example.shaimaaderbaz.inventory.data.ProductContract;
import com.example.shaimaaderbaz.inventory.utlis.UtilisUI;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DetailsActivity extends AppCompatActivity {
    int currentQuantityText =0;
    int PICK_PHOTO_FOR_AVATAR =1;
    Uri actualUri;
    public void insertProduct(String productName ,int price , int quantity,String supplierName,String supplierMail,String supplierPhone,String imageUri ) {
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,productName);
        values.put(ProductContract.ProductEntry.COLUMN_PRICE,price);
        values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, quantity);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME,supplierName);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL,supplierMail);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE, supplierPhone);
        values.put(ProductContract.ProductEntry.COLUMN_IMAGE,imageUri);

        Uri newUri = getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                actualUri = data.getData();
                //Display an error
                return;
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final Context mContext =getApplicationContext();
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
                    ProductContract.ProductEntry.COLUMN_IMAGE
            };
            String pName="";
            String price="";
            String quantity="";
            String sName="";
            String sMail="";
            String sPhone="";
            String sImage="";
            Cursor cursor=getContentResolver().query(ProductContract.ProductEntry.CONTENT_URI,projection,"_ID =?",new String[] {String.valueOf(currentItemId)},null);
            if(cursor.moveToFirst())
            {
                pName=cursor.getString(1);
                price=cursor.getString(2);
                quantity=cursor.getString(3);
                sName=cursor.getString(4);
                sMail=cursor.getString(5);
                sPhone=cursor.getString(6);
                sImage=cursor.getString(7);
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
                ImageView imageProduct=(ImageView) findViewById(R.id.image_product);
                imageProduct.setImageURI(Uri.parse(sImage));

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
                    Uri uriUpd = Uri.parse("content://" + "com.example.shaimaaderbaz.inventory" + "/products/" + currentItemId);
                    ContentValues values =new ContentValues();
                    values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,pname);
                    values.put(ProductContract.ProductEntry.COLUMN_PRICE,pprice);
                    values.put(ProductContract.ProductEntry.COLUMN_QUANTITY,pquantity);
                    values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME,sname);
                    values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL,smail);
                    values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE,sphone);
                    int res=getContentResolver().update(uriUpd,values,"_ID=?",new String[] {String.valueOf(currentItemId)});
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence recUpdated = "record updated successfully!";
                    Toast toast =Toast.makeText(context,recUpdated,duration);
                    toast.show();
                    Intent i = new Intent(DetailsActivity.this, MainActivity.class);
                    startActivity(i);


                }
            });

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Deete item");
            builder.setMessage("Are you sure?");

            FloatingActionButton fabDeleteProduct = (FloatingActionButton) findViewById(R.id.fabDetailsDelete);
            fabDeleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alert = builder.create();
                    alert.show();

                }});
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Uri uri = Uri.parse("content://" + "com.example.shaimaaderbaz.inventory" + "/products/" + currentItemId);
                    int id = getContentResolver().delete(uri, "_ID=?", new String[]{String.valueOf(currentItemId)});
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence dummyDeleted = "record deleted !";
                    Toast toast = Toast.makeText(context, dummyDeleted, duration);
                    toast.show();
                    Intent i = new Intent(DetailsActivity.this, MainActivity.class);
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


            FloatingActionButton fabContaSupplier = (FloatingActionButton) findViewById(R.id.fabContatctSupplier);
            fabContaSupplier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText mailEdit=(EditText)findViewById(R.id.edit_supplier_mail);
                    String mail=mailEdit.getText().toString();
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Order Products");
                    i.putExtra(Intent.EXTRA_TEXT, "Kindly be noted that , I want 5000 items ");
                    Intent mailer = Intent.createChooser(i, null);
                    startActivity(mailer);

                }});

        } else
        {

        }

        final EditText productNameEdit=(EditText)findViewById(R.id.edit_product_name);
        final EditText productPriceEdit=(EditText)findViewById(R.id.edit_price);
        final EditText productQuantityEdit=(EditText)findViewById(R.id.edit_quantity);
        final EditText supplierNameEdit=(EditText)findViewById(R.id.edit_supplier_name);
        final EditText supplierMailEdit=(EditText)findViewById(R.id.edit_supplier_mail);
        final EditText supplierPhoneEdit=(EditText)findViewById(R.id.edit_supplier_phone);
        Button SelectImageButton =(Button)findViewById(R.id.select_image_button);
        SelectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabDetailsOk);
        fabAdd.setOnClickListener(new View.OnClickListener() {

           @Override
            public void onClick(View view) {
               final String productName;
               final int productPrice ;
               final int productQuantity ;
               final String supplierName;
               final String supplierMail;
               final String supplierPhone ;
                try {
                     productName = productNameEdit.getText().toString();
                     productPrice = Integer.parseInt(productPriceEdit.getText().toString());
                     productQuantity = Integer.parseInt(productQuantityEdit.getText().toString());
                     supplierName = supplierNameEdit.getText().toString();
                     supplierMail = supplierMailEdit.getText().toString();
                     supplierPhone = supplierPhoneEdit.getText().toString();
                     String uriT=actualUri.toString();
                     insertProduct(productName,productPrice,productQuantity,supplierName,supplierMail,supplierPhone,actualUri.toString());
                     Context contextAdd = getApplicationContext();
                     int durationAdd = Toast.LENGTH_SHORT;
                    CharSequence recAdded = "record added successfully!";
                    Toast toastAdd =Toast.makeText(contextAdd,recAdded,durationAdd);
                    toastAdd.show();
                    Intent i = new Intent(DetailsActivity.this, MainActivity.class);
                    startActivity(i);
                }catch (Exception e)
                {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence recNull = "please fill all feilds and correct";
                    Toast toast =Toast.makeText(context,recNull,duration);
                    toast.show();
                }
            }
        });

        Button increaseButton = (Button) findViewById(R.id.increase_button);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editQuantity=(EditText)findViewById(R.id.edit_quantity);
                try {
                    currentQuantityText = Integer.parseInt(editQuantity.getText().toString());
                    currentQuantityText++;
                    editQuantity.setText(currentQuantityText + "");
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "fill quntity feild to can increase", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button decreaseButton = (Button) findViewById(R.id.decrease_button);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editQuantity=(EditText)findViewById(R.id.edit_quantity);
                try {
                    currentQuantityText = Integer.parseInt(editQuantity.getText().toString());
                    currentQuantityText--;
                    if (currentQuantityText < 0)
                        currentQuantityText = 0;
                    editQuantity.setText(currentQuantityText + "");
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "fill quntity feild to can decrease", Toast.LENGTH_LONG).show();
                }

            }
        });




    }
}
