package com.example.pc.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import java.util.PriorityQueue;

/**
 * Created by HuangRuiShu on 2016/8/30.
 */
public class MyScrollView extends ScrollView implements NestedScrollingChild {

    private NestedScrollingChildHelper helper;
    private int lastY;
    private int curY;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        helper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        helper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return helper.isNestedScrollingEnabled();
//        return true;
    }

    /**
     * 子view需要开启整个流程（内部主要是找到合适的能接受nestedScroll
     * 的parent），通知父View
     *
     * @param axes
     * @return
     */
    @Override
    public boolean startNestedScroll(int axes) {
        return helper.startNestedScroll(axes);
//        return true;
    }

    @Override
    public void stopNestedScroll() {
        helper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return helper.hasNestedScrollingParent();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       int spec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, spec);

    }

    /**
     * 向父view汇报滚动情况，包括子view消费的部分和子view没有消费的部分。
     * 如果父view接受了它的滚动参数，进行了部分消费，则这个函数返回true，否则为false。
     *
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param offsetInWindow
     * @return
     */
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    /**
     * 在子View的onInterceptTouchEvent或者onTouch中(一般在MontionEvent.ACTION_MOVE事件里)，
     * 调用该方法通知父View滑动的距离。
     * 如果父view接受了它的滚动参数，进行了部分消费，则这个函数返回true，否则为false。
     *
     * @param dx
     * @param dy
     * @param consumed       父view消费掉的scroll长度
     * @param offsetInWindow 子View的窗体偏移量
     * @return
     */
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {

        return helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return helper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return helper.dispatchNestedPreFling(velocityX, velocityY);
    }
    private int[] mConsumed = new int[2];

    private int[] mOffset = new int[2];
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // 按下事件调用startNestedScroll
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curY = (int) event.getY();
                // 移动事件调用startNestedScroll
                dispatchNestedPreScroll(0, curY - lastY,mConsumed,mOffset);
                // 输出一下偏移
//                dispatchNestedScroll(50,50,50,50,mOffset);
                lastY = curY;
                break;
            case MotionEvent.ACTION_UP:
                // 弹起事件调用startNestedScroll
                stopNestedScroll();
                break;
            default:
                break;
        }
        return true;
    }
}
