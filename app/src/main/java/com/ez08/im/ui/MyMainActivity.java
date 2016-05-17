package com.ez08.im.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.ez08.im.R;
import com.ez08.im.ui.fragment.BaseFragment;
import com.ez08.im.ui.fragment.MyCardFragment;
import com.ez08.im.ui.fragment.MyDetailFragment;
import com.ez08.im.ui.view.ParallaxScrollView;
import com.ez08.im.util.SystemUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-27
 */
public class MyMainActivity extends BackBaseActivity {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.main_viewpager)
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    @Bind(R.id.parallaxscrollview)
    ParallaxScrollView parallaxScrollView;
    @Bind(R.id.scrollableLayout)
    ScrollableLayout mScrollLayout;


    ArrayList<BaseFragment> fragmentList = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SystemUtils.show_msg(MyMainActivity.this, "刷新");
            parallaxScrollView.startRefresh();
            bar.setVisibility(View.GONE);
            imgMore.setVisibility(View.VISIBLE);
        }
    };
    @Bind(R.id.btn_go_back)
    RelativeLayout btnGoBack;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.btn_go_next)
    RelativeLayout btnGoNext;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private ProgressBar bar;
    private ImageView imgMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymain);
        ButterKnife.bind(this);
        setCustomTitle("个人中心");
        setBarBackColor(Color.TRANSPARENT);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        fragmentList.add(new MyDetailFragment());
        fragmentList.add(new MyCardFragment());


        View view = getLayoutInflater().inflate(R.layout.my_head, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.backimage);
        bar = (ProgressBar) view.findViewById(R.id.loding_bar);
        imgMore = (ImageView) view.findViewById(R.id.more);


        parallaxScrollView.addView(view);
        parallaxScrollView.setParallaxImage(imageView);

        mScrollLayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
//                ViewHelper.setTranslationY(parallaxScrollView, (float) (currentY * 0.5));
                //根据滑动状态修复tabLayout的位置
                if (currentY >= maxY - toobar.getHeight()) {
                    setBarBackColor(Color.WHITE);
                    setCustomTitleColor(Color.BLACK);
                } else {
                    setBarBackColor(Color.TRANSPARENT);
                    setCustomTitleColor(Color.TRANSPARENT);
                }

                if (currentY >=  maxY - toobar.getHeight()) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabLayout.getLayoutParams();
                    params.topMargin = currentY -  (maxY - toobar.getHeight());
                    tabLayout.setLayoutParams(params);
                }else{
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabLayout.getLayoutParams();
                    params.topMargin = 0;
                    tabLayout.setLayoutParams(params);
                }
            }

            @Override
            public void isUp() {
                parallaxScrollView.reboundImage();
            }

            @Override
            public void getTouchY(int touchY) {
                parallaxScrollView.setImageHeight(touchY / 30);
            }
        });
        parallaxScrollView.startRefresh();
        parallaxScrollView.setRefreshListener(new ParallaxScrollView.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                handler.sendEmptyMessageDelayed(0, 2000);
                bar.setVisibility(View.VISIBLE);
                imgMore.setVisibility(View.GONE);
            }
        });


        mScrollLayout.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragmentList.get(0));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mScrollLayout.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragmentList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] title = {"主页", "帖子"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
