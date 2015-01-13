package org.reno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private static final int READ = 0;
	private static final int WRITE = 1;

	private Button button1,button2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        button1 = (Button)findViewById(R.id.btn_read);
        button1.setOnClickListener(new ButtonListener());
        
        button2 = (Button)findViewById(R.id.btn_write);
        button2.setOnClickListener(new ButtonListener());
    }
    
    class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
	    	 
        	Intent  intent = new Intent();
        	intent.setAction("org.reno.second");
        	switch(v.getId()){
	    	 	case R.id.btn_read:
	    	 		intent.putExtra("org.reno.cardType", READ);// 第一个参数指定name，android规范是以包名+变量名来命名，后面是各种类型的数据类型
	    	 		break;
	    	 	case R.id.btn_write:
	    	 		intent.putExtra("org.reno.cardType", WRITE);// 第一个参数指定name，android规范是以包名+变量名来命名，后面是各种类型的数据类型
	    	 		break;
	         }
             //Bundle bundle = new Bundle();//　　Bundle的底层是一个HashMap<String, Object
             //bundle.putString("hello", "world");
             //intent.putExtra("bundle", bundle);
             startActivity(intent);
        }
    }
}
