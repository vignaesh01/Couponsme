package com.coupons.me.couponsme;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.coupons.me.couponsme.CouponsMeDbContract;
/**
 * Created by user on 6/17/2016.
 */
public class CouponsMeDBHelper extends SQLiteOpenHelper{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "DueDateDB.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CouponsMeDbContract.Coupons.TABLE_NAME + " (" +
                    CouponsMeDbContract.Coupons._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_STORE + TEXT_TYPE + COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED + TEXT_TYPE + COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_UPDATE_TMSTMP + TEXT_TYPE + COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_CREATE_TMSTMP + TEXT_TYPE + COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE + TEXT_TYPE+
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CouponsMeDbContract.Coupons.TABLE_NAME;

    public CouponsMeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
