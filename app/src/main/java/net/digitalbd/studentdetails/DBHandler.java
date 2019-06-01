package net.digitalbd.studentdetails;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "std_details";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "studentTable";
    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "stdid";
    public static final String COL_4 = "contact";
    public static final String COL_5 = "details";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,name text,stdid text,contact text,details text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public List<StudentModel> getdata() {
        List<StudentModel> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " ORDER BY " + COL_2 + " ASC", null);
        StringBuffer stringBuffer = new StringBuffer();
        StudentModel studentModel = null;
        while (cursor.moveToNext()) {
            studentModel = new StudentModel();
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String stdid = cursor.getString(cursor.getColumnIndexOrThrow("stdid"));
            String contact = cursor.getString(cursor.getColumnIndexOrThrow("contact"));
            String details = cursor.getString(cursor.getColumnIndexOrThrow("details"));
            studentModel.setId(id);
            studentModel.setName(name);
            studentModel.setStdid(stdid);
            studentModel.setContact(contact);
            studentModel.setDetails(details);
            stringBuffer.append(studentModel);
            // stringBuffer.append(dataModel);
            data.add(studentModel);
        }
        return data;
    }
}
