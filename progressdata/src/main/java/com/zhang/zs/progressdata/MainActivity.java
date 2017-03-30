package com.zhang.zs.progressdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listview;
    private String url = "http://api.bilibili.com/online_list?_device=android&platform=android&typeid=13&sign=a520d8d8f7a7240013006e466c8044f7%22";
    private List<DataBean> beans = new ArrayList<>();
     private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview);
        progress = (ProgressBar)findViewById(R.id.progress);
        getData();

        listview.setAdapter(new Mydescription());

    }


    class Mydescription extends BaseAdapter{

        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public Object getItem(int position) {
            return beans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view=View.inflate(MainActivity.this,R.layout.item,null);

            ImageView imageView= (ImageView) view.findViewById(R.id.image22);
            TextView  textView= (TextView) view.findViewById(R.id.title);
            TextView  textView1= (TextView) view.findViewById(R.id.descript);

            textView.setText(beans.get(position).getTitle());
            textView1.setText(beans.get(position).getDescription());
            x.image().bind(imageView,beans.get(position).getPic());
            return view;
        }
    }

    private void getData() {


        RequestParams entity = new RequestParams(url);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.e("TAG", "成功.........." + result);

                progressData(result);
                progress.setVisibility(View.GONE);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "失败..........");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "取消..........");
            }

            @Override
            public void onFinished() {
                Log.e("TAG", "完成..........");
            }
        });
    }

    private void progressData(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject list = jsonObject.optJSONObject("list");

            for (int i = 0; i < 21; i++) {
                JSONObject jsonObject1 = list.optJSONObject("" + i);
                String title=jsonObject1.optString("title");
                String pic=jsonObject1.optString("pic");
                String description=jsonObject1.optString("description");
                DataBean dataBean=new DataBean();
                dataBean.setTitle(title);
                dataBean.setPic(pic);
                dataBean.setDescription(description);
                beans.add(dataBean);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
