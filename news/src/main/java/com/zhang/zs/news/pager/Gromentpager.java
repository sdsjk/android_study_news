package com.zhang.zs.news.pager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zhang.zs.news.R;
import com.zhang.zs.news.adapter.CartAdapter;
import com.zhang.zs.news.base.Basepage;
import com.zhang.zs.news.bean.ShoppingCart;
import com.zhang.zs.news.utils.CartProvider;

import java.util.List;

/**
 * Created by zs on 2016/6/28.
 */
public class Gromentpager extends Basepage {

    private RecyclerView recyclerView;
    private CheckBox checkbox;
    private TextView prices;
    private Button order;
    private Button delete;

    private CartProvider cartProvider;

    List<ShoppingCart> allData;

    private CartAdapter adapter;


    /**
     * 编辑状态
     */
    public static final int ACTION_EDIT = 1;
    /*
      完成状态
     */
    public static final int ACTION_COMPLETE = 2;


    public Gromentpager(Context context) {
        super(context);
        cartProvider = new CartProvider(context);
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "政要被初始化了.........");
        tv_title.setText("购物车");
        edit.setVisibility(View.VISIBLE);
        View view = View.inflate(context, R.layout.govaffair_pager, null);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        prices = (TextView) view.findViewById(R.id.prices);
        order = (Button) view.findViewById(R.id.order);
        delete = (Button) view.findViewById(R.id.delete);

        base_content.removeAllViews();
        base_content.addView(view);

        edit.setTag(ACTION_EDIT);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int action = (int) edit.getTag();

                if (action == ACTION_EDIT) {

                    showDeleteControl();

                } else if (action == ACTION_COMPLETE) {

                    ideDeleteControl();
                }


            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //删除购物车里边的数据

                adapter.deleteItem();


            }
        });

        showData();
    }

    private void ideDeleteControl() {

        //按钮状态设置为完成
        edit.setText("编辑");
        //总价格隐藏
        prices.setVisibility(View.VISIBLE);
        //全选按钮设置非勾选
        checkbox.setChecked(true);
        //删除按钮显示
        delete.setVisibility(View.GONE);
        //隐藏去结算按钮
        order.setVisibility(View.VISIBLE);
        //把所有的设置为非勾选
        adapter.all_none(true);
        //设置tag

        //设置tag
        edit.setTag(ACTION_EDIT);



    }

    private void showDeleteControl() {
        //按钮状态设置为完成
        //按钮状态设置为完成
        edit.setText("完成");
        //总价格隐藏
        prices.setVisibility(View.GONE);
        //全选按钮设置非勾选
        checkbox.setChecked(false);
        //删除按钮显示
        delete.setVisibility(View.VISIBLE);
        //隐藏去结算按钮
        order.setVisibility(View.GONE);
        //把所有的设置为非勾选
        adapter.all_none(false);
        //设置tag

        //设置tag
        edit.setTag(ACTION_COMPLETE);


    }

    private void showData() {

        allData = cartProvider.getAllData();

        adapter = new CartAdapter(context, allData, checkbox, prices);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

    }
}
