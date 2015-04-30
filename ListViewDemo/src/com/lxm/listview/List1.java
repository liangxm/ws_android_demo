package com.lxm.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lxm.listview.bean.Value;

public class List1 extends ListActivity {

	private List<Value> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		list = getList();
		setListAdapter(new SpeechListAdapter(this, list));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		((SpeechListAdapter) getListAdapter()).changeOpen(position);
	}

	private class SpeechListAdapter extends BaseAdapter {
		List<Value> listValue;
		Context context;

		public SpeechListAdapter(Context context, List<Value> list) {
			this.context = context;
			this.listValue = list;
		}

		public int getCount() {
			return listValue.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final MyView mv;
			SpeechView sv;
			if (convertView == null) {
				sv = new SpeechView(context,
						listValue.get(position).getTitle(), listValue.get(
								position).getContent(), listValue.get(position)
								.isOpen());
			} else {
				sv = (SpeechView) convertView;
				sv.setTitle(listValue.get(position).getTitle());
				sv.setDialogue(listValue.get(position).getContent());
				sv.setExpanded(listValue.get(position).isOpen());
			}
			return sv;
		}

		public void changeOpen(int position) {
			listValue.get(position).setOpen(!listValue.get(position).isOpen());
			notifyDataSetChanged();
		}
	}

	private static class MyView {
		TextView tv1;
		TextView tv2;
	}

	public List<Value> getList() {
		List<Value> list = new ArrayList<Value>();
		Value value1 = new Value();
		value1.setTitle("第一条");
		value1.setContent("第一条内容");
		value1.setOpen(false);
		Value value2 = new Value();
		value2.setTitle("第二条");
		value2.setContent("第二条内容");
		value2.setOpen(false);
		Value value3 = new Value();
		value3.setTitle("第三条");
		value3.setContent("第三条内容");
		value3.setOpen(false);
		list.add(value1);
		list.add(value2);
		list.add(value3);
		return list;
	}

	private class SpeechView extends LinearLayout {
		public SpeechView(Context context, String title, String dialogue, boolean expanded) {
			super(context);
			mTitle = new TextView(context);
			mTitle.setText(title);
			mTitle.setBackgroundColor(Color.BLUE);
			mDialogue = new TextView(context);
			mDialogue.setPadding(10, 5, 10, 5);
			mDialogue.setBackgroundColor(Color.GREEN);
			mDialogue.setText(dialogue);
			mDialogue.setVisibility(expanded ? View.VISIBLE : View.GONE);
			LinearLayout bg = new LinearLayout(context);
			bg.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			bg.setBackgroundResource(R.drawable.welcome);
			bg.addView(mTitle, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			bg.addView(mDialogue, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			bg.setOrientation(VERTICAL);
			addView(bg);
		}

		public void setTitle(String title) {
			mTitle.setText(title);
		}

		public void setDialogue(String words) {
			mDialogue.setText(words);
		}

		public void setExpanded(boolean expanded) {
			mDialogue.setVisibility(expanded ? View.VISIBLE : View.GONE);
		}

		private TextView mTitle;
		private TextView mDialogue;
	}

}
