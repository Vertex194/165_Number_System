package dk.yj;

import static dk.yj.MemberDAO.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {
    // 資料庫名稱
    public static final String DATABASE_NAME = "scam_Gang.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;
    // 資料庫物件，固定的欄位變數
    private static SQLiteDatabase db;
    // 建構子，在一般的應用都不需要修改
    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public static SQLiteDatabase getDatabase(Context context) {
        if (db == null || !db.isOpen()) {
            db = new DBHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }
        return db;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MemberDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        // 刪除原有的表格
        db.execSQL("DROP TABLE IF EXISTS " + InsertActivity.TABLE_NAME);
        // 呼叫onCreate建立新版的表格
        onCreate(db);
    }
}
