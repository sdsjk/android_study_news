package com.zhang.zs.beijingnews.leftview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;
import com.zhang.zs.beijingnews.R;
import com.zhang.zs.beijingnews.activity.MainActivity;
import com.zhang.zs.beijingnews.base.BaseLeft;
import com.zhang.zs.beijingnews.domin.NewsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2016/6/29.
 */
public class NewLeftpager extends BaseLeft {

    private NewsData.DataBean databean;
    public TextView textview;
    private ViewPager viewPager;
    private TabPageIndicator indicator;
    private ImageButton next_image;
    private List<NewsDetailpager> newsDetailpagers = new ArrayList<>();
    private NewsDetailpager newsDetailpager;

    public NewLeftpager(Context context) {
        super(context);
    }

    public NewLeftpager(Context context, NewsData.DataBean dataBean) {
        super(context);
        this.databean = dataBean;
    }

    @Override
    public View initView() {


//        textview = new TextView(context);
//        textview.setTextColor(Color.RED);
//
//        textview.setTextSize(20);
//        textview.setGravity(Gravity.CENTER);

        //显示一个viewpager

        View view = View.inflate(context, R.layout.newsleft, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewpage);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        next_image = (ImageButton) view.findViewById(R.id.next_image);

        next_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
        return view;
    }

    @Override
    public void initData() {

        List<NewsData.DataBean.ChildrenBean> children = databean.getChildren();

        for (int i = 0; i < children.size(); i++) {
            NewsDetailpager newsDetailpager = new NewsDetailpager(context, children.get(i));
            newsDetailpagers.add(newsDetailpager);
        }


        viewPager.setAdapter(new MyPagerAdapter());
        newsDetailpagers.get(0).initData();

        indicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            MainActivity mainActivity = (MainActivity) context;
            SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
            if (position == 0) {

                slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            } else {
                slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {

            return newsDetailpagers.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return databean.getChildren().get(position).getTitle();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
             newsDetailpager = newsDetailpagers.get(position);
            View view = newsDetailpager.rootview;
            newsDetailpager.initData();
            container.addView(view);


            return view;
        }
    }
}
