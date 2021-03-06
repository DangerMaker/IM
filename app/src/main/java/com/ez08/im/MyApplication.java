package com.ez08.im;

import android.app.Application;

import com.ez08.im.auth.UserService;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MyApplication extends Application {
    public static MyApplication sInstance;
    public static String userID = "0";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //fresco init
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, new OkHttpClient())
                .build();
        Fresco.initialize(this, config);

        //初始化用户
        if (UserService.getInstance(this).hasActiveAccount()) {
            String uid = UserService.getInstance(this).getActiveAccountInfo().getUid();
            if (uid == null) {
                userID = "0";
            } else {
                userID = uid;
            }
        }
    }

    public static synchronized MyApplication getInstance() {
        return sInstance;
    }
}
