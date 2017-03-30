package com.zhang.zs.news.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhang.zs.news.R;
import com.zhang.zs.news.utils.Cache;
import com.zhang.zs.news.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2016/6/28.
 */
public class GuideActivity extends Activity {
    public static final String IS_FRIST = "isFrist";
    private ViewPager viewpage;
    private Button guide_btn;
    private LinearLayout guide_ll;
    private ImageView point_red;
    private int images[];
    private int widthleft;
    private List<ImageView> allImages = new ArrayList<>();
    private int distances;  //间距

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);
        viewpage = (ViewPager) findViewById(R.id.viewpage);
        guide_btn = (Button) findViewById(R.id.guide_btn);
        guide_ll = (LinearLayout) findViewById(R.id.guide_ll);
        point_red = (ImageView) findViewById(R.id.point_red);
        images = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        widthleft = DensityUtil.dip2px(this, 10);
        for (int i = 0; i < images.length; i++) {

            ImageView imageview = new ImageView(this);
            imageview.setBackgroundResource(images[0]);
            allImages.add(imageview);

            imageview = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthleft, widthleft);
            if (i != 0) {
                params.leftMargin = widthleft;
            }
            imageview.setLayoutParams(params);
            imageview.setBackgroundResource(R.drawable.point_nomal);
            guide_ll.addView(imageview);
            point_red.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        }


        viewpage.setAdapter(new MyPagerAdapter());
        viewpage.addOnPageChangeListener(new MyOnPageChangeListener());


        guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);

                //标识是不是第一次进入应用

                Cache.putBoolean(GuideActivity.this,IS_FRIST,true);

                finish();

            }
        });
    }


    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {


        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onGlobalLayout() {
            point_red.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            distances = guide_ll.getChildAt(1).getLeft() - guide_ll.getChildAt(0).getLeft();

        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            float movedistances = distances * positionOffset + position * distances;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthleft, widthleft);
            params.leftMargin = (int) movedistances;
            point_red.setLayoutParams(params);


        }

        @Override
        public void onPageSelected(int position) {
            if (position != images.length - 1) {
                guide_btn.setVisibility(View.GONE);
            } else {
                guide_btn.setVisibility(View.VISIBLE);

            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {

            return allImages.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = allImages.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
