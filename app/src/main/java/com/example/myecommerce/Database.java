package com.example.myecommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.Date;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "Ecommerce_DB";
    public static int DB_VER = 1;
    public static final String TABLE_PRODUCT_TABLE_NAME = "Product";
    public static final String TABLE_PRODUCT_CODE = "ProductCode";
    public static final String TABLE_PRODUCT_NAME = "ProductName";
    public static final String TABLE_PRODUCT_PRICE = "ProductPrice";
    public static final String TABLE_PRODUCT_CURRENCY = "ProductCurrency";
    public static final String TABLE_PRODUCT_DISCOUNT = "ProductDiscount";
    public static final String TABLE_PRODUCT_DIMENSION = "ProductDimension";
    public static final String TABLE_PRODUCT_UNIT = "ProductUnit";

    public static final String TABLE_USER_TABLE_NAME = "User";
    public static final String TABLE_USER_NAME = "Username";
    public static final String TABLE_USER_TABLE_PASSWORD = "Password";

    public static final String TABLE_TRX_HEADER_TABLE_NAME = "TransactionHeader";
    public static final String TABLE_TRX_HEADER_DOC_CODE = "DocumentCode";
    public static final String TABLE_TRX_HEADER_DOC_NUMBER = "DocumentNumber";
    public static final String TABLE_TRX_HEADER_USER = "DocumentUser";
    public static final String TABLE_TRX_HEADER_TOTAL = "DocumentTotal";
    public static final String TABLE_TRX_HEADER_DATE = "DocumentDate";

    public static final String TABLE_TRX_DETAIL_TABLE_NAME = "TransactionDetail";
    public static final String TABLE_TRX_DETAIL_DOC_CODE = "DetailCode";
    public static final String TABLE_TRX_DETAIL_DOC_NUMBER = "DetailNumber";
    public static final String TABLE_TRX_DETAIL_PRODUCT_CODE = "DetailProductCode";
    public static final String TABLE_TRX_DETAIL_PRODUCT_PRICE = "DetailProductPrice";
    public static final String TABLE_TRX_DETAIL_QTY = "DetailProductQty";
    public static final String TABLE_TRX_DETAIL_UNIT = "DetailProductUnit";
    public static final String TABLE_TRX_DETAIL_CURRENCY = "DetailProductCurrency";
    public static final String TABLE_TRX_DETAIL_SUBTOTAL = "DetailProductSubtotal";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String createTableProduct = "CREATE TABLE " + TABLE_PRODUCT_TABLE_NAME + " (" +
                TABLE_PRODUCT_CODE + " TEXT, " +
                TABLE_PRODUCT_NAME + " TEXT, " +
                TABLE_PRODUCT_CURRENCY + " TEXT, " +
                TABLE_PRODUCT_PRICE + " INTEGER, " +
                TABLE_PRODUCT_DISCOUNT + " INTEGER, " +
                TABLE_PRODUCT_DIMENSION + " TEXT, " +
                TABLE_PRODUCT_UNIT + " TEXT)";

        final String createTableUser = "CREATE TABLE " + TABLE_USER_TABLE_NAME + " (" +
                TABLE_USER_NAME + " TEXT, " +
                TABLE_USER_TABLE_PASSWORD + " TEXT)";

        final String createTableTrxHeader = "CREATE TABLE " + TABLE_TRX_HEADER_TABLE_NAME + " (" +
                TABLE_TRX_HEADER_DOC_CODE + " TEXT, " +
                TABLE_TRX_HEADER_DOC_NUMBER + " TEXT, " +
                TABLE_TRX_HEADER_USER + " TEXT, " +
                TABLE_TRX_HEADER_TOTAL + " INTEGER, " +
                TABLE_TRX_HEADER_DATE + " TEXT)";

        final String createTableTrxDetail = "CREATE TABLE " + TABLE_TRX_DETAIL_TABLE_NAME + " (" +
                TABLE_TRX_DETAIL_DOC_CODE + " TEXT, " +
                TABLE_TRX_DETAIL_DOC_NUMBER + " TEXT, " +
                TABLE_TRX_DETAIL_PRODUCT_CODE + " TEXT, " +
                TABLE_TRX_DETAIL_PRODUCT_PRICE + " INTEGER, " +
                TABLE_TRX_DETAIL_QTY + " INTEGER, " +
                TABLE_TRX_DETAIL_UNIT + " TEXT, " +
                TABLE_TRX_DETAIL_CURRENCY + " TEXT, " +
                TABLE_TRX_DETAIL_SUBTOTAL + " INTEGER)";

        Product p1 = new Product("SKUSKILNP", "So klin Pewangi", 15000, "IDR", 10, "13 cm x 10 cm", "PCS");
        Product p2 = new Product("SKUGIVB", "Giv Biru", 11000, "IDR", 0, "13 cm x 10 cm", "PCS");
        Product p3 = new Product("SKUSKILNL", "So klin Liquid", 18000, "IDR", 0, "13 cm x 10 cm", "PCS");
        Product p4 = new Product("SKUGIVK", "Giv Kuning", 10000, "IDR", 0, "13 cm x 10 cm", "PCS");

        ProductInit.productInit.add(p1);
        ProductInit.productInit.add(p2);
        ProductInit.productInit.add(p3);
        ProductInit.productInit.add(p4);

        User user1 = new User("Smit", "_sm1t_OK");
        UserInit.userInit.add(user1);

        sqLiteDatabase.execSQL(createTableProduct);
        sqLiteDatabase.execSQL(createTableUser);
        sqLiteDatabase.execSQL(createTableTrxHeader);
        sqLiteDatabase.execSQL(createTableTrxDetail);

        for (int i = 0; i < UserInit.userInit.size(); i++) {
            ContentValues cv = new ContentValues();
            User user = UserInit.userInit.get(i);
            cv.put(TABLE_USER_NAME, user.getUsername());
            cv.put(TABLE_PRODUCT_NAME, user.getPassword());
            sqLiteDatabase.insert(TABLE_USER_TABLE_NAME, null, cv);
        }

        for (int i = 0; i < ProductInit.productInit.size(); i++) {
            ContentValues cv = new ContentValues();
            Product product = ProductInit.productInit.get(i);
            cv.put(TABLE_PRODUCT_CODE, product.getProductCode());
            cv.put(TABLE_PRODUCT_NAME, product.getProductName());
            cv.put(TABLE_PRODUCT_PRICE, product.getPrice());
            cv.put(TABLE_PRODUCT_CURRENCY, product.getCurrency());
            cv.put(TABLE_PRODUCT_DISCOUNT, product.getDiscount());
            cv.put(TABLE_PRODUCT_DIMENSION, product.getDimension());
            cv.put(TABLE_PRODUCT_UNIT, product.getUnit());
            sqLiteDatabase.insert(TABLE_PRODUCT_TABLE_NAME, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF NOT EXISTS " + TABLE_PRODUCT_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF NOT EXISTS " + TABLE_USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF NOT EXISTS " + TABLE_TRX_HEADER_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getProduct() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_PRODUCT_TABLE_NAME, null);
        return cursor;
    }

    public Cursor getUserData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_USER_TABLE_NAME, null);
        return cursor;
    }

    public boolean purchaseDataHeader(String docCode, String docNumber, String docUser, int total, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TABLE_TRX_HEADER_DOC_CODE, docCode);
        cv.put(TABLE_TRX_HEADER_DOC_NUMBER, docNumber);
        cv.put(TABLE_TRX_HEADER_USER, docUser);
        cv.put(TABLE_TRX_HEADER_TOTAL, total);
        cv.put(TABLE_TRX_HEADER_DATE, date);
        long result = db.insert(TABLE_TRX_HEADER_TABLE_NAME, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

}
