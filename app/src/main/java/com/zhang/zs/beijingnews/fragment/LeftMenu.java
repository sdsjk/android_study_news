package com.zhang.zs.beijingnews.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhang.zs.beijingnews.R;
import com.zhang.zs.beijingnews.activity.MainActivity;
import com.zhang.zs.beijingnews.base.BaseFragment;
import com.zhang.zs.beijingnews.domin.NewsData;
import com.zhang.zs.beijingnews.pager.Newpager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by zs on 2016/6/28.
 */
public class LeftMenu extends BaseFragment {
    //    private List<String> alldata = new ArrayList<>();
    private ListView listView;
    private List<NewsData.DataBean> alldata;

    private int currentpostion;
    private MybaseAdapter adapter;

    /**
     * 初始化试图
     */
    @Override
    public View initView() {
        listView = new ListView(context);
        listView.setDividerHeight(0);
        listView.setBackgroundColor(Color.BLACK);
        listView.setCacheColorHint(Color.TRANSPARENT);
        return listView;
    }

    /**
     * 初始化shuju
     */
    @Override
    public void initData() {
        super.initData();

//        for (int i = 0; i < 10; i++) {
//            alldata.add("我是" + i);
//        }


    }

    public void setData(List<NewsData.DataBean> data) {
        this.alldata = data;

        adapter = new MybaseAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new MyOnItemClickListener());
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //对应字体变变成红色
            currentpostion = position;
            adapter.notifyDataSetChanged();
            ;
            // 关闭左侧菜单

            MainActivity mainActivity = (MainActivity) context;
            SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
            slidingMenu.toggle();
            // 右侧内容部分显示对应的内容

             changeContent(currentpostion);


        }
    }

    /**
     * 显示新闻相应的内容
     * @param currentpostion
     */
    private void changeContent(int currentpostion) {

        MainActivity mainActivity= (MainActivity) context;
        Content content= mainActivity.getContent();
        Newpager newpager=content.getNewPager();
        newpager.setCurrentShow(currentpostion);

    }
    class MybaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return alldata.size();
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

            TextView view = (TextView) View.inflate(context, R.layout.leftview, null);

            view.setText(alldata.get(position).getTitle());
            view.setEnabled(currentpostion == position);
            return view;
        }
    }

}
