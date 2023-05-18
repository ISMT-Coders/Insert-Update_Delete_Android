package com.example.studentdetails;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Db_Helper extends SQLiteOpenHelper {
    private  Context context;
    public static final String DB_NAME = "Student.db";
    public static final String TABLE_NAME = "table_Name";
    public static final String COL1 = "Roll_Num";
    public static final String COL2 = "name";
    public static final String COL3 = "address";
    private static final int DB_VERSION = 1;


    public Db_Helper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create the table with three columns: Roll_Num, NAME, and ADDRESS
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(Roll_Num INTEGER PRIMARY KEY, " +
                "name TEXT," +"address TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop the table if it exists and recreate it
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String rollNum, String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("Roll_Num", rollNum);
            contentValues.put("NAME", name);
            contentValues.put("ADDRESS", address);
            long result = db.insert("table_Name", null, contentValues);
            success = (result != -1); // Check if the insert was successful
        } catch (SQLException e) {
            // Handle the exception here
            e.printStackTrace();
        } finally {
            db.close();
        }
        return success;
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db !=null){
            cursor = db.rawQuery(query, null);
        } return cursor;
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean dataExists(String rollNum) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE " + COL1 + " = ?";
            cursor = db.rawQuery(query, new String[]{rollNum});
            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }
    void updataData(String rollNum, String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1,rollNum);
        cv.put(COL2, name);
        cv.put(COL3,address);

        long result = db.update("table_Name",cv,COL1+"=?", new String[]{rollNum});
        if (result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneRow(String rollNum){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("table_Name",COL1+"=?", new String[]{rollNum} );
        if (result == -1){
            Toast.makeText(context, "Failed To delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
