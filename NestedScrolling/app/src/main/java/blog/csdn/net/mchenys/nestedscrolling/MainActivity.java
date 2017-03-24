package blog.csdn.net.mchenys.nestedscrolling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    private RelativeLayout mTitleBar;
    private ListView mContent;
    private String[] strings = new String[]{"1","2","1","2","1","2","1","2","1","2","1","2","1","2","1","2","1","2","1","2","1","2","1","2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleBar = (RelativeLayout) findViewById(R.id.titlebar);
        mTitleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecActivity.class));

            }
        });
        mContent = (ListView) findViewById(R.id.listview);
        mContent.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings));
    }
}
