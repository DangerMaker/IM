package com.ez08.im.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;


import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.ez08.im.Config;
import com.ez08.im.R;
import com.ez08.im.model.FriendGroupItemModel;
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
public class MyDetailFragment extends BaseLoadFragment<FriendGroupListModel> implements ScrollableHelper.ScrollableContainer{
    @Bind(R.id.scrollview)
    ScrollView scrollView;
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
            //填一个位置
            FriendGroupItemModel model = new FriendGroupItemModel();
            model.setType(Config.BASEINFO);
            pageData.getList().add(0,model);
            adapter.updateItems(pageData.getList());
        }
    }

    @Override
    public View getScrollableView() {
        return scrollView;
    }
}
