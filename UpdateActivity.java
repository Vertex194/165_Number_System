package dk.yj;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateActivity extends Activity {
    private EditText iu_edit;
    private EditText su_edit;
    private EditText pu_edit;
    private EditText du_edit;
    private EditText nu_edit;
    // 資料庫物件
    private Member member;
    private MemberDAO memberDAO;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_page);
        // 取得資料庫物件
        memberDAO = new MemberDAO(this);
        Intent intent = getIntent();
        // 讀取修改資料的編號
        long id = intent.getLongExtra("id", -1);
        // 取得指定編號的物件
        member = memberDAO.get(id);
        processViews();
        AutoSetTime();
    }

    private void processViews() {
        iu_edit = (EditText) findViewById(R.id.id_update_edit);
        su_edit = (EditText) findViewById(R.id.skill_update_edit);
        pu_edit = (EditText) findViewById(R.id.phone_update_edit);
        du_edit = (EditText) findViewById(R.id.datetime_update_edit);
        nu_edit = (EditText) findViewById(R.id.note_update_edit);

        // 設定畫面元件顯示的資料
        iu_edit.setText(Long.toString(member.getId()));
        su_edit.setText(member.getSkill());
        pu_edit.setText(member.getPhone());
        du_edit.setText(member.getDatetime());
        nu_edit.setText(member.getNote());
    }
    private void AutoSetTime() {

    }
    public void changeOk(View view) {
        // 讀取使用者輸入的資料
        String skillValue = su_edit.getText().toString();
        String phoneValue = pu_edit.getText().toString();
        String datetimeValue = du_edit.getText().toString();
        String noteValue = nu_edit.getText().toString();
        // 把讀取的資料設定給物件
        member.setSkill(skillValue);
        member.setPhone(phoneValue);
        member.setDatetime(datetimeValue);
        member.setNote(noteValue);
        // 修改
        memberDAO.update(member);
        // 顯示修改成功
        Toast.makeText(this, "Update success!", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        // 設定回傳結果
        setResult(Activity.RESULT_OK, intent);
        // 結束
        finish();
    }
    public void changeNo(View view) {
        // 結束
        finish();
    }

    public void datePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);      //取得現在的日期年月日
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String datetime = String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(day);
                du_edit.setText(datetime);   //取得選定的日期指定給日期編輯框
            }
        }, year, month, day).show();
    }
}
