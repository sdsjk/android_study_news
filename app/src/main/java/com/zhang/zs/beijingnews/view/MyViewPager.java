package com.zhang.zs.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zs on 2016/7/1.
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private float startX;
    private float startY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //2.来到新的坐标
                float endX = ev.getX();
                float endY = ev.getY();

                //2.计算滑动的距离
                float distanceX = endX - startX;
                float distanceY = endY - startY;


                if(Math.abs(distanceX) >Math.abs(distanceY) ){
                    //水平方向滑动
                    //2.1：从左到右滑动，并且是第0个页面，不要求禁用父类拦截方法
                    //getParent().requestDisallowInterceptTouchEvent(false);
                    if(distanceX >0 && getCurrentItem() == 0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }

                    //2.2 从右到左滑动，并且是最后一个页面，不要求禁用拦截方法
                    //getParent().requestDisallowInterceptTouchEvent(false);
                    else if(distanceX < 0 && getCurrentItem() == getAdapter().getCount()-1){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }

                    //2.3 中间部分
                    //getParent().requestDisallowInterceptTouchEvent(true);
                    else{
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else{
                    //竖直方向滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }


                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(ev);
    }
}
