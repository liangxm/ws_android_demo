package com.example.slidecutlistview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.slidecutlistview.SlideCutListView.RemoveDirection;
import com.example.slidecutlistview.SlideCutListView.RemoveListener;

public class MainActivity extends Activity implements RemoveListener{
	private SlideCutListView slideCutListView;
	private ArrayAdapter<String> adapter;
	private List<String> dataSourceList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
    	slideCutListView = (SlideCutListView) findViewById(R.id.slideCutListView);
    	slideCutListView.setRemoveListener(this);
    	
    	for(int i=0;i<20;i++){
    		dataSourceList.add("滑动删除"+i);
    	}
    	
    	adapter = new ArrayAdapter<String>(this,R.layout.listview_item,R.id.list_item, dataSourceList);
    	slideCutListView.setAdapter(adapter);
    	
    	slideCutListView.setOnItemClickListener(new OnItemClickListener(){
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    			Toast.makeText(MainActivity.this, dataSourceList.get(position), Toast.LENGTH_SHORT).show();
    		}
    	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void removeItem(RemoveDirection direction, int position) {
		adapter.remove(adapter.getItem(position));
		switch (direction){
		case RIGHT:
			Toast.makeText(this, "向右删除"+position, Toast.LENGTH_SHORT).show();
			break;
		case LEFT:
			Toast.makeText(this, "向左删除"+position, Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
