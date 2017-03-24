package blog.csdn.net.mchenys.nestedscrolling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

/**
 * Created by HuangRuiShu on 2016/10/9.
 */
public class SecActivity extends Activity {
    private String[] strings = new String[]{"1", "2", "1", "2", "1", "2", "1", "2", "1", "1", "2", "1", "2", "1", "2", "1", "2", "1", "1", "2", "1", "2", "1", "2", "1", "2", "1", "1", "2", "1", "2", "1", "2", "1", "2", "1", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2"};
    private MyListView listView;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_layout);
        listView = (MyListView) findViewById(R.id.mylistview);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings));
        layout = (RelativeLayout) findViewById(R.id.titlebar);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecActivity.this, ThirdActivity.class));
            }
        });
    }
}
