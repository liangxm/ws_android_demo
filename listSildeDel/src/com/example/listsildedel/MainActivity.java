package com.example.listsildedel;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView mListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) this.findViewById(R.id.list);
        List<String> list = new ArrayList<String>();
        for(int i=0;i<10;i++){
        	list.add("选项"+i);
        }
        //实例化自定义内容适配类
        MyAdapter adapter = new MyAdapter(this,list);
        //为listView设置适配
        mListView.setAdapter(adapter);
    }


    
}
