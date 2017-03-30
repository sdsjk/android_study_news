package com.zhang.zs.news.pager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.zhang.zs.news.R;
import com.zhang.zs.news.adapter.ShopAdapter;
import com.zhang.zs.news.base.Basepage;
import com.zhang.zs.news.bean.ShopBean;
import com.zhang.zs.news.utils.UrlUtils;

import java.util.List;

/**
 * Created by zs on 2016/6/28.
 */
public class Smartpager extends Basepage {


    /**
     * 正常状态
     */
    private static final int STATE_NORMAL = 1;
    /**
     * 刷新状态
     */
    private static final int STATE_REFREH = 2;


    /**
     * 加载更多或者上拉刷新
     */
    private static final int STATE_MORE = 3;


    private int state = STATE_NORMAL;
    /**
     * 上啦刷新，下拉加载控件
     */
    private MaterialRefreshLayout refresh;

    /**
     * 特殊的listview可以这样理解
     */
    private RecyclerView recyclerView;


    private int pageSize = 10;

    private int curPage = 1;
    private String url;
    //列表数据
    private List<ShopBean.ListBean> list;

    private ShopAdapter adapter;
    private int totalpager;

    public Smartpager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("商城热卖");
        View view = View.inflate(context, R.layout.productlist, null);
        state=STATE_NORMAL;
        initData(view);

        getDataFromNet();


        initfrash();

        /**
         * 添加内容到布局当中
         */
        base_content.removeAllViews();
        base_content.addView(view);
    }

    private void initfrash() {

        refresh.setMaterialRefreshListener(new MyMaterialRefreshListener());

    }


    class MyMaterialRefreshListener extends MaterialRefreshListener {
        /**
         * 刷新
         *
         * @param materialRefreshLayout
         */
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            curPage = 1;
            state = STATE_REFREH;
            url = UrlUtils.SHOPURL + "pageSize=" + pageSize + "&curPage=" + curPage;
            getDataFromNet();

        }

        /**
         * 加载更多
         *
         * @param materialRefreshLayout
         */
        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);

            if(curPage<totalpager){
                    curPage=curPage+1;
                    state=STATE_MORE;
                url = UrlUtils.SHOPURL + pageSize + "&curPage=" + curPage;
                getDataFromNet();

            }else {
                Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                refresh.finishRefreshLoadMore();

            }

        }
    }

    /**
     * 网络请求数据
     */
    private void getDataFromNet() {
        url = UrlUtils.SHOPURL + "pageSize=" + pageSize + "&curPage=" + curPage;

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest String = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<java.lang.String>() {
            @Override
            public void onResponse(String s) {

                Log.e("TAG", "数据请求成功" + s);

                //解析数据

                progressData(s);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAG", "数据请求失败" + volleyError.getMessage());
            }
        });

        //添加队列
        queue.add(String);

    }

    /**
     * 解析数据
     */
    private void progressData(String s) {
        ShopBean shopBean = GetJsonByGson(s);
        //等到列表数据
        list = shopBean.getList();
        totalpager=shopBean.getTotalPage();

        showData();




    }

    private void showData() {
        switch (state){
            case STATE_NORMAL:
                //设置适配器
                adapter = new ShopAdapter(context, list);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                break;
            case STATE_REFREH:
                //清空数据
                adapter.removeAllData();
                adapter.addNewData(list);
                refresh.finishRefresh();
                break;
            case STATE_MORE:
                        //1.在原理的基础上添加数据
                adapter.addData(adapter.getDataCount(),list);
                //2.加载更多的布局隐藏
                refresh.finishRefreshLoadMore();
                break;
        }
    }

    /**
     * 数据解析
     *
     * @param s
     * @return
     */
    private ShopBean GetJsonByGson(String s) {
        return new Gson().fromJson(s, ShopBean.class);
    }

    /**
     * 初始化控件
     * <p/>
     * 方法提取的快捷键是 alt  shift  q
     */
    private void initData(View view) {
        refresh = (MaterialRefreshLayout) view.findViewById(R.id.refresh);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }
}
