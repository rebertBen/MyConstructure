package com.robert.tool.view.snap;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.robert.tool.R;


/**
 * @author RyanLee
 * @date 2017/12/8
 */

public class GalleryRecyclerView extends RecyclerView implements View.OnTouchListener, GalleryItemDecoration.OnItemSizeMeasuredListener {

    private static final String TAG = "MainActivity_TAG";

    public static final int LINEAR_SNAP_HELPER = 0;
    public static final int PAGER_SNAP_HELPER = 1;
    /**
     * 滑动速度
     */
    private int mFlingSpeed = 1000;
    /**
     * 是否自动播放
     */
    private boolean mAutoPlay = false;
    /**
     * 自动播放间隔时间
     */
    private int mInterval = 1000;

    private int mInitPos = -1;


    private AnimManager mAnimManager;
    public ScrollManager mScrollManager;
    private GalleryItemDecoration mDecoration;

    public GalleryItemDecoration getDecoration() {
        return mDecoration;
    }

    public ScrollManager getmScrollManager() {
        return mScrollManager;
    }

    public void setmScrollManager(ScrollManager mScrollManager) {
        this.mScrollManager = mScrollManager;
    }

    public AnimManager getAnimManager() {
        return mAnimManager;
    }

    public GalleryRecyclerView(Context context) {
        this(context, null);
    }

    public GalleryRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GalleryRecyclerView);
        int helper = ta.getInteger(R.styleable.GalleryRecyclerView_helper, LINEAR_SNAP_HELPER);
        ta.recycle();

        mAnimManager = new AnimManager();
        attachDecoration();
        attachToRecyclerHelper(helper);

        //设置触碰监听
        setOnTouchListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    private void attachDecoration() {
        mDecoration = new GalleryItemDecoration();
        mDecoration.setOnItemSizeMeasuredListener(this);
        addItemDecoration(mDecoration);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityX = balanceVelocity(velocityX);
        velocityY = balanceVelocity(velocityY);
        return super.fling(velocityX, velocityY);
    }

    /**
     * 返回滑动速度值
     *
     * @param velocity int
     * @return int
     */
    private int balanceVelocity(int velocity) {
        if (velocity > 0) {
            return Math.min(velocity, mFlingSpeed);
        } else {
            return Math.max(velocity, -mFlingSpeed);
        }
    }

    /**
     * 连接RecyclerHelper
     *
     * @param helper int
     */
    private void attachToRecyclerHelper(int helper) {
        mScrollManager = new ScrollManager(this);
        mScrollManager.initScrollListener();
        mScrollManager.initSnapHelper(helper);
    }

    /**
     * 设置页面参数，单位dp
     *
     * @param pageMargin           默认：0dp
     * @param leftPageVisibleWidth 默认：50dp
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView initPageParams(int pageMargin, int leftPageVisibleWidth) {
        mDecoration.mPageMargin = pageMargin;
        mDecoration.mLeftPageVisibleWidth = leftPageVisibleWidth;
        return this;
    }

    /**
     * 设置滑动速度（像素/s）
     *
     * @param speed int
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView initFlingSpeed(@IntRange(from = 0) int speed) {
        this.mFlingSpeed = speed;
        return this;
    }

    /**
     * 设置动画因子
     *
     * @param factor float
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView setAnimFactor(@FloatRange(from = 0f) float factor) {
        mAnimManager.setAnimFactor(factor);
        return this;
    }

    /**
     * 设置动画类型
     *
     * @param type int
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView setAnimType(int type) {
        mAnimManager.setAnimType(type);
        return this;
    }

    /**
     * 设置点击事件
     *
     * @param mListener OnItemClickListener
     */
    public GalleryRecyclerView setOnItemClickListener(OnItemClickListener mListener) {
        if (mDecoration != null) {
            mDecoration.setOnItemClickListener(mListener);
        }
        return this;
    }

    /**
     * 是否自动滚动
     *
     * @param auto boolean
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView autoPlay(boolean auto) {
        this.mAutoPlay = auto;
        return this;
    }

    /**
     * 装载
     *
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView setUp() {
        if (getAdapter().getItemCount() <= 0) {
            return this;
        }

        smoothScrollToPosition(0);
        mScrollManager.updateConsume();

        return this;
    }


    public int getOrientation() {

        if (getLayoutManager() instanceof LinearLayoutManager) {
            if (getLayoutManager() instanceof GridLayoutManager) {
                throw new RuntimeException("请设置LayoutManager为LinearLayoutManager");
            } else {
                return ((LinearLayoutManager) getLayoutManager()).getOrientation();
            }
        } else {
            throw new RuntimeException("请设置LayoutManager为LinearLayoutManager");
        }
    }

    public int getScrolledPosition() {
        if (mScrollManager == null) {
            return 0;
        } else {
            return mScrollManager.getPosition();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);

        // 如果是横竖屏切换（Fragment销毁），不应该走smoothScrollToPosition(0)，因为这个方法会导致ScrollManager的onHorizontalScroll不断执行，而ScrollManager.mConsumeX已经重置，会导致这个值紊乱
        // 而如果走scrollToPosition(0)方法，则不会导致ScrollManager的onHorizontalScroll执行，所以ScrollManager.mConsumeX这个值不会错误
        scrollToPosition(0);
        // 但是因为不走ScrollManager的onHorizontalScroll，所以不会执行切换动画，所以就调用smoothScrollBy(int dx, int dy)，让item轻微滑动，触发动画
        smoothScrollBy(10, 0);
        smoothScrollBy(0, 0);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 播放间隔时间 ms
     *
     * @param interval int
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView intervalTime(@IntRange(from = 10) int interval) {
        this.mInterval = interval;
        return this;
    }

    /**
     * 开始处于的位置
     *
     * @param i int
     * @return GalleryRecyclerView
     */
    public GalleryRecyclerView initPosition(@IntRange(from = 0) int i) {
        if (i >= getAdapter().getItemCount()) {
            i = getAdapter().getItemCount() - 1;
        } else if (i < 0) {
            i = 0;
        }
        mInitPos = i;
        return this;
    }

    @Override
    public void onItemSizeMeasured(int size) {
        if (mInitPos < 0) {
            return;
        }
        if (mInitPos == 0) {
            scrollToPosition(0);
        } else {
            if (getOrientation() == LinearLayoutManager.HORIZONTAL) {
                smoothScrollBy(mInitPos * size, 0);
            } else {
                smoothScrollBy(0, mInitPos * size);
            }
        }
        mInitPos = -1;
    }

    public interface OnItemClickListener {
        /**
         * 点击事件
         *
         * @param view     View
         * @param position int
         */
        void onItemClick(View view, int position);
    }
}
