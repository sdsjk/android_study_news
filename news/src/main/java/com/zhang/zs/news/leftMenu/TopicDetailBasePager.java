package com.zhang.zs.news.leftMenu;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;
import com.zhang.zs.news.R;
import com.zhang.zs.news.activity.MainActivity;
import com.zhang.zs.news.base.BaseLeftMenuPager;
import com.zhang.zs.news.bean.NewsCenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2016/6/29.
 */
public class TopicDetailBasePager extends BaseLeftMenuPager {
    private final NewsCenter.DataBean dataBean;
    private TextView textView;
    private ViewPager viewpager;
    private TabLayout indicator;
    private ImageButton next_image;

    private List<TopicTabDetailBasePager> alltabs = new ArrayList<>();

    public TopicDetailBasePager(Context context, NewsCenter.DataBean dataBean) {

        super(context);
        this.dataBean = dataBean;
    }


    @Override
    public View initView() {
//        textView = new TextView(context);
//        textView.setTextSize(23);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.RED);


        View view = View.inflate(context, R.layout.topic_detail_basepager, null);
        viewpager = (ViewPager) view.findViewById(R.id.viewpage);
        next_image = (ImageButton) view.findViewById(R.id.next_image);
        //1。初始化
        indicator = (TabLayout) view.findViewById(R.id.indicator);
        next_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        List<NewsCenter.DataBean.ChildrenBean> children = dataBean.getChildren();

        for (int i = 0; i < children.size(); i++) {
            TopicTabDetailBasePager tabDetailBasePager = new TopicTabDetailBasePager(context, children.get(i));

            Log.e("TAG", "-------" + children.get(i).getTitle());
            alltabs.add(tabDetailBasePager);
        }


        viewpager.setAdapter(new MyPagerAdapter());

        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        indicator.setTabMode(TabLayout.MODE_SCROLLABLE);
        indicator.setupWithViewPager(viewpager);

        for (int i = 0; i < indicator.getTabCount(); i++) {
            TabLayout.Tab tab = indicator.getTabAt(i);
            tab.setCustomView(getTabView(i));
        }

        //2.关联viewpager
//        viewpager.setViewPager(viewpager);

//        textView.setText("新闻");
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
        TextView tv= (TextView) view.findViewById(R.id.textView);
        tv.setText(dataBean.getChildren().get(position).getTitle());
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.dot_focus);
        return view;
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            MainActivity mainActivity= (MainActivity) context;
            SlidingMenu slidingMenu = mainActivity.getSlidingMenu();

            if (position == 0) {
                slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else {
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
            return dataBean.getChildren().size();
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return dataBean.getChildren().get(position).getTitle();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TopicTabDetailBasePager tabDetailBasePager = alltabs.get(position);
            tabDetailBasePager.initData();
            container.addView(tabDetailBasePager.rootView);

            return tabDetailBasePager.rootView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
