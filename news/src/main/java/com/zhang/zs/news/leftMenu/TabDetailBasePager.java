package com.zhang.zs.news.leftMenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.zhang.zs.news.R;
import com.zhang.zs.news.activity.NewsDeatailActivity;
import com.zhang.zs.news.base.BaseLeftMenuPager;
import com.zhang.zs.news.bean.NewsCenter;
import com.zhang.zs.news.bean.NewsItemBean;
import com.zhang.zs.news.bean.NewsListBean;
import com.zhang.zs.news.utils.Cache;
import com.zhang.zs.news.utils.UrlUtils;
import com.zhang.zs.news.view.HorizalViewPage;
import com.zhang.zs.news.view.RefreshListView;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by zs on 2016/6/29.
 */
public class TabDetailBasePager extends BaseLeftMenuPager {
    public static final String IS_READ = "isRead";
    private final NewsCenter.DataBean.ChildrenBean childrenBean;
    private TextView textView;
    private HorizalViewPage viewpage;
    //    private HackyViewPager viewpage;
    private TextView text_title;
    private LinearLayout point;
    private RefreshListView list_item;

    private String neturl;

    private NewsItemBean newsItemBean;

    private List<NewsListBean> newsListBeans;
    private List<NewsItemBean.DataBean.NewsBean> news;
    private List<NewsItemBean.DataBean.TopnewsBean> topnews;
    private int prepostion = 0;
    private String moreUrl = "";
    private boolean isLoadMore = false;
    private MyAdapter1 adapter1;


    public TabDetailBasePager(Context context, NewsCenter.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;
    }


    @Override
    public View initView() {
//        textView = new TextView(context);
//        textView.setTextSize(23);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.RED);

        View view = View.inflate(context, R.layout.new_news_pager, null);
        list_item = (RefreshListView) view.findViewById(R.id.list_item);
        View viewpagertitle = View.inflate(context, R.layout.viewpager_title, null);
        viewpage = (HorizalViewPage) viewpagertitle.findViewById(R.id.viewpage);
        text_title = (TextView) viewpagertitle.findViewById(R.id.text_title);
        point = (LinearLayout) viewpagertitle.findViewById(R.id.point);
//        list_item.addHeaderView(viewpagertitle);
        list_item.addTopNews(viewpagertitle);

        list_item.setOnRefreshListener(new MyOnRefreshListener());

        list_item.setOnItemClickListener(new MyOnItemClickListener());
        return view;
    }


    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int realPostion = position - 1;

            NewsItemBean.DataBean.NewsBean newsBean = news.get(realPostion);

            String nameid = Cache.getString(context, IS_READ);


