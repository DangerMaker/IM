package com.ez08.im.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.ez08.im.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * BaseActivity是Activity的基类，添加些通用功能，绑定组件，还可以加友盟统计等
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toobar;

    @Nullable
    @Bind(R.id.toolbar_title)
    TextView customTitle;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setupToolbar();
    }

    protected void setupToolbar() {
        if (toobar != null) {
            setSupportActionBar(toobar);
        }
    }

    public void setCustomTitle(String title) {
        if (customTitle != null) {
            customTitle.setText(title);
        }
    }

    public void setCustomTitleColor(int color){
        if(customTitle != null){
            customTitle.setTextColor(color);
        }
    }

    public void setBarBackColor(int color){
        if(toobar != null){
            toobar.setBackgroundColor(color);
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
