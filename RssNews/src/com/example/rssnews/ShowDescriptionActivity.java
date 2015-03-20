package com.example.rssnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rssnews.util.downloadImageTask;

public class ShowDescriptionActivity extends Activity {
	
	private ImageView imageView;
	private downloadImageTask task;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_description);
        TextView title = (TextView) this.findViewById(R.id.title);
        TextView description = (TextView) this.findViewById(R.id.description);
        TextView link = (TextView) this.findViewById(R.id.link);
        TextView pubdate = (TextView) this.findViewById(R.id.pubdate);
        
        Intent intent = getIntent();
        Bundle bundle = null;
        if(intent!=null){
        	bundle = intent.getBundleExtra("android.intent.extra.rssItem");
        	if(bundle==null){
        		title.setText("������˼���������");
        	}else{
        		title.setText(bundle.getString("title"));
        		description.setText(Html.fromHtml(bundle.getString("description")));
        		link.setText(Html.fromHtml("<a href='"+bundle.getString("link")+"'>"+bundle.getString("link")+"</a>"));
        		pubdate.setText(bundle.getString("pubdate"));
        	}
        }else{
        	title.setText("������˼���������");
        }
        
        imageView = (ImageView) this.findViewById(R.id.image);
        String url = bundle.getString("image");
        task = new downloadImageTask();
        task.execute(url,imageView);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
