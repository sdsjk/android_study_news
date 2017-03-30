package com.zhang.zs.beijingnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.zhang.zs.beijingnews.activity.GuideActivity;

public class LunchActivity extends Activity {
private RelativeLayout rl_lunch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        rl_lunch = (RelativeLayout)findViewById(R.id.rl_lunch);

        //设置动画
        //旋转动画
        RotateAnimation ro=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ro.setFillAfter(true);
        ro.setDuration(2000);
        //缩放动画
        ScaleAnimation sa=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        sa.setFillAfter(true);
        sa.setDuration(2000);
        //渐变动画
        AlphaAnimation al=new AlphaAnimation(0,1);
        al.setFillAfter(true);
        al.setDuration(2000);
        //添加集合动画当中

        AnimationSet animationSet=new AnimationSet(false);
        animationSet.addAnimation(ro);
        animationSet.addAnimation(sa);
        animationSet.addAnimation(al);
        rl_lunch.setAnimation(animationSet);
        //设置动画的监听

        animationSet.setAnimationListener(new MyAnimationListener());

    }
class  MyAnimationListener implements Animation.AnimationListener {

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
    //跳转到导航页面
        Intent intent=new Intent(LunchActivity.this,GuideActivity.class);
        LunchActivity.this.startActivity(intent);

        LunchActivity.this.finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

}
