package com.zhang.zs.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.zhang.zs.news.activity.GestureVerifyActivity;
import com.zhang.zs.news.activity.GuideActivity;
import com.zhang.zs.news.utils.Cache;

/**
 * Created by zs on 2016/6/28.
 */
public class Welcome extends Activity {
    private RelativeLayout welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        welcome = (RelativeLayout) findViewById(R.id.welcome);

        AlphaAnimation aa = new AlphaAnimation(0, 1);

        aa.setDuration(2000);
        aa.setFillAfter(true);

        RotateAnimation rr = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rr.setDuration(2000);
        rr.setFillAfter(true);

        ScaleAnimation ss = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ss.setDuration(2000);
        ss.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);

        set.addAnimation(aa);
        set.addAnimation(rr);
        set.addAnimation(ss);

        this.welcome.setAnimation(set);

        set.setAnimationListener(new MyAnimationListener());

    }




    class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            boolean flag = Cache.getBoolean(Welcome.this, GuideActivity.IS_FRIST);
            if (flag) {
                Intent intent = new Intent(Welcome.this, GestureVerifyActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(Welcome.this, GuideActivity.class);
                startActivity(intent);
                finish();
            }


        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
