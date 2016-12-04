package com.coupons.me.couponsme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 6/18/2016.
 */
public class WebAppInterface {
    Context mContext;
    private static final String COMMA_SEP = ",";

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String addCoupon(String jsonRec){
        try {
            JSONObject record = new JSONObject(jsonRec);
            CouponsMeDBHelper mDbHelper =new CouponsMeDBHelper(mContext);
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            Date date = new Date();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_STORE, record.getString("storeName"));
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_DATE, record.getString("validTill"));
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_DESC, record.getString("description"));
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE, record.getString("price"));
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED, "N");
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_CREATE_TMSTMP, getDateTime(date));
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_UPDATE_TMSTMP, getDateTime(date));

            // Insert the new row, returning the primary key value of the new row

            long newRowId = db.insert(
                    CouponsMeDbContract.Coupons.TABLE_NAME,
                    null,
                    values);
            if(newRowId!=-1){
                Toast.makeText(mContext, "Added Successfully!!!", Toast.LENGTH_SHORT).show();
                return newRowId+"";
            }else{
                Toast.makeText(mContext, "Transaction Failure!!!", Toast.LENGTH_SHORT).show();
                return "FAILURE";
            }

        }catch(JSONException ex){
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return "FAILURE";
    }

    @JavascriptInterface
    public String getCouponDetails(String pkId) {
        try {
            //List<JSONObject> result = new ArrayList<JSONObject>();
            JSONObject record = new JSONObject();
            CouponsMeDBHelper mDbHelper =new CouponsMeDBHelper(mContext);
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

           /* Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, -3); // minus 3 days
            String minus3days = getDateFormat(c.getTime());
            minus3days=minus3days+" 00:00:00:000";*/

            String query="select "+CouponsMeDbContract.Coupons._ID + COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_STORE +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DESC +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_UPDATE_TMSTMP +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_CREATE_TMSTMP +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE + " from "+CouponsMeDbContract.Coupons.TABLE_NAME+
                    " where "+CouponsMeDbContract.Coupons._ID + " = ? ";

            Cursor cur = db.rawQuery(query,new String[] {pkId});
            if (cur.getCount() > 0)
            {
                cur.moveToFirst();
              //  do {
                    //JSONObject record = new JSONObject();
                    record.put("pkId", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons._ID)));
                    record.put("storeName", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_STORE)));
                    record.put("validTill", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_DATE)));
                    record.put("description", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_DESC)));
                    record.put("isUsed", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED)));
                    record.put("price", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE)));
                    //result.add(record);
             //   } while (cur.moveToNext());
                cur.close();
            }

            //JSONArray records=new JSONArray(result);
            //JSONObject output=new JSONObject();
           //output.put("records",records);
            return record.toString();

        }catch(JSONException ex){
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


        return null;
    }
    @JavascriptInterface
    public String editCoupon(String jsonRec){
        try {
            JSONObject record = new JSONObject(jsonRec);
            CouponsMeDBHelper mDbHelper =new CouponsMeDBHelper(mContext);
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            Date date = new Date();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_STORE, record.getString("storeName"));
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_DATE, record.getString("validTill"));
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_DESC, record.getString("description"));
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE, record.getString("price"));
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED, record.getString("isUsed"));
            //values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_CREATE_TMSTMP, getDateTime(date));
            values.put(CouponsMeDbContract.Coupons.COLUMN_NAME_UPDATE_TMSTMP, getDateTime(date));

            String selection= CouponsMeDbContract.Coupons._ID +" = ?";
            String selectionArg[]={record.getString("pkId")};

           int count = db.update(
                   CouponsMeDbContract.Coupons.TABLE_NAME,
                   values,
                   selection,
                   selectionArg);
            if(count>0){
                Toast.makeText(mContext, "Saved Successfully!!!", Toast.LENGTH_SHORT).show();
                return "SUCCESS";
            }else{
                Toast.makeText(mContext, "Transaction Failure!!!", Toast.LENGTH_SHORT).show();
                return "FAILURE";
            }



        }catch(JSONException ex){
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return "FAILURE";
    }

    @JavascriptInterface
    public void deleteCoupon(String pkId){
        CouponsMeDBHelper mDbHelper =new CouponsMeDBHelper(mContext);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selection= CouponsMeDbContract.Coupons._ID +" = ?";
        String selectionArg[]={pkId};
        // Issue SQL statement.
        db.delete(CouponsMeDbContract.Coupons.TABLE_NAME, selection, selectionArg);

    }

    @JavascriptInterface
    public String searchCoupon(String jsonRec){
        try {
            List<JSONObject> result = new ArrayList<JSONObject>();
            JSONObject rec = new JSONObject(jsonRec);
            CouponsMeDBHelper mDbHelper =new CouponsMeDBHelper(mContext);
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            String storeName=rec.getString("storeName");
            String startDate=rec.getString("startDate");
            String endDate=rec.getString("endDate");
            String isUsed=rec.getString("isUsed");

            String query="select "+CouponsMeDbContract.Coupons._ID + COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_STORE +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DESC +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_UPDATE_TMSTMP +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_CREATE_TMSTMP +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE + " from "+CouponsMeDbContract.Coupons.TABLE_NAME;

            String whereClause=" where ";

            if(isUsed.equalsIgnoreCase("N")){
                whereClause += " "+CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED+" = '"+isUsed+"'";
            }else{
                whereClause += " "+CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED+" in ('Y','N') ";
            }

            if(startDate!=null && !startDate.isEmpty()){
                whereClause+= " and "+CouponsMeDbContract.Coupons.COLUMN_NAME_DATE+" >= '"+startDate+"'";
            }

            if(endDate!=null && !endDate.isEmpty()){
                whereClause+= " and "+CouponsMeDbContract.Coupons.COLUMN_NAME_DATE+" <= '"+endDate+"'";
            }

            if(storeName!=null && !storeName.isEmpty()){
                whereClause+= " and "+CouponsMeDbContract.Coupons.COLUMN_NAME_STORE+" like '%"+storeName+"%'";
            }

            String orderByClause=" order by "+CouponsMeDbContract.Coupons.COLUMN_NAME_DATE+" ASC";

            if(whereClause.equalsIgnoreCase(" where ")){
                whereClause=" ";
            }
            //Toast.makeText(mContext, query+whereClause+orderByClause, Toast.LENGTH_SHORT).show();
            Cursor cur = db.rawQuery(query+whereClause+orderByClause,null);
            if (cur.getCount() > 0)
            {
                cur.moveToFirst();
                do {
                    JSONObject record = new JSONObject();
                    record.put("pkId", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons._ID)));
                    record.put("storeName", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_STORE)));
                    record.put("validTill", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_DATE)));
                    record.put("description", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_DESC)));
                    record.put("isUsed", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED)));
                    record.put("price", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE)));
                    result.add(record);
                } while (cur.moveToNext());
                cur.close();
            }

            JSONArray records=new JSONArray(result);
            JSONObject output=new JSONObject();
            output.put("records",records);
            return output.toString();


        }catch(JSONException ex){
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return null;
    }

    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault());
        //Date date = new Date();

        return dateFormat.format(date);
    }
    private String getDateFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        //Date date = new Date();

        return dateFormat.format(date);
    }
    @JavascriptInterface
    public String recentlyAddedCoupons() {
        try {
            List<JSONObject> result = new ArrayList<JSONObject>();

        CouponsMeDBHelper mDbHelper =new CouponsMeDBHelper(mContext);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, -3); // minus 3 days
        String minus3days = getDateFormat(c.getTime());
        minus3days=minus3days+" 00:00:00:000";

  String query="select "+CouponsMeDbContract.Coupons._ID + COMMA_SEP +
          CouponsMeDbContract.Coupons.COLUMN_NAME_STORE +   COMMA_SEP +
          CouponsMeDbContract.Coupons.COLUMN_NAME_DATE +   COMMA_SEP +
          CouponsMeDbContract.Coupons.COLUMN_NAME_DESC +   COMMA_SEP +
          CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED +   COMMA_SEP +
          CouponsMeDbContract.Coupons.COLUMN_NAME_UPDATE_TMSTMP +   COMMA_SEP +
          CouponsMeDbContract.Coupons.COLUMN_NAME_CREATE_TMSTMP +   COMMA_SEP +
          CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE + " from "+CouponsMeDbContract.Coupons.TABLE_NAME+
          " where "+CouponsMeDbContract.Coupons.COLUMN_NAME_UPDATE_TMSTMP + " >= ? order by "+
          CouponsMeDbContract.Coupons.COLUMN_NAME_CREATE_TMSTMP+" desc";

        Cursor cur = db.rawQuery(query,new String[] {minus3days});
        if (cur.getCount() > 0)
        {
            cur.moveToFirst();
            do {
                JSONObject record = new JSONObject();
                record.put("pkId", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons._ID)));
                record.put("storeName", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_STORE)));
                record.put("validTill", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_DATE)));
                record.put("description", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_DESC)));
                record.put("isUsed", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED)));
                record.put("price", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE)));
                result.add(record);
            } while (cur.moveToNext());
            cur.close();
        }

            JSONArray records=new JSONArray(result);
            JSONObject output=new JSONObject();
            output.put("records",records);
            return output.toString();

        }catch(JSONException ex){
           Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


        return null;
    }

    @JavascriptInterface
    public String expiresIn7DaysCoupons() {
        try {
            List<JSONObject> result = new ArrayList<JSONObject>();

            CouponsMeDBHelper mDbHelper =new CouponsMeDBHelper(mContext);
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, 7); // add 7 days
            String add7days = getDateFormat(c.getTime());
            //add7days=add7days+" 00:00:00:000";
            String currDate=getDateFormat(new Date());

            String query="select "+CouponsMeDbContract.Coupons._ID + COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_STORE +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DESC +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_UPDATE_TMSTMP +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_CREATE_TMSTMP +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE + " from "+CouponsMeDbContract.Coupons.TABLE_NAME+
                    " where "+CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED+" = ? and "+
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE + " <= ? and "+
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE + " >= ? order by "+
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE+" asc";

            Cursor cur = db.rawQuery(query,new String[] {"N",add7days,currDate});
            if (cur.getCount() > 0)
            {
                cur.moveToFirst();
                do {
                    JSONObject record = new JSONObject();
                    record.put("pkId", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons._ID)));
                    record.put("storeName", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_STORE)));
                    record.put("validTill", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_DATE)));
                    record.put("description", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_DESC)));
                    record.put("isUsed", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED)));
                    record.put("price", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE)));
                    result.add(record);
                } while (cur.moveToNext());
                cur.close();
            }

            JSONArray records=new JSONArray(result);
            JSONObject output=new JSONObject();
            output.put("records",records);
            return output.toString();

        }catch(JSONException ex){
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


        return null;
    }
    @JavascriptInterface
    public String expiresIn30DaysCoupons() {
        try {
            List<JSONObject> result = new ArrayList<JSONObject>();

            CouponsMeDBHelper mDbHelper =new CouponsMeDBHelper(mContext);
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, 30); // add 30 days
            String add30days = getDateFormat(c.getTime());
            //add7days=add7days+" 00:00:00:000";
            String currDate=getDateFormat(new Date());

            String query="select "+CouponsMeDbContract.Coupons._ID + COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_STORE +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DESC +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_UPDATE_TMSTMP +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_CREATE_TMSTMP +   COMMA_SEP +
                    CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE + " from "+CouponsMeDbContract.Coupons.TABLE_NAME+
                    " where "+CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED+" = ? and "+
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE + " <= ? and "+
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE + " >= ? order by "+
                    CouponsMeDbContract.Coupons.COLUMN_NAME_DATE+" asc";

            Cursor cur = db.rawQuery(query,new String[] {"N",add30days,currDate});
            if (cur.getCount() > 0)
            {
                cur.moveToFirst();
                do {
                    JSONObject record = new JSONObject();
                    record.put("pkId", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons._ID)));
                    record.put("storeName", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_STORE)));
                    record.put("validTill", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_DATE)));
                    record.put("description", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_DESC)));
                    record.put("isUsed", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_IS_USED)));
                    record.put("price", cur.getString(cur.getColumnIndex(CouponsMeDbContract.Coupons.COLUMN_NAME_PRICE)));
                    result.add(record);
                } while (cur.moveToNext());
                cur.close();
            }

            JSONArray records=new JSONArray(result);
            JSONObject output=new JSONObject();
            output.put("records",records);
            return output.toString();

        }catch(JSONException ex){
            Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


        return null;
    }
}
