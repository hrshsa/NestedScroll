package blog.csdn.net.mchenys.nestedscrolling.nestedscrolling;

import android.content.Context;
import android.renderscript.Int2;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ListView;
import android.widget.OverScroller;

/**
 * Created by mChenys on 2016/8/19.
 */
public class NestedListView extends ListView implements NestedScrollingChild {
    private float lastY;
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private NestedScrollingChildHelper mChildHelper;
    public NestedListView(Context context) {
        super(context);
        init();
    }

    public NestedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mScroller = new OverScroller(getContext());
        mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getAdapter().getView(0,null,this);
        measureChild(child,widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(child.getMeasuredWidth(),getAdapter().getCount()*child.getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = ev.getY() - lastY;
                lastY = ev.getY();
                int[] consumed = new int[2];
                int[] offset = new int[2];
                dispatchNestedPreScroll(0, (int) dy,consumed,offset);
                mScroller.startScroll(0,mScroller.getFinalY(),0, (int) -consumed[1]);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
//                mScroller.springBack()
                mScroller.fling(0,mScroller.getFinalY(),0, (int) -velocityTracker.getYVelocity(),0,0,0,getHeight());
                break;
        }
        return true;
//        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(0,mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }
}
