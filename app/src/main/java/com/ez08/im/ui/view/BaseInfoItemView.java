package com.ez08.im.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


import com.ez08.im.R;
import com.ez08.im.model.FriendGroupItemModel;

import butterknife.ButterKnife;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-28
 */
public class BaseInfoItemView extends RelativeLayout {

    public BaseInfoItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_my_baseinfo, this);
        ButterKnife.bind(this, view);
    }

    public void setData(FriendGroupItemModel data) {

    }
}
