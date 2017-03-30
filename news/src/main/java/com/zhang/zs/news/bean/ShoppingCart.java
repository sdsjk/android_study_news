package com.zhang.zs.news.bean;

/**
 * Created by zs on 2016/7/11.
 */
public class ShoppingCart extends ShopBean.ListBean {

    private int count ;

    private boolean  isChecked;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "count=" + count +
                ", isChecked=" + isChecked +
                '}';
    }
}
