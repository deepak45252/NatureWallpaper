package com.devofindia.naturewallpaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.devofindia.naturewallpaper.Modals.OfflineModal;

import java.util.ArrayList;

public class DbSqlHelper extends SQLiteOpenHelper {

   final static String DBNAME = "naturewallpaper.db";
   final static int DBVERSION = 2;
    public DbSqlHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table wallpapers" +
                        "(id integer primary key autoincrement," +
                        "wallpaperimage int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP table if exists wallpapers");
            onCreate(db);
    }

    public boolean insertWallpaper(String wallpaperimage){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("wallpaperimage", wallpaperimage);
        long id = sqLiteDatabase.insert("wallpapers", null, contentValues);
        if (id<=0){
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<OfflineModal> getWallpaper(){
        ArrayList<OfflineModal> wpmodal = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select wallpaperimage from wallpapers", null);
        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                OfflineModal modal = new OfflineModal();
                modal.setSamplewallpaper(cursor.getString(0));
                wpmodal.add(modal);
            }
        }
        cursor.close();
        database.close();
        return wpmodal;
    }

}
