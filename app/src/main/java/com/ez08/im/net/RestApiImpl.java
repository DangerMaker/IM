package com.ez08.im.net;


import com.ez08.im.model.FriendGroupListModel;
import com.ez08.im.util.RestAdapterUtils;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * User: lyjq(1752095474)
 * Date: 2016-05-09
 */
public class RestApiImpl implements RestApi {
    @Override
    public void getFriendGroupList(final Callback<FriendGroupListModel> callback) {
        RestAdapterUtils.getFriendApi().getFriendGroupList(new retrofit.Callback<FriendGroupListModel>() {
            @Override
            public void success(FriendGroupListModel friendGroupListModel, Response response) {
                callback.success(friendGroupListModel,response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.failure(new Error());
            }
        });
    }
}
