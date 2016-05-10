package com.ez08.im.util;

import android.content.Context;
import android.content.Intent;

import com.ez08.im.model.EzAction;
import com.ez08.im.ui.NewsDetailActivity;


/**
 * Created by Administrator on 2016/4/14.
 */
public class ActivityJumper {
    //action处理跳转类
    public static void JumpToActivity(Context ctx, EzAction action) {
        Intent intent = new Intent();
        if(action.getEzActionType() == null){
            intent.putExtra("url",action.getEzTargetData().getEzDataUrl());
            intent.setClass(ctx, NewsDetailActivity.class);
        }else if(action.getEzActionType().equals("showPage")){
            intent.putExtra("url",action.getEzTargetData().getEzDataUrl());
            intent.setClass(ctx, NewsDetailActivity.class);
        }
        ctx.startActivity(intent); 
    }
}
