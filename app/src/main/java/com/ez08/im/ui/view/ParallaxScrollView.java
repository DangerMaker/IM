package com.ez08.im.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.ez08.im.util.SystemUtils;

/**
 * Created by shand on 2016/5/12.
 */
public class ParallaxScrollView extends ScrollView {

    public ParallaxScrollView(Context context) {
        super(context);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private OnRefreshListener listener;
    private ImageView parallaxImageView;
    private int maxHeight = SystemUtils.convertDpToPixel(getContext(),310);//图片本身的高度
    private int originalHeight;//ImageView最初的高度
    /**
     * 设置视差的ImageView
     */
    public void setParallaxImage(ImageView imageView){
        parallaxImageView = imageView;
        //获取图片的高
//        maxHeight = imageView.getDrawable().getIntrinsicHeight();

        //测量
        parallaxImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parallaxImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                originalHeight = parallaxImageView.getHeight();
            }
        });

    }

    private boolean isRefreshing = false;

//    /**
//     * 当ListView滑动到头，继续滑动则会执行该方法
//     * deltaY：继续滑动的距离 ，如果是正，表示底部到头，否则就是顶部到头
//     * maxOverScrollY：最大可以继续滚动的距离，
//     * isTouchEvent:是否是触摸滑动， true:表示是， flase:靠惯性滑动
//     *
//     */
//
//    @Override
//    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
//                                   int scrollY, int scrollRangeX, int scrollRangeY,
//                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
////		Log.e("tag", "deltaY: " + deltaY + "  maxOverScrollY:" + maxOverScrollY
////				+ " isTouchEvent" + isTouchEvent);
//
//        //如果是顶部到头，并且是手指触摸到头
//        if(deltaY<0 && isTouchEvent){
//            //1.根据deltaY让ImageVIew的height变高
//            int newHeight = parallaxImageView.getHeight() + Math.abs(deltaY)/3;
//            if(newHeight > (maxHeight / 2) && isRefreshing){
//                listener.onRefreshing();
//                isRefreshing = false;
//            }
//            //2.判断并限制IMageView的高度
//            if(newHeight > maxHeight) newHeight = maxHeight;
//
//            //3.将newHeight设置给ImageVIew
//            android.view.ViewGroup.LayoutParams params = parallaxImageView.getLayoutParams();
//            params.height = newHeight;
//            //让布局参数生效
//            parallaxImageView.setLayoutParams(params);
//
//        }
//        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
//                scrollRangeX, scrollRangeY, maxOverScrollX,maxOverScrollY,
//                isTouchEvent);
//    }

    public void setImageHeight(int height){
        //2.判断并限制IMageView的高度
        int newHeight = parallaxImageView.getHeight() + height;
        if(newHeight > maxHeight) newHeight = maxHeight;
        if(isRefreshing){
                listener.onRefreshing();
                isRefreshing = false;
            }
        if(newHeight < originalHeight) newHeight = originalHeight;
        //3.将newHeight设置给ImageVIew
        android.view.ViewGroup.LayoutParams params = parallaxImageView.getLayoutParams();
        params.height = newHeight;
        //让布局参数生效
        parallaxImageView.setLayoutParams(params);
    }

    public void reboundImage(){
        //让ImageView的高回弹的最初的120dp

        ValueAnimator animator = ValueAnimator.ofInt(parallaxImageView.getHeight(),originalHeight);
        //监听动画执行的过程
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获取动画当前的值
                int animatedValue = (Integer) animator.getAnimatedValue();
                //将animatedValue设置给ImageVIew的高度
                android.view.ViewGroup.LayoutParams params = parallaxImageView.getLayoutParams();
                params.height = animatedValue;
                parallaxImageView.setLayoutParams(params);
            }
        });
        animator.setDuration(350);
        animator.start();
    }


    public void setRefreshListener(OnRefreshListener listener){
        this.listener = listener;
    }

    public interface OnRefreshListener{
        void onRefreshing();
    }

    public void startRefresh(){
        isRefreshing = true;
    }

}
