package com.example.shaimaaderbaz.inventory.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shaimaaderbaz.inventory.R;
import com.example.shaimaaderbaz.inventory.data.ProductContract;
import com.example.shaimaaderbaz.inventory.activities.MainActivity;

/**
 * Created by Shaimaa Derbaz on 2/8/2018.
 */

public class ProductAdapter extends CursorAdapter {

    private  Activity context=new MainActivity();

    private ProductAdapterListener mProductAdapterListener;

    public interface ProductAdapterListener {
        void onSaleButtonClicked (long id);
        void onDetailsClicked(long id);
    }

    public ProductAdapter(Activity context, Cursor c) {
        super(context, c, 0);
        this.context = context;
        if (context instanceof ProductAdapterListener)
            mProductAdapterListener = (ProductAdapterListener) context;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        final TextView nameTextView = (TextView) view.findViewById(R.id.productNameDisplay);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantityDisplay);
        TextView priceTextView = (TextView) view.findViewById(R.id.priceDisplay);
        LinearLayout productDetails = (LinearLayout) view.findViewById(R.id.product_details_layout);
        FloatingActionButton saleButton = (FloatingActionButton) view.findViewById(R.id.fabSale);
        ImageView productImage = (ImageView) view.findViewById(R.id.itemImageDisplay);

        String productName = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME));
        final int quantity = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_QUANTITY));
        String price = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRICE));
        Uri imageUri=Uri.parse(cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_IMAGE)));

        final long id = cursor.getLong(cursor.getColumnIndex(ProductContract.ProductEntry._ID));
        productDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mProductAdapterListener != null)
                    mProductAdapterListener.onDetailsClicked(id);
            }
        });

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mProductAdapterListener !=null)
                    mProductAdapterListener.onSaleButtonClicked(id);
            }
        });

        nameTextView.setText(productName);
        quantityTextView.setText(String.valueOf(quantity));
        priceTextView.setText(price);
        productImage.setImageURI(imageUri);


    }
}


