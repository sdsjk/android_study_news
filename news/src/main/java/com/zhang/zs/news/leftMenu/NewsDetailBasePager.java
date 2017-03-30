package com.zhang.zs.news.leftMenu;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
public class NewsDetailBasePager extends BaseLeftMenuPager {
    private final NewsCenter.DataBean dataBean;
    private TextView textView;
    private ViewPager viewpager;
    private TabPageIndicator indicator;
    private ImageButton next_image;

    private List<TabDetailBasePager> alltabs = new ArrayList<>();

    public NewsDetailBasePager(Context context, NewsCenter.DataBean dataBean) {

        super(context);
        this.dataBean = dataBean;
    }


    @Override
    public View initView() {
//        textView = new TextView(context);
//        textView.setTextSize(23);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.RED);


        View view = View.inflate(context, R.layout.news_detail_basepager, null);
        viewpager = (ViewPager) view.findViewById(R.id.viewpage);
        next_image = (ImageButton) view.findViewById(R.id.next_image);
        //1。初始化
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        next_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        List<NewsCenter.DataBean.ChildrenBean> children = dataBean.getChildren();

        for (int i = 0; i < children.size(); i++) {
            TabDetailBasePager tabDetailBasePager = new TabDetailBasePager(context, children.get(i));

            Log.e("TAG", "-------" + children.get(i).getTitle());
            alltabs.add(tabDetailBasePager);
        }


        viewpager.setAdapter(new MyPagerAdapter());

        indicator.setOnPageChangeListener(new MyOnPageChangeListener());

        //2.关联viewpager
        indicator.setViewPager(viewpager);

//        textView.setText("新闻");
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
            TabDetailBasePager tabDetailBasePager = alltabs.get(position);
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
