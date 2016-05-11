package com.ez08.im.ui.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ez08.im.R;


/**
 * Created by shand on 2016/5/10.
 */
public class CommentPopupWindow extends PopupWindow implements View.OnClickListener {


    private final Context context;
    private final EditText comment;
    private final Emotion_ViewPager mAddEmojPage;

    public CommentPopupWindow(Context mContext, View parent) {
        this.context = mContext;
        View view = View.inflate(mContext, R.layout.item_send_comment_popup, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.push_bottom_in_2));
        setWidth(LinearLayout.LayoutParams.FILL_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        //设置能否获取焦点
        setFocusable(true);
        //设置点击外部区域能否关闭popup
        setOutsideTouchable(true);
        setContentView(view);
        //设置软键盘不会挡住popupwindown
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置展示位置
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        ImageView addFriend = (ImageView) view.findViewById(R.id.add_friend);
        ImageView addEmoj = (ImageView) view.findViewById(R.id.add_emoj);
        ImageView addPicature = (ImageView) view.findViewById(R.id.add_picature);
        comment = (EditText) view.findViewById(R.id.comment);
        final TextView send = (TextView) view.findViewById(R.id.send);
        mAddEmojPage = (Emotion_ViewPager) view.findViewById(R.id.add_emoj_pager);
        mAddEmojPage.setEditText(comment);
        comment.setFocusableInTouchMode(true);
        comment.requestFocus();


        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    send.setTextColor(context.getResources().getColor(R.color.colorTextGray));
                    send.setBackgroundColor(context.getResources().getColor(R.color.gray_text));
                    if (send.isClickable()) send.setClickable(false);
                } else {
                    send.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    send.setBackgroundColor(context.getResources().getColor(R.color.orange));
                    if (!send.isClickable()) send.setClickable(true);
                }
            }
        });
        addFriend.setOnClickListener(this);
        addEmoj.setOnClickListener(this);
        addPicature.setOnClickListener(this);
        send.setOnClickListener(this);
        comment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_friend:

                break;

            case R.id.add_emoj:
                hideInput();
                if (mAddEmojPage.getVisibility() == View.GONE) {
                    mAddEmojPage.setVisibility(View.VISIBLE);
                } else if (mAddEmojPage.getVisibility() == View.VISIBLE) {
                    mAddEmojPage.setVisibility(View.GONE);
                }
                break;

            case R.id.add_picature:

                break;


            case R.id.send:

                break;

            case R.id.comment:
                mAddEmojPage.setVisibility(View.GONE);
                break;
        }
    }

    //强制隐藏键盘
    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(comment.getWindowToken(), 0);
    }

}