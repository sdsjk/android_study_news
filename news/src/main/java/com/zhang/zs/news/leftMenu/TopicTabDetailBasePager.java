package com.zhang.zs.news.leftMenu;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.zhang.zs.news.R;
import com.zhang.zs.news.base.BaseLeftMenuPager;
import com.zhang.zs.news.bean.NewsCenter;
import com.zhang.zs.news.bean.NewsItemBean;
import com.zhang.zs.news.bean.NewsListBean;
import com.zhang.zs.news.utils.Cache;
import com.zhang.zs.news.utils.DensityUtil;
import com.zhang.zs.news.utils.UrlUtils;
import com.zhang.zs.news.view.HorizalViewPage;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by zs on 2016/7/3.
 */
public class TopicTabDetailBasePager extends BaseLeftMenuPager {
    private final NewsCenter.DataBean.ChildrenBean childrenBean;
    private TextView textView;
    private HorizalViewPage viewpage;
    private TextView text_title;
    private LinearLayout point;

    private PullToRefreshListView pull_refresh_list;
    private ListView list_item;

    private String neturl;

    private NewsItemBean newsItemBean;

    private List<NewsListBean> newsListBeans;
    private List<NewsItemBean.DataBean.NewsBean> news;
    private List<NewsItemBean.DataBean.TopnewsBean> topnews;
    private int prepostion = 0;
    private String moreUrl = "";
    private boolean isLoadMore = false;
    private MyAdapter1 adapter1;


    public TopicTabDetailBasePager(Context context, NewsCenter.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean = childrenBean;
    }


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.topic_top_pager, null);
//        list_item = (ListView) view.findViewById(R.id.list_item);
        pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        list_item = pull_refresh_list.getRefreshableView();
        pull_refresh_list.setOnRefreshListener(new MyOnRefreshListener2());
        View viewpagertitle = View.inflate(context, R.layout.viewpager_title, null);
        viewpage = (HorizalViewPage) viewpagertitle.findViewById(R.id.viewpage);
        text_title = (TextView) viewpagertitle.findViewById(R.id.text_title);
        point = (LinearLayout) viewpagertitle.findViewById(R.id.point);
        list_item.addHeaderView(viewpagertitle);


        /**
         * Add Sound Event Listener
         */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(context);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        pull_refresh_list.setOnPullEventListener(soundListener);
        return view;
    }


    class MyOnRefreshListener2 implements PullToRefreshBase.OnRefreshListener2 {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            Toast.makeText(context, "下拉刷新...", Toast.LENGTH_SHORT).show();
            goNetAndGetData();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {

            if (!"".equals(moreUrl)) {
                Toast.makeText(context, "上啦加载...", Toast.LENGTH_SHORT).show();
                GetMoreData();
            } else {
                Toast.makeText(context, "没有更多数据...", Toast.LENGTH_SHORT).show();
                pull_refresh_list.onRefreshComplete();
            }


        }
    }

    private void GetMoreData() {

        RequestParams entity = new RequestParams(moreUrl);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "请求数据成功" + result);
                isLoadMore = true;
                progressData(result);

//                回复状态
                pull_refresh_list.onRefreshComplete();


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求数据错误" + ex.getMessage());
                pull_refresh_list.onRefreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "请求数据取消");
                pull_refresh_list.onRefreshComplete();
            }

            @Override
            public void onFinished() {
                Log.e("TAG", "请求数据完成");
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
        }


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
            String url = news.get(position).getListimage();
            url = UrlUtils.url + url;
//            x.image().bind(imageView,news.get(position).getListimage().replace("http://10.0.2.2:8080","http://36.110.76.7:8050"));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            Glide.with(context).load(url).override(128, 128).fitCenter().into(imageView);
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
                pull_refresh_list.onRefreshComplete();


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求数据错误" + ex.getMessage());
                pull_refresh_list.onRefreshComplete();
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
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5));
                if (i == 0) {
                    imageView.setEnabled(true);
                } else {
                    imageView.setEnabled(false);
                }
                if (i != 0) {
                    params.leftMargin = DensityUtil.dip2px(context, 8);
                }
                imageView.setLayoutParams(params);

                point.addView(imageView);

                viewpage.addOnPageChangeListener(new MyOnPageChangeListener());
                text_title.setText(topnews.get(0).getTitle());

            }
        }

    }

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
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String url = topnews.get(position).getTopimage();
            url = UrlUtils.url + url;
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