            if (!nameid.contains(newsBean.getId() + "")) {
                //添加到缓存当中
                Log.e("TAG", "添加到已经读队列" + newsBean.getId());
                nameid = nameid + "," + newsBean.getId();
                Log.e("TAG", "添加到已经读队列" + nameid + "--------");
                Cache.putString(context, IS_READ, nameid);
                adapter1.notifyDataSetChanged();


            }
            Intent intent = new Intent(context, NewsDeatailActivity.class);
            intent.putExtra("url", newsBean.getUrl());
            context.startActivity(intent);


        }
    }

    class MyOnRefreshListener implements RefreshListView.OnRefreshListener {

        @Override
        public void onPullDonwRefresh() {

            //下拉刷新就是重新加载列表数据
            goNetAndGetData();


        }

        @Override
        public void onLoadMore() {

            //加载更多数据
            if (!"".equals(moreUrl)) {
                //联网请求
                getMoreDataFromNet();


            } else {
                Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                list_item.onFinishRefresh(false);
            }


        }
    }

    private void getMoreDataFromNet() {

        RequestParams params = new RequestParams(moreUrl);
        params.setConnectTimeout(4000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                isLoadMore = true;
                //2.解析和显示数据
                progressData(result);

                list_item.onFinishRefresh(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                list_item.onFinishRefresh(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
//        textView.setText(childrenBean.getTitle());


        Log.e("TAG", childrenBean.getUrl() + "-------");
        neturl = UrlUtils.url + childrenBean.getUrl();
        Log.e("TAG", neturl + "-------");
        //联网请求数据

        String data = Cache.getString(context, neturl);
        if (data != null && !"".equals(data)) {
            Log.e("TAG", "有缓存数据");
            progressData(data);
        } else {
            goNetAndGetData();
//            goNetAndGetData1();

        }


    }

    private void goNetAndGetData1() {

        Log.e("TAG", "volley请求数据");
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, neturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(stringRequest);


    }


    class MyAdapter1 extends BaseAdapter {

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

            View view = View.inflate(context, R.layout.item, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.image22);
            TextView textView = (TextView) view.findViewById(R.id.title);
            TextView textView1 = (TextView) view.findViewById(R.id.descript);

            textView.setText(news.get(position).getTitle());
            textView1.setText(news.get(position).getPubdate());
            if (news.get(position).getListimage() != null) {


                String url = news.get(position).getListimage();
                       url=UrlUtils.url+url;
//            .replace("http://10.0.2.2:8080", "http://36.110.76.7:8050");
//            x.image().bind(imageView,news.get(position).getListimage().replace("http://10.0.2.2:8080","http://36.110.76.7:8050"));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                Glide.with(context).load(url).override(128, 128).fitCenter().into(imageView);
            }
            String nameid = Cache.getString(context, IS_READ);

            if (nameid.contains(news.get(position).getId() + "")) {
                textView.setTextColor(Color.GRAY);
            } else {
                textView.setTextColor(Color.BLACK);
            }


            return view;
        }
    }

    private void goNetAndGetData() {

        RequestParams entity = new RequestParams(neturl);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "请求数据成功" + result);
                //缓存数据

                Cache.putString(context, neturl, result);
                Log.e("TAG", "缓存成功");
                progressData(result);

                list_item.onFinishRefresh(true);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求数据错误" + ex.getMessage());
                list_item.onFinishRefresh(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "请求数据取消");
            }

            @Override
            public void onFinished() {
                Log.e("TAG", "请求数据完成");
            }
        });
    }

    private void progressData(String result) {
        newsItemBean = GsonGet(result);
        NewsItemBean.DataBean data = newsItemBean.getData();
        news = data.getNews();
//      设置listview数据

        // 设置viewpage数据
        //初始化数据

        String more = newsItemBean.getData().getMore();

        if (!"".equals(more) && more != null) {
            moreUrl = UrlUtils.url + more;
        } else {
            moreUrl = "";
        }


        if (isLoadMore) {

            news.addAll(newsItemBean.getData().getNews());
            adapter1.notifyDataSetChanged();
            isLoadMore = false;

        } else {


            topnews = newsItemBean.getData().getTopnews();

            viewpage.setAdapter(new MyViewPage());
            adapter1 = new MyAdapter1();
            list_item.setAdapter(adapter1);
            point.removeAllViews();
            for (int i = 0; i < topnews.size(); i++) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.point_selector);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(5), DensityUtil.dip2px(5));
                if (i == 0) {
                    imageView.setEnabled(true);
                } else {
                    imageView.setEnabled(false);
                }
                if (i != 0) {
                    params.leftMargin = DensityUtil.dip2px(8);
                }
                imageView.setLayoutParams(params);

                point.addView(imageView);
                viewpage.addOnPageChangeListener(new MyOnPageChangeListener());
                text_title.setText(topnews.get(0).getTitle());

            }
        }


        //发送消息 viewpager开始滚动
        if (handle == null) {
            handle = new Intenthandle();

        }

        Log.e("TAG", "----------" + newsItemBean.getData().getTitle());

        handle.removeCallbacksAndMessages(null);
        handle.postDelayed(new Myrunnable(), 4000);


    }


    private Intenthandle handle;

    class Intenthandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int postion = (viewpage.getCurrentItem() + 1) % topnews.size();
            viewpage.setCurrentItem(postion);
            handle.postDelayed(new Myrunnable(), 4000);

        }
    }


    class Myrunnable implements Runnable {
        @Override
        public void run() {

            handle.sendEmptyMessage(0);

        }
    }


    private boolean isdrag = false;

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            text_title.setText(topnews.get(position).getTitle());
            point.getChildAt(prepostion).setEnabled(false);

            point.getChildAt(position).setEnabled(true);
            prepostion = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                isdrag = true;
                handle.removeCallbacksAndMessages(null);
            } else if (state == ViewPager.SCROLL_STATE_SETTLING && isdrag) {
                isdrag = false;
                handle.removeCallbacksAndMessages(null);

                handle.postDelayed(new Myrunnable(), 4000);
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                isdrag = false;
                handle.removeCallbacksAndMessages(null);
                handle.postDelayed(new Myrunnable(), 4000);
            }

        }
    }

    class MyViewPage extends PagerAdapter {

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

           ImageView imageView = new ImageView(context);
//            PhotoView imageView = new PhotoView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            String url = UrlUtils.url+topnews.get(position).getTopimage();

            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.news_pic_default)
                    .error(R.drawable.news_pic_default).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private NewsItemBean GsonGet(String result) {
        return new Gson().fromJson(result, NewsItemBean.class);
    }
}
