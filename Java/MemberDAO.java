package dk.yj;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import java.text.SimpleDateFormat;


// 資料功能類別
public class MemberDAO {
    // 表格名稱
    public static final String TABLE_NAME = "show";
    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";
    // 其它需要的表格欄位名稱
    public static final String SKILL_COLUMN = "skill";
    public static final String PHONE_COLUMN = "phone";
    public static final String DATETIME_COLUMN = "datetime";
    public static final String NOTE_COLUMN = "note";
    // 所有欄位名稱陣列，把所有表格欄位名稱變數湊起來建立一個字串陣列
    public static final String[] COLUMNS = {KEY_ID, SKILL_COLUMN, PHONE_COLUMN, DATETIME_COLUMN,NOTE_COLUMN};
    // 顯示用欄位名稱陣列，
    // 在資料查詢畫面上希望顯示位置表格的編號、日期時間和說明，
    // 所以額外使用三個表格欄位名稱變數建立這個欄位名稱陣列
    public static final String[] SHOW_COLUMNS = {KEY_ID,SKILL_COLUMN,PHONE_COLUMN,DATETIME_COLUMN,NOTE_COLUMN};
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SKILL_COLUMN + " TEXT NOT NULL, " +
                    PHONE_COLUMN + " TEXT NOT NULL, " +
                    DATETIME_COLUMN + " TEXT NOT NULL, " +
                    NOTE_COLUMN + " TEXT NOT NULL)";
    // 資料庫物件
    private SQLiteDatabase db;
    // 建構子，一般的應用都不需要修改
    public MemberDAO(Context context) {
        db = DBHelper.getDatabase(context);
    }
    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }
    // 新增參數指定的物件
    public Member insert(Member member) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(SKILL_COLUMN, member.getSkill());
        cv.put(PHONE_COLUMN, member.getPhone());
        cv.put(DATETIME_COLUMN, member.getDatetime());
        cv.put(NOTE_COLUMN, member.getNote());
        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);
        // 設定編號
        member.setId(id);
        // 回傳結果
        return member;
    }
    // 修改參數指定的物件
    public boolean update(Member member) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(SKILL_COLUMN, member.getSkill());
        cv.put(PHONE_COLUMN, member.getPhone());
        cv.put(DATETIME_COLUMN, member.getDatetime());
        cv.put(NOTE_COLUMN, member.getNote());
        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = KEY_ID + "=" + member.getId();
        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }
    // 取得指定編號的資料物件
    public Member get(long id) {
        // 準備回傳結果用的物件
        Member member = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(TABLE_NAME, COLUMNS, where,
                null, null, null, null, null);
        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            member = getRecord(result);
        }
        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return member;
    }
    // 把Cursor目前的資料包裝為物件
    public Member getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        Member result = new Member();
        result.setId(cursor.getLong(0));
        result.setSkill(cursor.getString(1));
        result.setPhone(cursor.getString(2));
        result.setDatetime(cursor.getString(3));
        result.setNote(cursor.getString(4));
        // 回傳結果
        return result;
    }
    public void sampleData(Context context) {
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context);
        boolean firstTime = sp.getBoolean("FIRST_TIME", true);
        if (firstTime) {
            Member m01 = new Member(0,"電話詐騙","0911720411","2012-01-01","HITO本舖");
            Member m02 = new Member(1,"簡訊詐騙","0987267397","2012-01-03","行政院五倍卷");
            insert(m01);
            insert(m02);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("FIRST_TIME", false);
            editor.commit();
        }
    }

    public boolean delete(long id) {
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    // 取得所有資料的Cursor物件
    public Cursor getAllCursor() {
        return db.query(TABLE_NAME, SHOW_COLUMNS,
                null, null, null, null, null);
    }

    // 取得參數指定日期資料的Cursor物件
    public Cursor getSearchCursor(String date) {
        // 設定條件為查詢日期時間欄位的前十個字元，就是日期的部份，
        // 格式為「substr(欄位名稱,開始,個數)='資料'」
        // 字串資料必須在前後加上「'」，數字不用
        String where = "substr(datetime, 1, 10)='" + date + "'";
        // 查詢指定日期條件的資料
        return db.query(TABLE_NAME, SHOW_COLUMNS, where,
                null, null, null, null);
    }
}
