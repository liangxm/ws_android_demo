package com.lxm.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lxm.listview.adapter.ListAdapter;
import com.lxm.listview.R;

public class ListViewTestActivity extends Activity implements OnItemClickListener{
    private ListView mListView;
    private ListAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        mListView = (ListView)findViewById(R.id.list);
        mAdapter = new  ListAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }
     
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
        mAdapter.changeImageVisable(view, position);  
    }  
}