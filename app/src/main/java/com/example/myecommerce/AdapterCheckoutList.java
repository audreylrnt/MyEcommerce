package com.example.myecommerce;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterCheckoutList extends RecyclerView.Adapter<AdapterCheckoutList.ViewHolder> {
    Context context;
    ArrayList<ProductCheckout> checkoutInit;
    ArrayList<ProductCheckout> checkoutTemp = new ArrayList<>();
    CheckoutListener checkoutListener;

    public AdapterCheckoutList(Context context, ArrayList<ProductCheckout> checkoutInit, CheckoutListener checkoutListener) {
        this.context = context;
        this.checkoutInit = checkoutInit;
        this.checkoutListener = checkoutListener;
    }

    @NonNull
    @Override
    public AdapterCheckoutList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.checkout_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCheckoutList.ViewHolder holder, int position) {
        Product product = checkoutInit.get(position);
        String code = product.getProductCode();
        String name = product.getProductName();
        String currency = product.getCurrency();
        String dimension = product.getDimension();
        String unit = product.getUnit();
        int price = product.getPrice();
        int discount = product.getDiscount();
        holder.checkoutName.setText(product.getProductName());
        holder.checkoutPrice.setText("Subtotal : Rp 0");
        holder.etQty.setInputType(InputType.TYPE_CLASS_NUMBER);
        holder.etQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString() + "" == "") {
                    holder.checkoutPrice.setText(String.valueOf(0));
                } else {
                    holder.checkoutPrice.setText(String.valueOf(Integer.parseInt(charSequence.toString()) * product.getPrice()));
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable editable) {

                ProductCheckout product = new ProductCheckout(code, name, price, currency, discount, dimension, unit, holder.checkoutPrice.getText().toString().equals("") ? 0 : Integer.parseInt(holder.checkoutPrice.getText().toString()));
                if (product.getQty() > 0) {
                    checkoutTemp.add(product);
                }
                checkoutTemp.removeIf(item -> item.getQty() == 0);

                int totalPrice = 0;
                for (ProductCheckout item: checkoutTemp
                     ) {
                    if (item.getQty() > 0) {
                        totalPrice += item.getQty();
                    }
                }

                checkoutListener.onChangeQty(totalPrice);
            }
        });
    }

    @Override
    public int getItemCount() {
        return checkoutInit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView checkoutName, checkoutPrice;
        EditText etQty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkoutName = itemView.findViewById(R.id.checkoutName);
            checkoutPrice = itemView.findViewById(R.id.tvSubtotalPrice);
            etQty = itemView.findViewById(R.id.etQty);
        }
    }
}
