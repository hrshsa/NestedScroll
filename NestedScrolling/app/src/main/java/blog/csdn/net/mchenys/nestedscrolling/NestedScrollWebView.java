package blog.csdn.net.mchenys.nestedscrolling;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by HuangRuiShu on 2017/3/23.
 */

public class NestedScrollWebView extends WebView implements NestedScrollingChild{
    private NestedScrollingChildHelper helper;
    private int mLastMotionY;

    public NestedScrollWebView(Context context) {
        super(context);
        init(context);
    }

    public NestedScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NestedScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        helper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }
    private float mLastY;
    private float curY;


    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        helper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            Log.e("Build","Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP");
        boolean result;
        MotionEvent newEvent;

        final int action = MotionEventCompat.getActionMasked(event);

        int y = (int) event.getY();
        int newY = y;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = mLastMotionY - y;
                mLastMotionY = y;
                if (dispatchNestedPreScroll(0, deltaY, consumed, offset)) {
                    mLastMotionY -= offset[1];
                }
                newY = mLastMotionY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                stopNestedScroll();
                break;
        }
        newEvent = MotionEvent.obtain(event.getDownTime(), event.getEventTime(), action, event.getX(), newY, event.getMetaState());
        result = super.onTouchEvent(newEvent);
        newEvent.recycle();
        return result;
    }

    private int[] offset = new int[2], consumed = new int[2];
    @Override
    public boolean isNestedScrollingEnabled() {
        return helper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return helper.startNestedScroll(axes);
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
        return false;
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return false;
    }
}
