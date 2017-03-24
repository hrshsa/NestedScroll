package com.example.pc.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.OverScroller;
import android.widget.RelativeLayout;

/**
 * Created by HuangRuiShu on 2016/8/30.
 */
public class NestedScrollRelativeLayout extends RelativeLayout implements NestedScrollingParent {

    private int viewHeight;
    private NestedScrollingParentHelper helper;
    private OverScroller mScroller;
    private View view;

    public NestedScrollRelativeLayout(Context context) {
        this(context, null);
    }

    public NestedScrollRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new NestedScrollingParentHelper(this);
        mScroller = new OverScroller(context);
    }

    /**
     * 收到子view 滑动开始的调用 startNestedScroll()，决定是否需要配合 Child
     * 一起进行处理滑动如果需要配合，还会回调 onNestedScrollAccepted()
     * @param child
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        helper.onNestedScrollAccepted(child,target,nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        helper.onStopNestedScroll(target);
    }

    /**
     * Child 滑动以后，会调用 onNestedScroll(),触发该方法
     */
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    /**
     * 每次滑动前，Child 先询问 Parent 是否需要滑动，即子view调用
     * dispatchNestedPreScroll()，Parent 可以在这个回调中“劫持”
     * 掉 Child 的滑动，也就是先于 Child 滑动。
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 && getScrollY() < viewHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop)
        {
            scrollBy(0, -dy);
            consumed[1] = dy;
        }

    }


    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        return helper.getNestedScrollAxes();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = getChildAt(0);
    }

    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0)
        {
            y = 0;
        }
        if (y > viewHeight)
        {
            y = viewHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = view.getMeasuredHeight();
    }
    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    public void fling(int velocityY)
    {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, viewHeight);
        invalidate();
    }

}
