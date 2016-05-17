package com.ez08.im.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.im.R;

/**
 * Created by shand on 2016/4/18.
 */
public class PersonContentItemView extends FrameLayout {
    TextView mTitle;
    ImageView mImage;

    public PersonContentItemView(Context context) {
        this(context, null);
    }

    public PersonContentItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PersonContentItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PersonContent);
        boolean isShow = typedArray.getBoolean(R.styleable.PersonContent_isShowImage,false);
        String title = typedArray.getString(R.styleable.PersonContent_userText);
        int imageSrc = typedArray.getResourceId(R.styleable.PersonContent_imageSrc,R.drawable.avatar_default);
        View view = inflate(getContext(), R.layout.item_personcontent, this);
        mTitle = (TextView) view.findViewById(R.id.tv_text);
        mImage = (ImageView) view.findViewById(R.id.iv_image);
        initData(title,isShow,imageSrc);
    }

    private void initData(String title, boolean isShow,int id) {
        mTitle.setText(title);
        if(isShow){
            mImage.setVisibility(View.VISIBLE);
            mImage.setImageResource(id);
        }else{
            mImage.setVisibility(View.GONE);
        }
    }
}
