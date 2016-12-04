package com.coupons.me.couponsme;
import android.provider.BaseColumns;
/**
 * Created by user on 6/17/2016.
 */
public class CouponsMeDbContract {
    public CouponsMeDbContract() {}
    /* Inner class that defines the table contents */
    public static abstract class Coupons implements BaseColumns {
        public static final String TABLE_NAME = "Coupons";
        //public static final String COLUMN_NAME_ID = "Id";
        public static final String COLUMN_NAME_STORE = "StoreName";
        public static final String COLUMN_NAME_DATE = "ExpiryDt";
        public static final String COLUMN_NAME_DESC = "Description";
        public static final String COLUMN_NAME_IS_USED = "IsUsed";
        public static final String COLUMN_NAME_PRICE = "Price";
        public static final String COLUMN_NAME_UPDATE_TMSTMP="UpdateTimestamp";
        public static final String COLUMN_NAME_CREATE_TMSTMP = "CreatedTimestamp";


    }
}
