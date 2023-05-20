package com.example.myecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class CheckoutActivity extends AppCompatActivity implements CheckoutListener {
    RecyclerView checkoutList;
    AdapterCheckoutList adapterCheckoutList;
    TextView tvTotalPrice;
    Button btnConfirm;
    Database dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        checkoutList = findViewById(R.id.checkoutList);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnConfirm = findViewById(R.id.btnConfirm);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapterCheckoutList = new AdapterCheckoutList(this, CheckoutInit.checkoutInit, this);
        checkoutList.setLayoutManager(layoutManager);
        checkoutList.setAdapter(adapterCheckoutList);
        dbHelper = new Database(this);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String docCode = "TRX";
                String docNumber = String.valueOf(CheckoutInit.checkoutInit.size() + 1);
                String user = "Smit";
                String date = new Date().toString();
                int total = Integer.parseInt(tvTotalPrice.getText().toString());
                boolean isInserted = dbHelper.purchaseDataHeader(docCode, docNumber, user, total, date);
                if (isInserted) {
                    Toast.makeText(CheckoutActivity.this, "Purchase success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckoutActivity.this, "Purchase failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onChangeQty(int totalPrice) {
        tvTotalPrice.setText(String.valueOf(totalPrice));
    }
}