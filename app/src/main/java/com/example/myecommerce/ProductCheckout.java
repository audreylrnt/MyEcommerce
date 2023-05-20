package com.example.myecommerce;

public class ProductCheckout extends Product {
    private int qty;

    public ProductCheckout(String productCode, String productName, int price, String currency, int discount, String dimension, String unit, int qty) {
        super(productCode, productName, price, currency, discount, dimension, unit);
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
