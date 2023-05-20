package com.example.myecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProductDetailActivity extends AppCompatActivity {
    TextView tvProductDetailName, tvProductDetailPrice, tvProductDetailDimension, tvProductDetailUnit;
    Button btnBuyProductDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        tvProductDetailName = findViewById(R.id.productDetailName);
        tvProductDetailPrice = findViewById(R.id.productDetailPrice);
        tvProductDetailDimension = findViewById(R.id.productDetailDimension);
        tvProductDetailUnit = findViewById(R.id.productDetailUnit);
        btnBuyProductDetail = findViewById(R.id.btnBuyProductDetail);

        Intent productDetail = getIntent();
        String code = productDetail.getStringExtra("productCode");
        String name = productDetail.getStringExtra("productName");
        String currency = productDetail.getStringExtra("productCurrency");
        String dimension = productDetail.getStringExtra("productDimension");
        String unit = productDetail.getStringExtra("productUnit");
        int price = productDetail.getIntExtra("productPrice", 0);
        int discount = productDetail.getIntExtra("productDiscount", 0);

        tvProductDetailName.setText(name);
        tvProductDetailPrice.setText(String.valueOf(price));
        tvProductDetailDimension.setText(dimension);
        tvProductDetailUnit.setText(unit);

        btnBuyProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductCheckout product = new ProductCheckout(code, name, price, currency, discount, dimension, unit, 0);
                CheckoutInit.checkoutInit.add(product);
            }
        });
    }
}