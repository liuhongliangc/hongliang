package liuhongliang201745.weektestdemo01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView main_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        main_listview = (ListView) findViewById(R.id.main_listview);
        MyAsyncTask task = new MyAsyncTask(main_listview, this);
        task.execute();
    }
}
