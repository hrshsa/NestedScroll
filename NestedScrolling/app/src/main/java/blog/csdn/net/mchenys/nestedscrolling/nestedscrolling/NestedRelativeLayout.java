package blog.csdn.net.mchenys.nestedscrolling.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import blog.csdn.net.mchenys.nestedscrolling.R;

/**
 * Created by mChenys on 2016/8/19.
 */
public class NestedRelativeLayout extends RelativeLayout implements NestedScrollingParent {
    private NestedScrollingParentHelper mParentHelper;
    private int minheight;
    private int maxheight;
    public NestedRelativeLayout(Context context) {
        super(context);
        init();
    }

    public NestedRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mParentHelper = new NestedScrollingParentHelper(this);
        maxheight = getResources().getDimensionPixelOffset(R.dimen.aaaa);
        minheight = maxheight/2;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.titlebar);
        LayoutParams params = (LayoutParams) layout.getLayoutParams();
        if (params.height >= maxheight && dy>0 ){
            consumed[1] = dy;
        } else if (params.height <= minheight && dy < 0){
            consumed[1] = dy;
        } else if (params.height + dy > maxheight){
            consumed[1] = dy - (maxheight - params.height);
            params.height = maxheight;
        } else if (params.height + dy < minheight){
            consumed[1] = dy + params.height - minheight;
            params.height = minheight;
        } else {
            params.height += dy;
            consumed[1] = 0;
        }
        layout.requestLayout();

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
        return mParentHelper.getNestedScrollAxes();
    }
}
