package dk.yj;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Proxy;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertActivity extends Activity {
    public static final String TABLE_NAME = "show";
    private MemberDAO memberDAO;
    EditText PhoneNumber,Skill,TimeToHappen,Notice;
    Button SubmitCheck;
    private static final String ACTIVITY_TAG="InsertAction";
    private Calendar calendar;
    private int mYear, mMonth, mDay;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_input);
        RebornViewID();
        memberDAO = new MemberDAO(this);

    }
    private void RebornViewID() {
        Skill = findViewById(R.id.skill_insert_edit);
        PhoneNumber = findViewById(R.id.phone_insert_edit);
        TimeToHappen = findViewById(R.id.date_insert_edit);
        Notice = findViewById(R.id.note_insert_edit);
        SubmitCheck = findViewById(R.id.btn_insert_submit);
    }
    public void GoHome(View view){
        Log.d(InsertActivity.ACTIVITY_TAG, "Home Touch");
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void BCancel(View view){
        Log.d(InsertActivity.ACTIVITY_TAG, "Clear Input");
        Skill.setText("");
        PhoneNumber.setText("");
        TimeToHappen.setText("");
        Notice.setText("");
    }
    public void BSubmit(View view){
        Log.d(InsertActivity.ACTIVITY_TAG, "This is Submit.");
        // 讀取使用者輸入的資料
        String skill_Text=Skill.getText().toString();
        String phone_Text=PhoneNumber.getText().toString();
        String time_Text=TimeToHappen.getText().toString();
        String notice_Text=Notice.getText().toString();
        // 市內電話或手機號碼輸入正規化判定
        Pattern pattern=Pattern.compile("09\\d{8}");
        Matcher matcher_CellPhone=pattern.matcher(phone_Text);
        Pattern pattern1=Pattern.compile("0\\d-\\d{7}");
        Matcher matcher_HomePhone=pattern1.matcher(phone_Text);
        boolean skillEmpty =skill_Text.isEmpty();
        boolean inputEmpty =phone_Text.isEmpty();
        boolean timeEmpty =time_Text.isEmpty();
        boolean noticeEmpty =notice_Text.isEmpty();
        if(inputEmpty){
            //透過Log.d檢查功能
            Log.d(InsertActivity.ACTIVITY_TAG,"Phone No Input");
            SubmitCheck.setClickable(false);
            Toast.makeText(this,"電話區塊尚未輸入，請重新輸入",Toast.LENGTH_LONG).show();
        }
        if(skillEmpty) {
            //透過Log.d檢查功能
            Log.d(InsertActivity.ACTIVITY_TAG, "Skill No Input");
            SubmitCheck.setClickable(false);
            Toast.makeText(this, "騙人方式尚未輸入，請重新輸入", Toast.LENGTH_LONG).show();
        }
        if(timeEmpty){
            //透過Log.d檢查功能
            Log.d(InsertActivity.ACTIVITY_TAG,"Time No Choose");
            SubmitCheck.setClickable(false);
            Toast.makeText(this,"時間區塊尚未輸入，請重新輸入",Toast.LENGTH_LONG).show();
        }
        if(noticeEmpty){
            //透過Log.d檢查功能
            Log.d(InsertActivity.ACTIVITY_TAG,"Notice No Input");
            SubmitCheck.setClickable(false);
            Toast.makeText(this,"時間區塊尚未輸入，請重新輸入",Toast.LENGTH_LONG).show();
        }
        if(matcher_CellPhone.matches()||matcher_HomePhone.matches()){
            Log.d(InsertActivity.ACTIVITY_TAG,"Number Have Check");
            // 建立準備新增資料的物件
            Member member = new Member();
            // 把讀取的資料設定給物件
            member.setSkill(skill_Text);
            member.setPhone(phone_Text);
            member.setDatetime(time_Text);
            member.setNote(notice_Text);
            // 新增
            memberDAO.insert(member);
            // 顯示新增成功
            Toast.makeText(this, "Insert success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Data01Activity.class);
            // 設定回傳結果
            setResult(Activity.RESULT_OK, intent);
            startActivity(intent);
            // 結束
            finish();
        }
    }
    public void datePicker(View view) {
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(InsertActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String singleDay = "";
                singleDay = day>0 && day<10 ? "0"+String.valueOf(day) : String.valueOf(day);
                int month_c =month+1;
                String singleMonth = "";
                singleMonth = month_c>0 && month_c<10 ? "0"+String.valueOf(month_c) : String.valueOf(month_c);
                TimeToHappen.setText(String.valueOf(year) + "-" + String.valueOf(singleMonth) + "-" + String.valueOf(singleDay));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}