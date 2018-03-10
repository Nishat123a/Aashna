package aashna.com.aashna.S_SelfSafety;

/**
 * Created by dell on 1/12/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "contacts";
    public static final String COL_NAME = "pName";
    public static final String COL_NUM = "pNumber";


    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String STRING_CREATE = "CREATE TABLE "+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COL_NAME+" TEXT unique, "+COL_NUM+" TEXT unique)";
        db.execSQL(STRING_CREATE);
//        ContentValues cv = new ContentValues(2);
//        db.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean addContact(String Name, String Number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, Name);//user latitude
        contentValues.put(COL_NUM, Number);//user longitude


        long result = db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        //Select all Query
        Cursor data = db.rawQuery("SELECT * FROM contacts ",null);
        return  data;
    }

}