package com.ez08.im.ui.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.ez08.im.R;

import java.util.ArrayList;
import java.util.List;

public class Emotion_ViewPager extends ViewPager {

	private List<Emotion_GridView2> views = new ArrayList<>();
	private Context context;

	public Emotion_ViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public Emotion_ViewPager(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		if (isInEditMode()) {
			return;
		}
		super.onAttachedToWindow();

		Emotion_GridView2 gv = new Emotion_GridView2(context);
		gv.setPageIndex(0);
		gv.setStartEndEmotion(R.drawable.emoji_01, R.drawable.emoji_01
				+ Emotion_GridView2.EMO_COUNT_PER_PAGE);
		views.add(gv);
		
		
		Emotion_GridView2 gv2 = new Emotion_GridView2(context);
		gv2.setPageIndex(1);
		gv2.setStartEndEmotion(R.drawable.emoji_24, R.drawable.emoji_24
				+ Emotion_GridView2.EMO_COUNT_PER_PAGE);
		views.add(gv2);

		EAdapter mAdapter = new EAdapter(views);
		setAdapter(mAdapter);

		setOnPageChangeListener(mChangeListener);

		getEGridView(position).setEditText(et);
	}

	private int position;
	private static EditText et;

	public Emotion_GridView2 getEGridView(int position) {
		if (views.size() > 0) {
			return (Emotion_GridView2) views.get(position);
		}
		return null;
	}



	public void setEditText(EditText mInputTextView) {
		et = (EditText) mInputTextView;
		if (views.size() > 0) {
			getEGridView(position).setEditText(et);
		}
	}

	private OnPageChangeListener mChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			position = arg0;
			setEditText(et);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private class EAdapter extends PagerAdapter {

		private List<Emotion_GridView2> views;

		public EAdapter(List<Emotion_GridView2> views) {
			this.views = views;
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(views.get(position));
			return views.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			super.destroyItem(container, position, object);
			container.removeView((View) object);
		}
	}

}
