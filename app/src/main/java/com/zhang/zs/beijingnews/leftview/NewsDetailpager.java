package com.zhang.zs.beijingnews.leftview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhang.zs.beijingnews.R;
import com.zhang.zs.beijingnews.base.BaseLeft;
import com.zhang.zs.beijingnews.domin.NewsData;
import com.zhang.zs.beijingnews.domin.NewsList;
import com.zhang.zs.beijingnews.util.CashUtils;
import com.zhang.zs.beijingnews.util.UrlUtils;
import com.zhang.zs.beijingnews.view.MyViewPager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import okhttp3.Request;

/**
 * Created by zs on 2016/6/29.
 */
public class NewsDetailpager extends BaseLeft {
    private final NewsData.DataBean.ChildrenBean childrenBean;
    /**
     * 数据
     */
    /**
     * 初始化试图
     */

    private MyViewPager viewpager;

    private TextView tv_title;

    private LinearLayout ll_point_group;

    public TextView textview;
    /**
     * 列表的请求地址
     */
    private String detail_url;

    /**
     * 列表数据
     */
    private NewsList newsList;
    /**
     * 头部viewpage数据
     */
    private List<NewsList.DataBean.TopnewsBean> topnews;

    private ListView listview;

    private PullToRefreshListView pullToRefreshListView ;
    /**
     * 前一个红点的点位置
     */
    private int prePostion = 0;
    /**
     * 新闻列表数据
     */
    private List<NewsList.DataBean.NewsBean> news;

    public NewsDetailpager(Context context, NewsData.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;

    }

    /**
     * 初始化试图
     *
     * @return
     */
    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.news_detail_pager, null);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
        listview =pullToRefreshListView.getRefreshableView();
        View topview=View.inflate(context,R.layout.top_news_viewpager,null);
        viewpager = (MyViewPager) topview.findViewById(R.id.viewpager);
        tv_title = (TextView) topview.findViewById(R.id.tv_title);
        ll_point_group = (LinearLayout) topview.findViewById(R.id.ll_point_group);
        //Listview添加数据
        listview.addHeaderView(topview);

        /**
         * 刷新回调
         */
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                getDataFromNet2();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                //上啦加载加载更多
                getDataMoreFromNet2();




            }
        });
        return view;
    }

    private void getDataMoreFromNet2() {


    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

        detail_url = UrlUtils.url + childrenBean.getUrl();
        Log.e("TAG", detail_url + "-----");

        /**
         *联网请求
         */

         String data=CashUtils.getString(context,detail_url);

        if(data!=null&& !"".equals(data)){
            ProgressData(data);
        }else {
//            getDataFromNet1();
            getDataFromNet2();

        }


    }

    private void getDataFromNet2() {


        OkHttpUtils
                .get()
                .url(detail_url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.e("TAG", "是用OkHttp失败" + e.getMessage());
                        pullToRefreshListView.onRefreshComplete();

                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "是用OkHttp请求成功" + response);
                        ProgressData(response);
                        pullToRefreshListView.onRefreshComplete();
                    }
                });



    }

    /**
     * 数据请求
     */
    private void getDataFromNet1() {


        RequestParams params = new RequestParams(detail_url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "请求成功" + result);

                /**
                 * 数据进行缓存
                 */
//                CashUtils.putString(context, detail_url, result);
//                Log.e("TAG", "缓存成功");
                /**
                 * 数据解析
                 */
                ProgressData(result);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求失败" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "请求取消");
            }

            @Override
            public void onFinished() {
                Log.e("TAG", "请求完成");
            }
        });


    }

    /**
     * 数据解析
     */
    private void ProgressData(String result) {
        newsList = getDataByGson(result);
        //判断是否有更多数据
        String more = newsList.getData().getMore();



        /**
         * 设置viewpager数据
         */

        topnews = newsList.getData().getTopnews();

        /**
         * 设置适配器
         */
        viewpager.setAdapter(new TopPagerAdapter());

        /**
         * 设置页面监听
         *
         */
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        /**
         * 设置默认选中显示第一个文字
         */
        tv_title.setText(topnews.get(0).getTitle());


        /**
         * 显示圆点
         */
        ll_point_group.removeAllViews();
        for (int i = 0; i < topnews.size(); i++) {

            ImageView imageView = new ImageView(context);

            imageView.setBackgroundResource(R.drawable.image_point_selector);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(6), DensityUtil.dip2px(6));
            if (i != 0) {
                params.leftMargin = 8;
                imageView.setEnabled(false);
            }

            imageView.setLayoutParams(params);

            ll_point_group.addView(imageView);
        }


        /**
         * 设置Listview中的数据
         */

        news = newsList.getData().getNews();




        listview.setAdapter(new MyBaseAdapter());


    }


    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return news.size();
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
            Viewhodle viewhodle;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_tab_detail_pager, null);
                viewhodle = new Viewhodle();
                viewhodle.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewhodle.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewhodle.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(viewhodle);
            } else {
                viewhodle = (Viewhodle) convertView.getTag();
            }

            viewhodle.tv_title.setText(news.get(position).getTitle());
            viewhodle.tv_time.setText(news.get(position).getPubdate());
            String url=news.get(position).getListimage();
            url = url.replace("10.0.2.2:8080", "36.110.76.7:8050");
            x.image().bind(viewhodle.iv_icon,url);
            return convertView;
        }
    }

    class Viewhodle {
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            String title = topnews.get(position).getTitle();
            tv_title.setText(title);

            ll_point_group.getChildAt(prePostion).setEnabled(false);
            ll_point_group.getChildAt(position).setEnabled(true);
            prePostion = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class TopPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return topnews.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.home_scroll_default);
            String url = topnews.get(position).getTopimage();
            Log.e("TAG", url + "---------");
            url = url.replace("10.0.2.2:8080", "36.110.76.7:8050");
            Log.e("TAG", url + "---------1111");
            x.image().bind(imageView, url);
            container.addView(imageView);
            return imageView;
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

    /**
     * 通过第三方框架来解析shujuu
     *
     * @param result
     * @return
     */
    private NewsList getDataByGson(String result) {
        return new Gson().fromJson(result, NewsList.class);
    }
}
