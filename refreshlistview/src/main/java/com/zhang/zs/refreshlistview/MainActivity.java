package com.zhang.zs.refreshlistview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private RecyclerView recyclerview;
    private Button btn_add, btn_delete, btn_list, btn_grid;
    private ArrayList<String> datas;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //初始化数据
        initData();

        setAdapter();

        //设置分割线

        addItemDecoration();

        setListener();


    }

    /**
     * 设置监听
     */
    private void setListener() {

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String content) {
                Toast.makeText(MainActivity.this, "点击" + position, Toast.LENGTH_SHORT).show();
            }
        });

        //设置按钮的监听


        btn_add.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_grid.setOnClickListener(this);


    }

    /**
     * 设置分割线
     */
    private void addItemDecoration() {
        recyclerview.addItemDecoration(new MyItemDecoration(this, MyItemDecoration.VERTICAL_LIST));
    }

    private void setAdapter() {

        adapter = new RecyclerViewAdapter(this, datas);
        recyclerview.setAdapter(adapter);


        //设置布局管理器
        /**
         * 第一个参数：上下文
         * 第二参数：方向：竖直和水平
         * 第三个参数：是否倒序
         */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        /**
         *  第一个参数：上下文
         *  第二个参数：设置多少列
         * 第三参数：方向：竖直和水平
         * 第四个参数：是否倒序
         */
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        /**
         *
         *  第一个参数：设置多少列
         *  第二参数：方向：竖直和水平
         */
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerview.setLayoutManager(linearLayoutManager);


        //实现默认动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());


    }

    /**
     * 初始化数据
     */
    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("Content" + i);
        }


    }

    private void initView() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_grid = (Button) findViewById(R.id.btn_grid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                adapter.addData(0,"Newdata");
                break;
            case R.id.btn_delete:
                adapter.deletData(0);
                break;
            case R.id.btn_list:

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                recyclerview.setLayoutManager(linearLayoutManager);

                break;
            case R.id.btn_grid:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
                recyclerview.setLayoutManager(gridLayoutManager);
                break;


        }
    }
}
