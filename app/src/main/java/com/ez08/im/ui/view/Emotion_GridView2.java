package com.ez08.im.ui.view;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.ez08.im.R;
import com.ez08.im.util.EditTextUtils;
import com.ez08.im.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Emotion_GridView2 extends GridView {
	Context context;
	EditText et;
	List<HashMap<String, Integer>> emothion_items = new ArrayList<>();

	public Emotion_GridView2(Context context) {
		super(context);
		this.context = context;
	}

	public Emotion_GridView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void setEditText(EditText mInputTextView) {
		this.et = mInputTextView;
	}

	private int startId;

	public void setStartEndEmotion(int startId, int endId) {
		this.startId = startId;
		initEmoGV(startId, endId);
	}

	public static final int EMO_COUNT_PER_PAGE = 23;

	@Override
	protected void onAttachedToWindow() {
		if (isInEditMode())
			return;

		setNumColumns(6);

		super.onAttachedToWindow();
	}

	private int mPageIndex = 0;

	public void setPageIndex(int pageIndex) {
		mPageIndex = pageIndex;
	}

	private int getPageIndex() {
		return mPageIndex;
	}

	private void initEmoGV(int startId, int endId) {
		for (int i = 0; i < EMO_COUNT_PER_PAGE; i++) {

			if (startId + i > R.drawable.emoji_01 + 40) {
				continue;
			}
			HashMap<String, Integer> map = new HashMap<>();

			map.put("emotion", startId + i);
			emothion_items.add(map);

			EditTextUtils.emo_map.put("[" + EditTextUtils.EMOS[i + mPageIndex * EMO_COUNT_PER_PAGE]	+ "]", startId + i);

		}

		MyGridViewAdapter mgva = new MyGridViewAdapter(emothion_items);
		this.setAdapter(mgva);
		this.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				int emo_id;
				if (position == EMO_COUNT_PER_PAGE) {
					emo_id = -1;
				} else {
					if (position > emothion_items.size()) {
						return;
					}
					emo_id = emothion_items.get(position).get("emotion");
				}

				System.out.println("///position:" + emo_id);
				if (emo_id != 0) {
					addEmotion(position + mPageIndex * EMO_COUNT_PER_PAGE,
							emo_id);
				}

			}
		});
	}

	/**
	 * @param index
	 * @param id
	 *            -1 表示删除动作
	 */
	public void addEmotion(int index, int id) {
		if (et.getText() == null) {
			et.setText(" ");
		}
		String newtextString = "";
		if (id == -1) {
			String str = et.getText().toString();
			if (TextUtils.isEmpty(str)) {
				return;
			}
			// 内容长度
			int length = str.length();
			// 最后一个"]"标记的位置
			int last1 = str.lastIndexOf("]");
			// 最后一个"["标记的位置
			int last2 = str.lastIndexOf("[");
			// 最后一个字符是表情
			if (last1 == str.length() - 1) {
				// 一个不严谨的判断，表情的格式是"[表情]" 或者
				// "[表]"，判断两个括号的index的差，如果差值等于2或者3，就当作表情处理

				if (last1 - last2 == 3 || last1 - last2 == 2) {
					// 删除代表表情的字符串
					newtextString = str.substring(0, last2 -1);
				} else {// 不是表情，删除最后一个字
					newtextString = str.substring(0, length - 1);
				}
			} else {// 最后一个字符是表情以外的
				newtextString = str.substring(0, length - 1);
			}

		} else {
			newtextString = et.getText().toString() + "["
					+ EditTextUtils.EMOS[index] + "]";
		}

		SpannableStringBuilder ssb = EditTextUtils.getEmotion(context,
				newtextString);
		et.setText(ssb);
		et.setSelection(ssb.length());

	}

	class MyGridViewAdapter extends BaseAdapter {

		List<HashMap<String, Integer>> items;

		public MyGridViewAdapter(List<HashMap<String, Integer>> items) {
			this.items = items;
		}

		@Override
		public int getCount() {
			return EMO_COUNT_PER_PAGE + 1;
		}

		@Override
		public Object getItem(int position) {
			if (position > EMO_COUNT_PER_PAGE - 2) {
				return null;
			}
			return items.get(position);
		}

		@Override
		public View getView(final int position, View convertView,
							ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.im_emotion_layout_item, null);
				ImageView iv = (ImageView) convertView.findViewById(R.id.emo);
				viewHolder = new ViewHolder();
				viewHolder.image = iv;
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.image.setVisibility(View.VISIBLE);
			
			if (position == EMO_COUNT_PER_PAGE) {
				viewHolder.image.setImageResource(R.drawable.im_selector_del);
			} else {
				if (position < items.size()) {
					viewHolder.image.setImageResource(items.get(position).get(
							"emotion"));
				}else {
					viewHolder.image.setImageResource(items.get(0).get(
							"emotion"));
					viewHolder.image.setVisibility(View.INVISIBLE);
				}
			}

			return convertView;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			public TextView title;
			public ImageView image;
		}
	}
}
