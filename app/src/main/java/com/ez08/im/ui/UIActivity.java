package com.ez08.im.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.ez08.im.R;
import com.ez08.im.ui.fragment.NewsParentFragment;
import com.ez08.im.ui.fragment.PersonCenterFragment;
import com.ez08.im.ui.fragment.Tab1Fragment;
import com.ez08.im.util.SystemUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class UIActivity extends BaseActivity {
    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;
    @Bind(R.id.btn_go_back)
    RelativeLayout goBack;
    @Bind(R.id.toolbar_title)
    TextView title;
    @Bind(R.id.btn_go_next)
    RelativeLayout goNext;
    @Bind(R.id.image)
    ImageView image;
    public static final String tab1 = "首页";
    public static final String tab2 = "课堂";
    public static final String tab3 = "我的";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        ButterKnife.bind(this);
        setCustomTitle(tab1);
        title.setText(tab1);
        goBack.setVisibility(View.INVISIBLE);
        goNext.setVisibility(View.VISIBLE);
        image.setVisibility(View.VISIBLE);
        initView();
    }

    private void initView() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        View indicator = getIndicatoreView(tab1, R.layout.home_indicator);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(indicator), Tab1Fragment.class, null);

        indicator = getIndicatoreView(tab2, R.layout.home_indicator);
        mTabHost.addTab(mTabHost.newTabSpec("live").setIndicator(indicator), NewsParentFragment.class, null);

        indicator = getIndicatoreView(tab3, R.layout.home_indicator);
        mTabHost.addTab(mTabHost.newTabSpec("my").setIndicator(indicator), PersonCenterFragment.class, null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("home")) {
                    goNext.setVisibility(View.VISIBLE);
                    image.setVisibility(View.VISIBLE);
                    title.setText(tab1);
                } else if (tabId.equals("live")) {
                    goNext.setVisibility(View.GONE);
                    image.setVisibility(View.GONE);
                    title.setText(tab2);
                } else if (tabId.equals("my")) {
                    goNext.setVisibility(View.GONE);
                    image.setVisibility(View.GONE);
                    title.setText(tab3);
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animShow(image);
                showPopup();
            }
        });

    }
    private String[] strings = {"首页","消息","好友","健身","IT","美女","科普","科技"};
    private void showPopup() {
        ListView listView = new ListView(this);
        listView.setBackgroundResource(R.drawable.messages_left_bubble_highlighted);
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,strings));
        PopupWindow pw = new PopupWindow(listView, SystemUtils.convertDpToPixel(this,150),SystemUtils.convertDpToPixel(this,350),true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new ColorDrawable());
        pw.showAsDropDown(title);
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                animDismiss(image);
            }
        });
        pw.update();
    }

    private View getIndicatoreView(String name, int layoutId) {

        View v = getLayoutInflater().inflate(layoutId, null);
        TextView tv = (TextView) v.findViewById(R.id.tabText);
        tv.setText(name);
        ImageView iv = (ImageView) v.findViewById(R.id.tabImg);

        if (name.equals(tab1)) {
            iv.setImageResource(R.drawable.shouye2);
        } else if (name.equals(tab2)) {
            iv.setImageResource(R.drawable.ketang2);
        } else if (name.equals(tab3)) {
            iv.setImageResource(R.drawable.geren2);
        }
        return v;
    }

    @OnClick(R.id.btn_go_next)
    public void goSendCircle() {
        startActivity(new Intent(this, PublishedActivity.class));
    }


    public void animShow(ImageView iv){
        RotateAnimation ra = new RotateAnimation(0f,180f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(500);
        ra.setFillAfter(true);
        iv.startAnimation(ra);
    }

    public void animDismiss(ImageView iv){
        RotateAnimation ra = new RotateAnimation(180f,0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(500);
        ra.setFillAfter(true);
        iv.startAnimation(ra);
    }
}
