package com.robert.tool.view.snap;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.robert.tool.R;

/**
 *
 * @author RyanLee
 * @date 2017/12/12
 */

public class AnimManager {

    public static final int ANIM_BOTTOM_TO_TOP = 0;
    public static final int ANIM_TOP_TO_BOTTOM = 1;

    /**
     * 动画类型
     */
    private int mAnimType = ANIM_BOTTOM_TO_TOP;
    /**
     * 变化因子
     */
    private float mAnimFactor = 0.3f;

    public void setAnimation(RecyclerView recyclerView, int position, float percent) {
        switch (mAnimType) {
            case ANIM_BOTTOM_TO_TOP:
                setBottomToTopAnim(recyclerView, position, percent);
                break;
            case ANIM_TOP_TO_BOTTOM:
                setTopToBottomAnim(recyclerView, position, percent);
                break;
            default:
                setBottomToTopAnim(recyclerView, position, percent);
                break;
        }
    }

    /**
     * 从下到上的动画效果
     *
     * @param recyclerView RecyclerView
     * @param position int
     * @param percent float
     */
    private void setBottomToTopAnim(RecyclerView recyclerView, int position, float percent) {
        // 中间页
        View mCurView = recyclerView.getLayoutManager().findViewByPosition(position);
        // 左边页
        View mLeftView = recyclerView.getLayoutManager().findViewByPosition(position - 1);
        // 左左边页
        View mLLView = recyclerView.getLayoutManager().findViewByPosition(position - 2);
        // 左左边页
        View mLLLView = recyclerView.getLayoutManager().findViewByPosition(position - 3);
        // 右边页
        View mRightView = recyclerView.getLayoutManager().findViewByPosition(position + 1);
        // 右右边页
        View mRRView = recyclerView.getLayoutManager().findViewByPosition(position + 2);
        // 右右边页
        View mRRRView = recyclerView.getLayoutManager().findViewByPosition(position + 3);

        if (mCurView != null) {
            mCurView.setScaleX(1 - percent * mAnimFactor);
            mCurView.setScaleY(1 - percent * mAnimFactor);
            mCurView.setBackgroundResource(R.mipmap.icon_main_fragment_snap_circle);
        }
        if (mLeftView != null) {
            mLeftView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
            mLeftView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
            mLeftView.setBackgroundResource(R.mipmap.icon_main_fragment_snap_circle2);
        }
        if (mLLView != null) {
            mLLView.setScaleX((1 - mAnimFactor * 3/2) + percent * mAnimFactor);
            mLLView.setScaleY((1 - mAnimFactor * 3/2) + percent * mAnimFactor);
            mLLView.setBackgroundResource(R.mipmap.icon_main_fragment_snap_circle3);
        }
        if (mLLLView != null) {
            mLLLView.setScaleX((1 - mAnimFactor * 2) + percent * mAnimFactor);
            mLLLView.setScaleY((1 - mAnimFactor * 2) + percent * mAnimFactor);
            mLLLView.setBackgroundResource(R.mipmap.icon_main_fragment_snap_circle3);
        }
        if (mRightView != null) {
            mRightView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
            mRightView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
            mRightView.setBackgroundResource(R.mipmap.icon_main_fragment_snap_circle2);
        }
        if (mRRView != null) {
            mRRView.setScaleX((1 - mAnimFactor * 3/2) + percent * mAnimFactor);
            mRRView.setScaleY((1 - mAnimFactor * 3/2) + percent * mAnimFactor);
            mRRView.setBackgroundResource(R.mipmap.icon_main_fragment_snap_circle3);
        }
        if (mRRRView != null) {
            mRRRView.setScaleX((1 - mAnimFactor * 2) + percent * mAnimFactor);
            mRRRView.setScaleY((1 - mAnimFactor * 2) + percent * mAnimFactor);
            mRRRView.setBackgroundResource(R.mipmap.icon_main_fragment_snap_circle3);
        }
    }


    /***
     * 从上到下的效果
     * @param recyclerView RecyclerView
     * @param position int
     * @param percent int
     */
    private void setTopToBottomAnim(RecyclerView recyclerView, int position, float percent) {
//        // 中间页
//        View mCurView = recyclerView.getLayoutManager().findViewByPosition(position);
//        // 左边页
//        View mLeftView = recyclerView.getLayoutManager().findViewByPosition(position - 1);
//        // 左左边页
//        View mLLView = recyclerView.getLayoutManager().findViewByPosition(position - 2);
//        // 左左边页
//        View mLLLView = recyclerView.getLayoutManager().findViewByPosition(position - 3);
//        // 右边页
//        View mRightView = recyclerView.getLayoutManager().findViewByPosition(position + 1);
//        // 右右边页
//        View mRRView = recyclerView.getLayoutManager().findViewByPosition(position + 2);
//        // 右右边页
//        View mRRRView = recyclerView.getLayoutManager().findViewByPosition(position + 3);
//
//        if (mCurView != null) {
//            mCurView.setScaleX(1 - percent * mAnimFactor);
//            mCurView.setScaleY(1 - percent * mAnimFactor);
//            mCurView.setBackgroundResource(R.mipmap.icon_circle);
//        }
//        if (mLeftView != null) {
//            mLeftView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
//            mLeftView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
//            mLeftView.setBackgroundResource(R.mipmap.icon_circle2);
//        }
//        if (mLLView != null) {
//            mLLView.setScaleX((1 - mAnimFactor * 2) + percent * mAnimFactor);
//            mLLView.setScaleY((1 - mAnimFactor * 2) + percent * mAnimFactor);
//            mLLView.setBackgroundResource(R.mipmap.icon_circle3);
//        }
//        if (mLLLView != null) {
//            mLLLView.setScaleX((1 - mAnimFactor * 3) + percent * mAnimFactor);
//            mLLLView.setScaleY((1 - mAnimFactor * 3) + percent * mAnimFactor);
//            mLLLView.setBackgroundResource(R.mipmap.icon_circle2);
//        }
//        if (mRightView != null) {
//            mRightView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
//            mRightView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
//            mRightView.setBackgroundResource(R.mipmap.icon_circle2);
//        }
//        if (mRRView != null) {
//            mRRView.setScaleX((1 - mAnimFactor * 2) + percent * mAnimFactor);
//            mRRView.setScaleY((1 - mAnimFactor * 2) + percent * mAnimFactor);
//            mRRView.setBackgroundResource(R.mipmap.icon_circle3);
//        }
//        if (mRRRView != null) {
//            mRRRView.setScaleX((1 - mAnimFactor * 3) + percent * mAnimFactor);
//            mRRRView.setScaleY((1 - mAnimFactor * 3) + percent * mAnimFactor);
//            mRRRView.setBackgroundResource(R.mipmap.icon_circle2);
//        }

    }

    public void setAnimFactor(float mAnimFactor) {
        this.mAnimFactor = mAnimFactor;
    }

    public void setAnimType(int mAnimType) {
        this.mAnimType = mAnimType;
    }
}
