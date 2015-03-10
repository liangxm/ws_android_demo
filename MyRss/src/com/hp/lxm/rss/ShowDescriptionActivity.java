package com.hp.lxm.rss;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowDescriptionActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_description);
        String content =null;
        
        Intent intent = getIntent();
        if(intent!=null){
        	Bundle bundle = intent.getBundleExtra("android.intent.extra.rssItem");
        	if(bundle==null){
        		content = "������˼���������";
        	}else{
        		content = bundle.getString("title") + "nn"
						+ bundle.getString("pubdate") + "nn"
						+ bundle.getString("description").replace('n', ' ')
						+ "nn��ϸ��Ϣ�����������ַ:n" + bundle.getString("link");
        	}
        }else{
        	content = "������˼���������";
        }
        
        TextView contentText = (TextView) this.findViewById(R.id.content);
        contentText.setText(content);
        
        Button backButton = (Button) this.findViewById(R.id.back);
        backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}