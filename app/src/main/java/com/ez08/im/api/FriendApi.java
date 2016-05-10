package com.ez08.im.api;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-26
 */

import com.ez08.im.model.FriendGroupListModel;

import retrofit.Callback;
import retrofit.http.GET;

public interface  FriendApi  {
    @GET("/circle_json.txt")
    void getFriendGroupList(Callback<FriendGroupListModel> callback);
}
