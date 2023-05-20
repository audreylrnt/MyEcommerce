package com.example.myecommerce;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.ViewHolder> {
    Context context;
    ArrayList<Product> productInit;
    ArrayList<ProductCheckout> productCheckoutInit;

    public AdapterProductList(Context context, ArrayList<Product> productInit) {
        this.context = context;
        this.productInit = productInit;
    }

    @NonNull
    @Override
    public AdapterProductList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductList.ViewHolder holder, int position) {
        Product product = productInit.get(position);
        String code = product.getProductCode();
        String name = product.getProductName();
        String currency = product.getCurrency();
        String dimension = product.getDimension();
        String unit = product.getUnit();
        int price = product.getPrice();
        int discount = product.getDiscount();
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productDetail = new Intent(context, ProductDetailActivity.class);
                productDetail.putExtra("productCode", product.getProductCode());
                productDetail.putExtra("productName", product.getProductName());
                productDetail.putExtra("productPrice", product.getPrice());
                productDetail.putExtra("productCurrency", product.getCurrency());
                productDetail.putExtra("productDiscount", product.getDiscount());
                productDetail.putExtra("productDimension", product.getDimension());
                productDetail.putExtra("productUnit", product.getUnit());
                context.startActivity(productDetail);
            }
        });
    holder.btnAddCart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ProductCheckout product = new ProductCheckout(code, name, price, currency, discount, dimension, unit, 0);
            CheckoutInit.checkoutInit.add(product);
        }
    });
    }

    @Override
    public int getItemCount() {
        return productInit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        Button btnAddCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            btnAddCart = itemView.findViewById(R.id.btnAdd);
        }
    }
}
