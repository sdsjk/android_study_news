package com.zhang.zs.news.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhang.zs.news.R;
import com.zhang.zs.news.activity.MainActivity;
import com.zhang.zs.news.base.BaseFragmnet;
import com.zhang.zs.news.bean.NewsCenter;
import com.zhang.zs.news.pager.Newspager;
import com.zhang.zs.news.utils.DensityUtil;

import java.util.List;

/**
 * Created by zs on 2016/6/28.
 */
public class LeftFragment extends BaseFragmnet {
    List<NewsCenter.DataBean> data;
    private int currentPostion;
    private ListView listView;
    private MyAdapter adapter;

    @Override
    public View initView() {
        listView = new ListView(context);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setDividerHeight(0);
        listView.setBackgroundColor(Color.BLACK);
        listView.setPadding(0, DensityUtil.dip2px(context, 40), 0, 0);

        listView.setOnItemClickListener(new MyOnItemClickListener());
        return listView;
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //对应条目变红
            currentPostion = position;
            adapter.notifyDataSetChanged();

            //关闭右侧菜单
            MainActivity mainActivity= (MainActivity) context;
            SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
            slidingMenu.toggle();

            //内容详情显示相应的内容
            changeNewsView(currentPostion);


        }
    }

    private void changeNewsView(int currentPostion) {
        //获取mainactivity
         MainActivity mainActivity= (MainActivity) context;

        //获取到 content
        ContentFragment contentFragment= mainActivity.getContentView();
        //获取到 newpager
         Newspager newspager=contentFragment.getNewPager();
        //调用newspager的切换方法
        newspager.switchpager(currentPostion);

    }

    @Override
    public void initData() {
        super.initData();


    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textview = (TextView) View.inflate(context, R.layout.textview, null);
            textview.setText(data.get(position).getTitle());

            textview.setEnabled(currentPostion == position);


            return textview;
        }
    }

    public void setLeftData(List<NewsCenter.DataBean> data) {

        this.data = data;
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        for (int i = 0; i < data.size(); i++) {
            Log.e("TAG", data.get(i).getTitle() + "--------");
        }
    }
}
