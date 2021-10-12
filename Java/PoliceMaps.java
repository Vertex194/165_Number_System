package dk.yj;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class PoliceMaps extends ListActivity {
    ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
    private SimpleAdapter adapter;
    //地點
    private static final String[] mPlaces=new String[]
            {"臺南市永康區中華路34號","臺南市永康區東橋七路320號","臺南市永康區民族路295號"};
    //地點名稱
    private static final String[] mTitles=new String[]
            {"復興派出所","大橋派出所","大灣派出所"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.qaa1);
        for(int i=0;i<mPlaces.length;i++){
            HashMap<String,String> item=new HashMap<String,String>();
            item.put("title",mTitles[i]);
            item.put("place","地點:"+mPlaces[i]);
            list.add(item);
        }
        //新增SimpleAdapter
        adapter=new SimpleAdapter(this,list,R.layout.activity_list,
                new String[]{"title","place"},
                new int[]{R.id.textView1,R.id.textView2});
        //List設定adapter
        setListAdapter(adapter);
        //啟用按鍵過濾功能(按地址可自動導航)
        getListView().setTextFilterEnabled(true);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Uri uri=Uri.parse("geo:0,0?q="+mTitles[position]);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
}
