package com.example.myecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProductListActivity extends AppCompatActivity {
    RecyclerView productList;
    Database databaseHelper;
    AdapterProductList adapterProductList;
    Button btnCheckout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        productList = findViewById(R.id.productList);
        btnCheckout = findViewById(R.id.btnCheckout);
        databaseHelper = new Database(this);
        getAllProduct();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapterProductList = new AdapterProductList(this, ProductInit.productInit);
        productList.setLayoutManager(layoutManager);
        productList.setAdapter(adapterProductList);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkout = new Intent(ProductListActivity.this, CheckoutActivity.class);
                startActivity(checkout);
            }
        });
    }

    private void getAllProduct() {
        Cursor cursor = databaseHelper.getProduct();
        ProductInit.productInit.clear();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String productCode = cursor.getString(cursor.getColumnIndex(Database.TABLE_PRODUCT_CODE));
            @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex(Database.TABLE_PRODUCT_NAME));
            @SuppressLint("Range") String productDimension = cursor.getString(cursor.getColumnIndex(Database.TABLE_PRODUCT_DIMENSION));
            @SuppressLint("Range") String productUnit = cursor.getString(cursor.getColumnIndex(Database.TABLE_PRODUCT_UNIT));
            @SuppressLint("Range") String productCurrency = cursor.getString(cursor.getColumnIndex(Database.TABLE_PRODUCT_CURRENCY));
            @SuppressLint("Range") int productPrice = cursor.getInt(cursor.getColumnIndex(Database.TABLE_PRODUCT_PRICE));
            @SuppressLint("Range") int productDiscount = cursor.getInt(cursor.getColumnIndex(Database.TABLE_PRODUCT_DISCOUNT));

            Product product = new Product(productCode, productName, productPrice, productCurrency, productDiscount, productDimension, productUnit);
            ProductInit.productInit.add(product);
        }
    }
}