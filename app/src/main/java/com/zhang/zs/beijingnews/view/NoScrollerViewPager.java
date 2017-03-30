package com.zhang.zs.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zs on 2016/7/1.
 */
public class NoScrollerViewPager extends ViewPager {
    public NoScrollerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return  true;
    }

    /**
     * 交给子类试图处理
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return false;
    }
}
