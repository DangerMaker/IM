package com.ez08.im.api;

import retrofit.Callback;
import retrofit.http.POST;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-19
 */
public interface ListApi {
    @POST("/text9.txt")
    void getNewsList(Callback<String> callback);
}
