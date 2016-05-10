package com.ez08.im.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ez08.im.Config;
import com.ez08.im.model.FriendGroupItemModel;
import com.ez08.im.ui.view.BaseInfoItemView;
import com.ez08.im.ui.view.FriendGroupItemView;


/**
 * User: lyjq(1752095474)
 * Date: 2016-04-26
 */
public class FriendGroupAdapter extends BaseRecyclerAdapter<FriendGroupItemModel> {

    public FriendGroupAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case Config.BASEINFO:
                holder = new BaseInfoHolder(new BaseInfoItemView((mContext)));
                break;
            case Config.NORMAL:
                holder = new MessageHolder(new FriendGroupItemView(mContext));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FriendGroupItemModel model = getItem(position);
        switch (getItemViewType(position)){
            case Config.BASEINFO:
                BaseInfoHolder baseInfoHolder = (BaseInfoHolder)holder;
                baseInfoHolder.itemView.setData(model);
                break;
            case Config.NORMAL:
                MessageHolder messageHolder = (MessageHolder)holder;
                messageHolder.itemView.setData(model);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return  getItem(position).getType();
    }

    protected class MessageHolder extends RecyclerView.ViewHolder{
        FriendGroupItemView itemView;
        public MessageHolder(FriendGroupItemView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    protected class BaseInfoHolder extends RecyclerView.ViewHolder{
        BaseInfoItemView itemView;
        public BaseInfoHolder(BaseInfoItemView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
