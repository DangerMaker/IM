package com.ez08.im.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.ez08.im.R;
import com.ez08.im.model.FriendGroupItemModel;
import com.ez08.im.ui.adapter.ArticleCommentAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * User: lyjq(1752095474)
 * Date: 2016-04-25
 */
public class Tab2Fragment extends BaseFragment implements ScrollableHelper.ScrollableContainer {
    @Bind(R.id.scrollview)
    ScrollView scrollView;
    @Bind(R.id.list_comment)
    ListView mComment;
    private List<FriendGroupItemModel.EzContentDataBean.CommentArrayBean> mList;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_article_comment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArticleCommentAdapter adapter = new ArticleCommentAdapter(getActivity(),getActivity().getLayoutInflater());
        adapter.updateItems(mList);
        mComment.setAdapter(adapter);
    }

    public void initDatas(List<FriendGroupItemModel.EzContentDataBean.CommentArrayBean> list){
        this.mList = list;
    }
    @Override
    public View getScrollableView() {
        return scrollView;
    }
}
