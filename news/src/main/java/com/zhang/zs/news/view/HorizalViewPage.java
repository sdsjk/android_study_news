package com.zhang.zs.news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zhang.zs.news.leftMenu.TopicTabDetailBasePager;

/**
 * Created by zs on 2016/7/1.
 */
public class HorizalViewPage extends ViewPager {
    public HorizalViewPage(Context context, AttributeSet attrs) {
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

                float endX=ev.getX();
                float endY=ev.getY();

                float  distansX=endX-startX;
                float   distansY=endY-startY;

                if(Math.abs(distansY)>Math.abs(distansX)){
                        getParent().requestDisallowInterceptTouchEvent(false);
                }else{
                    if(getCurrentItem()==0&&distansX>0){
                     getParent().requestDisallowInterceptTouchEvent(false);
                    }else if(getCurrentItem()==getAdapter().getCount()-1&&distansX<0){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

                }


                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(ev);
    }


}
