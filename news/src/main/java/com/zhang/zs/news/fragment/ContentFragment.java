package com.zhang.zs.news.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhang.zs.news.R;
import com.zhang.zs.news.activity.MainActivity;
import com.zhang.zs.news.base.BaseFragmnet;
import com.zhang.zs.news.base.Basepage;
import com.zhang.zs.news.pager.Gromentpager;
import com.zhang.zs.news.pager.Homepager;
import com.zhang.zs.news.pager.Newspager;
import com.zhang.zs.news.pager.Setingpager;
import com.zhang.zs.news.pager.Smartpager;
import com.zhang.zs.news.view.MyPagerView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2016/6/28.
 */
public class ContentFragment extends BaseFragmnet {
    @ViewInject(R.id.home_rb)
    private RadioGroup home_rb;
    @ViewInject(R.id.home_viewpage)
    private MyPagerView home_viewpage;

    private List<Basepage> basepages;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.content_fragment, null);
        x.view().inject(this, view);
        return view;
    }


    @Override
    public void initData() {
        super.initData();
        home_rb.check(R.id.rb_home);

        //设置数据
        basepages = new ArrayList<>();
        basepages.add(new Homepager(context));
        basepages.add(new Newspager(context));
        basepages.add(new Smartpager(context));
        basepages.add(new Gromentpager(context));
        basepages.add(new Setingpager(context));

        home_viewpage.setAdapter(new MyPagerAdapter());
        setSlidingMode(false);
//        home_viewpage.setCurrentItem(0, false);
        basepages.get(0).initData();
        home_viewpage.addOnPageChangeListener(new MyOnPageChangeListener());
/**
 * 设置切换监听
 */


        home_rb.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

    }

    /**
     * 获取到新闻详细
     * @return
     */
    public Newspager getNewPager() {

        return (Newspager) basepages.get(1);
    }


    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            basepages.get(position).initData();
            if (position == 1) {
                basepages.get(position).ib_menu.setVisibility(View.VISIBLE);
            } else {
                basepages.get(position).ib_menu.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    home_viewpage.setCurrentItem(0, false);
                    //屏蔽侧滑
                    setSlidingMode(false);
                    break;
                case R.id.rb_news:
                    home_viewpage.setCurrentItem(1, false);
                    setSlidingMode(true);
                    break;
                case R.id.rb_smartservcie:
                    home_viewpage.setCurrentItem(2, false);
                    setSlidingMode(false);
                    break;
                case R.id.rb_govaffair:
                    home_viewpage.setCurrentItem(3, false);
                    setSlidingMode(false);
                    break;
                case R.id.rb_setting:
                    home_viewpage.setCurrentItem(4, false);
                    setSlidingMode(false);
                    break;
            }
        }
    }

    /**
     * 判断是不是要侧滑
     *
     * @param b
     */
    private void setSlidingMode(boolean b) {
        MainActivity main = (MainActivity) getActivity();
        SlidingMenu slidingMenu = main.getSlidingMenu();
        if (b) {  //要
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {//不要
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return basepages.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Basepage basepage = basepages.get(position);
            View view = basepage.view;
            // basepage.initData();
            container.addView(view);
            return view;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
