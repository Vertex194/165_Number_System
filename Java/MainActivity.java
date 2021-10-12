package dk.yj;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void InsertPhone(View view){
        Intent intent = new Intent(MainActivity.this, InsertActivity.class);
        startActivity(intent);
        finish();
    }
    public void AllPhone(View view){
        Intent intent = new Intent(MainActivity.this, Data01Activity.class);
        startActivity(intent);
        finish();
    }
    public void FindPolice(View view){
        Intent intent = new Intent(MainActivity.this, PoliceMaps.class);
        startActivity(intent);
        finish();
    }
}