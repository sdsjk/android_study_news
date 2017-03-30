package com.zhang.zs.beijingnews.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhang.zs.beijingnews.R;
import com.zhang.zs.beijingnews.activity.MainActivity;
import com.zhang.zs.beijingnews.base.BaseFragment;
import com.zhang.zs.beijingnews.base.BasePager;
import com.zhang.zs.beijingnews.pager.Groverpager;
import com.zhang.zs.beijingnews.pager.Homepager;
import com.zhang.zs.beijingnews.pager.Newpager;
import com.zhang.zs.beijingnews.pager.Setingpager;
import com.zhang.zs.beijingnews.pager.Smartpager;
import com.zhang.zs.beijingnews.view.NoScrollerViewPager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2016/6/28.
 */
public class Content extends BaseFragment {
    @ViewInject(R.id.home_rb)
    private RadioGroup home_rb;
    @ViewInject(R.id.home_viewpage)
    private NoScrollerViewPager home_viewpage;

    private List<BasePager> basePagers;

    /**
     * 初始化试图
     *
     * @return
     */
    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.home_content, null);
        x.view().inject(this, view);
        //设置点击button切换viewpager
        home_rb.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        return view;
    }

    //获取新闻详情页面
    public Newpager getNewPager() {

        return (Newpager) basePagers.get(1);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    home_viewpage.setCurrentItem(0, false);
                    changeSlidingState(false);

                    break;
                case R.id.rb_news:
                    home_viewpage.setCurrentItem(1, false);
                    changeSlidingState(true);
                    break;
                case R.id.rb_smartservcie:
                    home_viewpage.setCurrentItem(2, false);
                    changeSlidingState(false);
                    break;
                case R.id.rb_govaffair:
                    home_viewpage.setCurrentItem(3, false);
                    changeSlidingState(false);
                    break;
                case R.id.rb_setting:
                    home_viewpage.setCurrentItem(4, false);
                    changeSlidingState(false);
                    break;
            }
        }
    }

    /**
     * 修改是不是可以滑动左菜单
     *
     * @param b
     */
    private void changeSlidingState(boolean b) {
        MainActivity mainActivity = (MainActivity) context;

        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();

        if (b) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        super.initData();
        home_rb.check(R.id.rb_home);

        //初始化显示试图
        basePagers = new ArrayList<>();
        basePagers.add(new Homepager(context));
        basePagers.add(new Newpager(context));
        basePagers.add(new Smartpager(context));
        basePagers.add(new Groverpager(context));
        basePagers.add(new Setingpager(context));

        //viewpage 显示五个页面
        home_viewpage.setAdapter(new MyPagerAdapter());

        //viewpager设置滑动监听
         basePagers.get(0).initData();
        home_viewpage.addOnPageChangeListener(new MyOnPageChangeListener());

    }


    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {
            basePagers.get(position).initData();
            if (position == 1) {

                changeSlidingState(true);
            } else {
                changeSlidingState(false);
            }
            switch (position) {
                case 0:
                    home_rb.check(R.id.rb_home);
                    break;
                case 1:
                    home_rb.check(R.id.rb_news);
                    break;
                case 2:
                    home_rb.check(R.id.rb_smartservcie);
                    break;
                case 3:
                    home_rb.check(R.id.rb_govaffair);
                    break;
                case 4:
                    home_rb.check(R.id.rb_setting);
                    break;
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return basePagers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = basePagers.get(position);
            View view = basePager.rootView;

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
