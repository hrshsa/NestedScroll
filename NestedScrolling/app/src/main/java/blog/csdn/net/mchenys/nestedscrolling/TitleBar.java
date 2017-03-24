package blog.csdn.net.mchenys.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by HuangRuiShu on 2016/10/9.
 */
public class TitleBar extends RelativeLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper helper;
    private int maxheight;
    private int minheight;
    private int height;

    public TitleBar(Context context) {
        super(context);
        init();

    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        helper = new NestedScrollingParentHelper(this);
        minheight = 0;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getChildAt(0).getMeasuredHeight();
        maxheight = height;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        helper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
        helper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    /**
     * 嵌套滑动子View滑动之前的准备工作
     *
     * @param target   实现嵌套滑动的子View
     * @param dx       水平方向上嵌套滑动的子View滑动的总距离
     * @param dy       竖直方向上嵌套滑动的子View滑动的总距离
     * @param consumed consumed[0]水平方向与consumed[1]竖直方向上父View消耗(滑动)的距离
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        Log.e("dy :", dy + " scrollY" + getScrollY());
        consumed[0] = 0;
        if (dy > 0) {
            //往上推
            if (getScrollY() + dy <= height) {
                scrollBy(0, dy);
                consumed[1] = dy;
            } else if (getScrollY() + dy > height) {
                consumed[1] = height - getScrollY();
                scrollTo(0, height);
            } else {
                consumed[1] = 0;
            }
        } else if (dy < 0) {
            //往下拉
            if (getScrollY() + dy >= 0) {
                scrollBy(0, dy);
                consumed[1] = dy;
            } else if (getScrollY() + dy < 0) {
                consumed[1] = -getScrollY();
                scrollTo(0, 0);
            } else {
                consumed[1] = 0;
            }
        }
        Log.e("consumed", consumed[1] + "");
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return helper.getNestedScrollAxes();
    }
}
