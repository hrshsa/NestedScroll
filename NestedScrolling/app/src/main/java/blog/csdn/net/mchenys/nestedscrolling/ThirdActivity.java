package blog.csdn.net.mchenys.nestedscrolling;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

/**
 * Created by HuangRuiShu on 2017/3/23.
 */

public class ThirdActivity extends Activity {

    private NestedScrollWebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
        webView = (NestedScrollWebView) findViewById(R.id.webview);
        webView.loadUrl("http://mrobot.pcauto.com.cn/xsp/s/auto/info/nocache/bbs/topics.xsp?pageNo=1&pageSize=19&authorId=0&picRule=2&topicId=13749668&night=false&special=0&app=pcautobrowser&appKey=36172344ca14ed2b00000600");

    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.e("-------","down");
//        webView.dispatchTouchEvent(ev);
//        return super.dispatchTouchEvent(ev);
//    }
}
