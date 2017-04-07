package liuhongliang201745.weektestdemo01;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * date:2017/4/7.
 * author:刘宏亮.
 * function:
 */

public class MyAsyncTask extends AsyncTask<String,Integer,List<JsonBean.ListBean>>{
    private ListView mListView;
    private Context mContext;
    private List<JsonBean.ListBean>list=new ArrayList<>();
    private CommonAdapter<JsonBean.ListBean> mAdapter;
    private int mId;

    public MyAsyncTask(ListView listView, Context context) {
        mListView = listView;
        mContext = context;
    }

    @Override
    protected List<JsonBean.ListBean> doInBackground(String... params) {
        String s = doGet(Url.url);
        Gson gson = new Gson();
        JsonBean jsonBean = gson.fromJson(s, JsonBean.class);
        return jsonBean.getList();
    }

    @Override
    protected void onPostExecute(List<JsonBean.ListBean> been) {
        super.onPostExecute(been);
        list=been;
        mAdapter = new CommonAdapter<JsonBean.ListBean>(mContext, list) {



            @Override
            public void convert(ViewHolder holder, JsonBean.ListBean item) {
                holder.setText(R.id.textView2,item.getSite_name());
                holder.setText(R.id.textView3,item.getAddress());
                mId = item.getId();
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext,list.get(position).getId()+"",Toast.LENGTH_SHORT).show();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                list.remove(list.get(position));
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public String doGet(String uri){
            StringBuffer sb=new StringBuffer();
            try {
                URL url=new URL(uri);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                if(conn.getResponseCode()==200){
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String line=null;
                    while((line=br.readLine())!=null){
                        sb.append(line);
                    }
                }
                return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
}
