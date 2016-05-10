package com.ez08.im.net;


import com.ez08.im.model.FriendGroupListModel;

/**
 * User: lyjq(1752095474)
 * Date: 2016-05-04
 */
public interface RestApi {
    void getFriendGroupList(Callback<FriendGroupListModel> callback);
}
