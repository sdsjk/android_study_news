package com.zhang.zs.news.utils;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhang.zs.news.bean.ShopBean;
import com.zhang.zs.news.bean.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2016/7/11.
 */
public class CartProvider {

    public static final String CART_JSON = "cart_json";
    private final Context context;

    /**
     * 相遇hashMap 但是要比hashmaop的效率要高
     */
    private SparseArray<ShoppingCart> datas;


    public CartProvider(Context context) {
        this.context = context;
        datas = new SparseArray<>(10);

        //获取数据;
        listToSparse();


    }

    //从本地获取视频
    public void listToSparse() {

        List<ShoppingCart> carts = getAllData();

        //转换成SparseArray
        if (carts != null && carts.size() > 0) {
            for (int i = 0; i < carts.size(); i++) {

                datas.put(carts.get(i).getId(), carts.get(i));

            }

        }


    }


    public List<ShoppingCart> getAllData() {

        return getDataFromlocal();
    }

    /**
     * 获取本地数据转换成集合数据
     *
     * @return
     */
    public List<ShoppingCart> getDataFromlocal() {
        List<ShoppingCart> carts = new ArrayList<>();

        String json = Cache.getString(context, CART_JSON);

        if (!"".equals(json) && json != null) {
            carts = new Gson().fromJson(json, new TypeToken<List<ShoppingCart>>() {
            }.getType());

        }

        return carts;
    }


    /**
     * 添加数据
     *
     * @param cart
     */
    public void addCarts(ShoppingCart cart) {
        //在内存中存添加数据
        datas.put(cart.getId(), cart);

        //更新到缓存中
       commit();

    }


    public void deleteCarts(ShoppingCart cart) {
        //删除数据
        datas.remove(cart.getId());

        //更新状态

        commit();
    }


    public void upCarts(ShoppingCart cart) {

        ShoppingCart cart1 = datas.get(cart.getId());

        if (cart1 != null) {

            cart1.setCount(cart1.getCount() + 1);
            Log.e("TAG","----个数---"+cart1.getCount());

        } else {
            cart1 = cart;
            cart1.setCount(1);


        }
        datas.put(cart1.getId(), cart1);

        //修改状态
        commit();




    }

    private void commit() {
      //转换成一list集合
        List<ShoppingCart> list=sparseToList();
        //转换成  json

        String json=new Gson().toJson(list);

        Cache.putString(context, CART_JSON, json);
    }

    private List<ShoppingCart> sparseToList() {
        List<ShoppingCart>  list=new ArrayList<>();

        if(datas!=null&& datas.size()>0){

            for(int i=0;i<datas.size();i++){

                list.add(datas.valueAt(i));
            }
        }


        return list;
    }


    public ShoppingCart conversionData(ShopBean.ListBean listBean) {

        ShoppingCart cart=new ShoppingCart();

        cart.setDescription(listBean.getDescription());
        cart.setId(listBean.getId());
        cart.setImgUrl(listBean.getImgUrl());
        cart.setName(listBean.getName());
        cart.setPrice(listBean.getPrice());
        cart.setSale(listBean.getSale());
        cart.setCount(1);
        cart.setIsChecked(true);
        return cart;
    }
}
