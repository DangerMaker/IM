package com.ez08.im.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.im.Config;
import com.ez08.im.R;
import com.ez08.im.model.CustomItemModel;
import com.ez08.im.model.EzImage;
import com.ez08.im.model.EzText;
import com.ez08.im.util.Json2EzText;
import com.ez08.im.util.ViewSettingUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/12.
 */
public class NewsGridAdapter extends MyBaseAdapter<CustomItemModel> {

    private final OnLoadMoreListener mListener;
    LayoutInflater layoutInflater;
    OnListClick onListClick;
    Gson gson;

    public NewsGridAdapter(Context context, OnListClick onListClick,OnLoadMoreListener listener) {
        super(context);
        gson = new Gson();
        layoutInflater = LayoutInflater.from(context);
        this.onListClick = onListClick;
        this.mListener = listener;
    }

    public interface OnListClick {
        void click(CustomItemModel str);
    }
    public interface OnLoadMoreListener{
        void loadMore();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CustomItemModel model = getItem(position);
        convertView = layoutInflater.inflate(R.layout.item_news_grid, null);
        RelativeLayout rootView = (RelativeLayout) convertView.findViewById(R.id.root);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListClick.click(model);
            }
        });
        Iterator<Map.Entry<String, Object>> iterator = model.getMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            View view = convertView.findViewWithTag(model.getEzContentMap().get(key));

            if (view instanceof TextView) {
                if (value.toString().startsWith("@")) {
                    EzText ezText = Json2EzText.convert(value.toString());
                    ViewSettingUtil.setTextView(mContext,(TextView)view,ezText);
                } else {
                    ((TextView) view).setText((String) value);
                }
            } else if (view instanceof SimpleDraweeView) {
                if (value instanceof String) {
                    ((SimpleDraweeView) view).setImageURI(Uri.parse(value.toString()));
                } else {
                    EzImage ezImage = gson.fromJson(value.toString(), EzImage.class);
                    ((SimpleDraweeView) view).setImageURI(Uri.parse(Config.IMAGE_URL + File.separator + ezImage.getFilename()));
                }
            }
        }

        if(position == getCount() -1){
            mListener.loadMore();
        }
        return convertView;
    }

}
