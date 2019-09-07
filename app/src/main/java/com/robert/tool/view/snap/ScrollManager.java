package com.robert.tool.view.snap;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.robert.tool.R;
import com.robert.tool.view.snap.util.DLog;
import com.robert.tool.view.snap.util.OsUtil;

/**
 * @author RyanLee
 * @date 2017/12/8
 */

public class ScrollManager {
    private static final String TAG = "MainActivity_TAG";

    private GalleryRecyclerView mGalleryRecyclerView;
    private LinearSnapHelper mLinearSnapHelper;
    private int mPosition = 0;
    public GalleryScrollerListener mScrollerListener;

    /**
     * x方向消耗距离，使偏移量为左边距 + 左边Item的可视部分宽度
     */
    private int mConsumeX = 0;
    private int mConsumeY = 0;

    ScrollManager(GalleryRecyclerView mGalleryRecyclerView) {
        this.mGalleryRecyclerView = mGalleryRecyclerView;
    }

    /**
     * 初始化SnapHelper
     *
     * @param helper int
     */
    public void initSnapHelper(int helper) {
        switch (helper) {
            case GalleryRecyclerView.LINEAR_SNAP_HELPER:
                mLinearSnapHelper = new LinearSnapHelper();
                mLinearSnapHelper.attachToRecyclerView(mGalleryRecyclerView);
                break;
            case GalleryRecyclerView.PAGER_SNAP_HELPER:
                PagerSnapHelper mPagerSnapHelper = new PagerSnapHelper();
                mPagerSnapHelper.attachToRecyclerView(mGalleryRecyclerView);
                break;
            default:
                break;
        }
    }

    /**
     * 监听RecyclerView的滑动
     */
    public void initScrollListener() {
        mScrollerListener = new GalleryScrollerListener();
        mGalleryRecyclerView.addOnScrollListener(mScrollerListener);
    }

    public void updateConsume() {
        mConsumeX += OsUtil.dpToPx(mGalleryRecyclerView.getDecoration().mLeftPageVisibleWidth + mGalleryRecyclerView.getDecoration().mPageMargin * 2);
        mConsumeY += OsUtil.dpToPx(mGalleryRecyclerView.getDecoration().mLeftPageVisibleWidth + mGalleryRecyclerView.getDecoration().mPageMargin * 2);
        DLog.d(TAG, "ScrollManager updateConsume mConsumeX=" + mConsumeX);
    }

    class GalleryScrollerListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            DLog.d(TAG, "ScrollManager newState=" + newState);
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mGalleryRecyclerView.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                onHorizontalScroll(recyclerView, dx);
            } else {
//                onVerticalScroll(recyclerView, dy);
            }
        }
    }

    /**
     * 垂直滑动
     *
     * @param recyclerView RecyclerView
     * @param dy           int
     */
    private void onVerticalScroll(final RecyclerView recyclerView, int dy) {
        mConsumeY += dy;

        // 让RecyclerView测绘完成后再调用，避免GalleryAdapterHelper.mItemHeight的值拿不到
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int shouldConsumeY = mGalleryRecyclerView.getDecoration().mItemConsumeY;

                // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
                float offset = (float) mConsumeY / (float) shouldConsumeY;
                // 获取当前页移动的百分值
                float percent = offset - ((int) offset);

                mPosition = (int) offset;

                DLog.i(TAG, "ScrollManager offset=" + offset + "; mConsumeY=" + mConsumeY + "; shouldConsumeY=" + mPosition);

                // 设置动画变化
                mGalleryRecyclerView.getAnimManager().setAnimation(recyclerView, mPosition, percent);
            }
        });
    }

    /**
     * 水平滑动
     *
     * @param recyclerView RecyclerView
     * @param dx           int
     */
    private void onHorizontalScroll(final RecyclerView recyclerView, final int dx) {
        mConsumeX += dx;

        // 让RecyclerView测绘完成后再调用，避免GalleryAdapterHelper.mItemWidth的值拿不到
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int shouldConsumeX = mGalleryRecyclerView.getDecoration().mItemConsumeX;

                // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
                float offset = (float) mConsumeX / (float) shouldConsumeX;

                // 获取当前页移动的百分值
                float percent = offset - ((int) offset);

                mPosition = (int) offset;

                DLog.i(TAG, "ScrollManager offset=" + offset + "; percent=" + percent + "; mConsumeX=" + mConsumeX + "; shouldConsumeX=" + shouldConsumeX + "; position=" + mPosition);
                // 设置动画变化
                mGalleryRecyclerView.getAnimManager().setAnimation(recyclerView, mPosition + 2, percent);
                setTextViewColor(mPosition + 2);
            }
        });
    }

    public int getPosition() {
        return mPosition;
    }

    private void setTextViewColor(int position){
        // 中间页
        TextView mCurView = (TextView) mGalleryRecyclerView.getLayoutManager().findViewByPosition(position);
        if (null != mCurView){
            mCurView.setTextColor(mCurView.getContext().getResources().getColor(R.color.white));
        }
        // 左边页
        TextView mLeftView = (TextView) mGalleryRecyclerView.getLayoutManager().findViewByPosition(position - 1);
        if (null != mLeftView){
            mLeftView.setTextColor(mCurView.getContext().getResources().getColor(R.color.blue_dark));
        }
        // 左左边页
        TextView mLLView = (TextView) mGalleryRecyclerView.getLayoutManager().findViewByPosition(position - 2);
        if (null != mLLView){
            mLLView.setTextColor(mCurView.getContext().getResources().getColor(R.color.blue_dark));
        }
        // 右边页
        TextView mRightView = (TextView) mGalleryRecyclerView.getLayoutManager().findViewByPosition(position + 1);
        if (null != mRightView){
            mRightView.setTextColor(mCurView.getContext().getResources().getColor(R.color.blue_dark));
        }
        // 右右边页
        TextView mRRView = (TextView) mGalleryRecyclerView.getLayoutManager().findViewByPosition(position + 2);
        if (null != mRRView){
            mRRView.setTextColor(mCurView.getContext().getResources().getColor(R.color.blue_dark));
        }
    }
}
