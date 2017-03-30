package com.zhang.zs.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhang.zs.news.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zs on 2016/7/2.
 */
public class RefreshListView extends ListView {
    /**
     * 下拉刷新控件和顶部轮播图部分
     */
    private LinearLayout headerView;

    /**
     * 下拉刷新控件
     */
    private View ll_pull_down_refresh;
    private ImageView iv_arrow;
    private ProgressBar pb_statu;
    private TextView tv_statu;
    private TextView tv_time;
    /**
     * 下拉刷新空间的高度
     */
    private int pullDownRefrehHeight;
    /**
     * ListView在屏幕上的坐标
     */
    private int listViewOnScreenY = -1;
    /**
     * 顶部轮播图部分
     */
    private View topnews;

    /**
     * 下拉刷新状态
     */
    private static final int PULL_DOWN_REFRESH = 1;


    /**
     * 松手刷新状态
     */
    private static final int RELEASE_REFRESH = 2;


    /**
     * 正在刷新状态
     */
    private static final int REFRESHING = 3;


    private int currentState = PULL_DOWN_REFRESH;


    private LinearLayout refresh_foot;

    private int pullDownRefrehfootHeight;
    /**
     * 是不是加载更多
     */
    public boolean isLoadMore;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();
        initFooterView(context);
    }

    /**
     * 初始化加载更多
     */
    private void initFooterView(Context context) {
        refresh_foot = (LinearLayout) View.inflate(context, R.layout.listviewfoot, null);

        refresh_foot.measure(0, 0);
        pullDownRefrehfootHeight = refresh_foot.getMeasuredHeight();
        refresh_foot.setPadding(0, -pullDownRefrehfootHeight, 0, 0);
        addFooterView(refresh_foot);
        setOnScrollListener(new MyOnScrollListener());

    }

    private Animation upAnimation;
    private Animation downAnimation;

    private void initAnimation() {
        upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);
        downAnimation = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView(Context context) {
        headerView = (LinearLayout) View.inflate(context, R.layout.llistviewhead, null);
        ll_pull_down_refresh = headerView.findViewById(R.id.pull_down_freash);//下拉刷新控件
        iv_arrow = (ImageView) headerView.findViewById(R.id.imageview_arrow);
        pb_statu = (ProgressBar) headerView.findViewById(R.id.pb_frash);
        tv_statu = (TextView) headerView.findViewById(R.id.tv_state);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);

        //View.setPadding(0,-控件的高，0,0);//完成隐藏
        //View.setPadding(0,0，0,0);//完成显示
        //View.setPadding(0,控件的高，0,0);//控件两倍高显示
        ll_pull_down_refresh.measure(0, 0);
        pullDownRefrehHeight = ll_pull_down_refresh.getMeasuredHeight();
        ll_pull_down_refresh.setPadding(0, -pullDownRefrehHeight, 0, 0);

        addHeaderView(headerView);


    }


    class MyOnScrollListener implements OnScrollListener {


        /**
         * 当ListView滚动状态发生变化的时候回调这个方法
         * 静止->滑动
         * 滑动-->惯性滚动
         * 惯性滚动-静止
         *
         * @param view
         * @param scrollState
         */
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
                if (getLastVisiblePosition() == getCount() - 1) {
                    refresh_foot.setPadding(0, 0, 0, 0);
                    isLoadMore = true;
                    //3.调用接口
                    if (onRefreshListener != null) {
                        onRefreshListener.onLoadMore();
                    }
                }


            }

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }


    private float startY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录坐标
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                if (currentState == REFRESHING) {
                    break;
                }

                //判断顶部轮播图是否完全显示
                boolean isDisplayTopnews = isDisplayTopnews();
                if (!isDisplayTopnews) {
                    break;
                }


                //2.来到新坐标
                float endY = event.getY();
                float distanceY = endY - startY;
                if (distanceY > 0) {//往下拉才显示下拉刷新控件
//                    int paddingTop = -控件的高 + distanceY;
//                    View.setPadding(0,paddingTop，0,0);//完成显示
                    int paddingTop = (int) (-pullDownRefrehHeight + distanceY);
                    if (paddingTop > 0 && currentState != RELEASE_REFRESH) {
                        currentState = RELEASE_REFRESH;
                        //更新UI
                        refreshHeaderViewState();

                    } else if (paddingTop < 0 && currentState != PULL_DOWN_REFRESH) {
                        currentState = PULL_DOWN_REFRESH;
                        //更新UI
                        refreshHeaderViewState();

                    }


                    ll_pull_down_refresh.setPadding(0, paddingTop, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = 0;
                if (currentState == PULL_DOWN_REFRESH) {
                    //控件隐藏
//                    View.setPadding(0,-控件的高，0,0);//完成隐藏
                    ll_pull_down_refresh.setPadding(0, -pullDownRefrehHeight, 0, 0);

                } else if (currentState == RELEASE_REFRESH) {

                    currentState = REFRESHING;
//                    View.setPadding(0,0，0,0);//完成显示
                    ll_pull_down_refresh.setPadding(0, 0, 0, 0);
                    refreshHeaderViewState();
                    //调用接口
                    if (onRefreshListener != null) {
                        onRefreshListener.onPullDonwRefresh();
                    }

                }
                break;

        }
        return super.onTouchEvent(event);
    }

    private void refreshHeaderViewState() {
        switch (currentState) {
            case PULL_DOWN_REFRESH://下拉刷新
                iv_arrow.startAnimation(downAnimation);
                tv_statu.setText("下拉刷新...");
                break;
            case RELEASE_REFRESH://手松刷新
                iv_arrow.startAnimation(upAnimation);
                tv_statu.setText("手松刷新...");
                break;
            case REFRESHING://正在刷新
                tv_statu.setText("正在刷新...");
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(INVISIBLE);
                pb_statu.setVisibility(VISIBLE);
                break;
        }
    }

    /**
     * 当ListView在屏幕上的Y轴坐标小于或者等于顶部轮播在屏幕上Y轴坐标的时候，顶部轮播图就完全显示了
     *
     * @return
     */
    private boolean isDisplayTopnews() {

        if (topnews != null) {
            int[] location = new int[2];

            //1.ListView在屏幕上的Y轴坐标
            if (listViewOnScreenY == -1) {
                getLocationOnScreen(location);
                listViewOnScreenY = location[1];
            }


            //2.顶部轮播在屏幕上Y轴坐标
            topnews.getLocationOnScreen(location);
            int topnewsOnScreenY = location[1];


            //3.比较
//        if(listViewOnScreenY <=topnewsOnScreenY){
//            return true;
//        }else{
//            return  false;
//        }
            return listViewOnScreenY <= topnewsOnScreenY;
        } else {
            return true;
        }


    }

    public void addTopNews(View topnews) {
        this.topnews = topnews;
        //下拉刷新空间和顶部轮播图部分合并
        if (topnews != null) {

            headerView.addView(topnews);
        }
    }

    public void onFinishRefresh(boolean sucess) {

        if(isLoadMore){
            refresh_foot.setPadding(0, -pullDownRefrehfootHeight, 0, 0);
        }

        //1.状态还原
        currentState = PULL_DOWN_REFRESH;
        //2.下拉刷新控件隐藏
        ll_pull_down_refresh.setPadding(0, -pullDownRefrehHeight, 0, 0);
        tv_statu.setText("下拉刷新...");
        pb_statu.setVisibility(GONE);
        iv_arrow.setVisibility(VISIBLE);


        if (sucess) {
            tv_time.setText("上次刷新时间：" + getSystemTime());
        }

    }

    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 刷新的监听者
     */
    public interface OnRefreshListener {

        /**
         * 当下拉刷新的时候回调这个方法
         */
        public void onPullDonwRefresh();

        public void onLoadMore();

    }


    private OnRefreshListener onRefreshListener;


    /**
     * 设置监听刷新控件
     *
     * @param l
     */
    public void setOnRefreshListener(OnRefreshListener l) {
        this.onRefreshListener = l;

    }

}
