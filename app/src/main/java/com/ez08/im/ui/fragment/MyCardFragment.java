package com.ez08.im.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.ez08.im.R;
import com.ez08.im.model.FriendGroupListModel;
import com.ez08.im.ui.adapter.FriendGroupAdapter;
import com.ez08.im.ui.view.DividerLine;
import com.ez08.im.util.RestAdapterUtils;
import com.ez08.im.util.SystemUtils;

import butterknife.Bind;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class MyCardFragment extends BaseLoadFragment<FriendGroupListModel> implements ScrollableHelper.ScrollableContainer{
    @Bind(R.id.list)
    RecyclerView mRecyclerView;

    FriendGroupAdapter adapter;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_friend_baseinfo;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(adapter == null ? adapter = new FriendGroupAdapter(getActivity()) : adapter);
            mRecyclerView.setItemViewCacheSize(5);
            DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
            dividerLine.setSize(SystemUtils.convertDpToPixel(getActivity(),1));
            dividerLine.setColor(getResources().getColor(R.color.home_backgroundcolor));
            mRecyclerView.addItemDecoration(dividerLine);
        }

        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
    }

    @Override
    protected void onLoadData() {
        showLoad();
        RestAdapterUtils.getFriendApi().getFriendGroupList(this);
    }

    @Override
    protected void onInitLoadData(FriendGroupListModel pageData) {
        hideEmptyView();
        if(pageData != null){
            adapter.updateItems(pageData.getList());
        }
    }

    @Override
    public View getScrollableView() {
        return mRecyclerView;
    }
}
