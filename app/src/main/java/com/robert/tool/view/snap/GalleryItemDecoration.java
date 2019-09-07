package com.robert.tool.view.snap;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.robert.tool.view.snap.util.OsUtil;

/**
 * @author RyanLee
 * @date 2017/12/14
 */

public class GalleryItemDecoration extends RecyclerView.ItemDecoration {
    private final String TAG = "MainActivity_TAG";

    /**
     * 每一个页面默认页边距
     */
    public int mPageMargin = 0;
    /**
     * 中间页面左右两边的页面可见部分宽度
     */
    public int mLeftPageVisibleWidth = 50;

    public int mItemConsumeY = 0;
    public int mItemConsumeX = 0;

    private GalleryRecyclerView.OnItemClickListener onItemClickListener;

    private OnItemSizeMeasuredListener mOnItemSizeMeasuredListener;

    GalleryItemDecoration() {
    }

    @Override
    public void getItemOffsets(Rect outRect, final View view, final RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        final int position = parent.getChildAdapterPosition(view);
        final int itemCount = parent.getAdapter().getItemCount();

        parent.post(new Runnable() {
            @Override
            public void run() {
                if (((GalleryRecyclerView)parent).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    onSetHorizontalParams(parent, view, position, itemCount);
                } else {
                    onSetVerticalParams(parent, view, position, itemCount);
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    private void onSetVerticalParams(ViewGroup parent, View itemView, int position, int itemCount) {
        int itemNewWidth = parent.getWidth();
        int itemNewHeight = parent.getHeight() - OsUtil.dpToPx(4 * mPageMargin + 2 * mLeftPageVisibleWidth);

        mItemConsumeY = itemNewHeight + OsUtil.dpToPx(2 * mPageMargin);

        if (mOnItemSizeMeasuredListener != null) {
            mOnItemSizeMeasuredListener.onItemSizeMeasured(mItemConsumeY);
        }

        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
        int topMargin = position == 0 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);
        int bottomMargin = position == itemCount - 1 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);

        setLayoutParams(itemView, 0, topMargin, 0, bottomMargin, itemNewWidth, itemNewHeight);
    }

    /**
     * 设置水平滚动的参数
     *
     * @param parent    ViewGroup
     * @param itemView  View
     * @param position  int
     * @param itemCount int
     */
    private void onSetHorizontalParams(ViewGroup parent, View itemView, int position, int itemCount) {
        int itemNewWidth = itemView.getWidth();
        int itemNewHeight = itemView.getHeight();

        mItemConsumeX = itemNewWidth + OsUtil.dpToPx(2 * mPageMargin);

        if (mOnItemSizeMeasuredListener != null) {
            mOnItemSizeMeasuredListener.onItemSizeMeasured(mItemConsumeX);
        }

//        int margin = (parent.getWidth() / 5 - itemNewWidth) / 2;
        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
//        int leftMargin = position == 0 ? OsUtil.dpToPx(itemNewWidth + margin) : OsUtil.dpToPx(margin);
//        int rightMargin = position == itemCount - 1 ? OsUtil.dpToPx(itemNewWidth + margin) : OsUtil.dpToPx(margin);
//        int leftMargin = position == 0 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);
//        int rightMargin = position == itemCount - 1 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);

        setLayoutParams(itemView, 0, 0, 0, 0, itemNewWidth, itemNewHeight);
    }

//    private void onSetHorizontalParams(ViewGroup parent, View itemView, int position, int itemCount) {
//        int itemNewWidth = parent.getWidth() - OsUtil.dpToPx(4 * mPageMargin + 2 * mLeftPageVisibleWidth);
//        int itemNewHeight = parent.getHeight();
//
//        mItemConsumeX = itemNewWidth + OsUtil.dpToPx(2 * mPageMargin);
//
//        if (mOnItemSizeMeasuredListener != null) {
//            mOnItemSizeMeasuredListener.onItemSizeMeasured(mItemConsumeX);
//        }
//
//        DLog.d(TAG, "GalleryItemDecoration onSetHorizontalParams -->" + "parent.width=" + parent.getWidth() + ";mPageMargin=" + OsUtil.dpToPx(mPageMargin)
//                + ";mLeftVis=" + OsUtil.dpToPx(mLeftPageVisibleWidth) + ";itemNewWidth=" + itemNewWidth);
//
//        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
//        int leftMargin = position == 0 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);
//        int rightMargin = position == itemCount - 1 ? OsUtil.dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) : OsUtil.dpToPx(mPageMargin);
//
//        setLayoutParams(itemView, leftMargin, 0, rightMargin, 0, itemNewWidth / 5, itemNewHeight);
//    }

    /**
     * 设置参数
     *
     * @param itemView   View
     * @param left       int
     * @param top        int
     * @param right      int
     * @param bottom     int
     * @param itemWidth  int
     * @param itemHeight int
     */
    private void setLayoutParams(View itemView, int left, int top, int right, int bottom, int itemWidth, int itemHeight) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        boolean mMarginChange = false;
        boolean mWidthChange = false;
        boolean mHeightChange = false;

        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            mMarginChange = true;
        }
        if (lp.width != itemWidth) {
            lp.width = itemWidth;
            mWidthChange = true;
        }
        if (lp.height != itemHeight) {
            lp.height = itemHeight;
            mHeightChange = true;
        }

        // 因为方法会不断调用，只有在真正变化了之后才调用
        if (mWidthChange || mMarginChange || mHeightChange) {
        }
        itemView.setLayoutParams(lp);
    }

    public void setOnItemClickListener(GalleryRecyclerView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemSizeMeasuredListener(OnItemSizeMeasuredListener itemSizeMeasuredListener) {
        this.mOnItemSizeMeasuredListener = itemSizeMeasuredListener;
    }

    interface OnItemSizeMeasuredListener {
        /**
         * Item的大小测量完成
         * @param size int
         */
        void onItemSizeMeasured(int size);
    }
}
