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
    private EditText iu_edit,su_edit,pu_edit,du_edit,nu_edit;
    // 資料庫物件
    private Member member;
    private MemberDAO memberDAO;
    private Calendar calendar;
    private int mYear, mMonth, mDay;
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
        du_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String singleDay = "";
                        singleDay = day>0 && day<10 ? "0"+String.valueOf(day) : String.valueOf(day);
                        int month_c =month+1;
                        String singleMonth = "";
                        singleMonth = month_c>0 && month_c<10 ? "0"+String.valueOf(month_c) : String.valueOf(month_c);
                        du_edit.setText(String.valueOf(year) + "-" + String.valueOf(singleMonth) + "-" + String.valueOf(singleDay));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
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
        memberDAO.update(member);
        // 顯示修改成功
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

}
