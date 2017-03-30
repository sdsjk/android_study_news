package com.zhang.zs.beijingnews.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhang.zs.beijingnews.R;
import com.zhang.zs.beijingnews.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {
    private ViewPager guide_viewpage;
    private Button guide_btn;
    private LinearLayout guide_cyle;
    private int image[];
    private List<ImageView> allimage = new ArrayList<>();
    private ImageView cyle_red;
    private int cyleparams;
    private int leftMagin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        guide_viewpage = (ViewPager) findViewById(R.id.guide_viewpage);
        guide_btn = (Button) findViewById(R.id.guide_btn);
        guide_cyle = (LinearLayout) findViewById(R.id.guide_cyle);
        cyle_red = (ImageView) findViewById(R.id.cyle_red);  //红色小园
        cyleparams = DensityUtil.dip2px(this, 10);

        //设置是不是第一次进入应用标识 和判断是不是
        setAndGetFlag();


        //跳转到主页面
        guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                SharedPreferences sharedPreferences = getSharedPreferences("frist", MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("flag",false).commit();
                finish();

            }
        });
        //初始化图片数据
        image = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        for (int i = 0; i < image.length; i++) {
            ImageView imageview = new ImageView(this);
            imageview.setBackgroundResource(image[i]);
            allimage.add(imageview);
            //设置小园圈
            imageview = new ImageView(this);
            imageview.setBackgroundResource(R.drawable.cyle_nomal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cyleparams, cyleparams);
            if (i != 0) {
                params.leftMargin = cyleparams;
            }

            imageview.setLayoutParams(params);

            guide_cyle.addView(imageview);

        }

        //设置适配器
        guide_viewpage.setAdapter(new MyPagerAdapter());
        //得到圆点的间距
        cyle_red.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        guide_viewpage.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 判断是不是h第一次进入
     */
    private void setAndGetFlag() {
        SharedPreferences sharedPreferences = getSharedPreferences("frist", MODE_PRIVATE);
        boolean flag = sharedPreferences.getBoolean("flag", true);
        if(!flag){
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onGlobalLayout() {
        cyle_red.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        leftMagin = guide_cyle.getChildAt(1).getLeft() - guide_cyle.getChildAt(0).getLeft();
        Log.e("TAG", leftMagin + "");
    }
}

/**
 * viewPage设置监听
 */
class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

    /**
     * @param position             当前位置
     * @param positionOffset       页面滑动 的百分比
     * @param positionOffsetPixels 滑动 的像素
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        float distances = leftMagin * position + leftMagin * positionOffset;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cyleparams, cyleparams);
        params.leftMargin = (int) distances;

        cyle_red.setLayoutParams(params);
    }

    /**
     * 当页面被选中的时候
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        if (position == image.length - 1) {
            guide_btn.setVisibility(View.VISIBLE);
        } else {
            guide_btn.setVisibility(View.GONE);
        }

    }

    /**
     * 当状态被改变的时候
     *
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}


class MyPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageview = allimage.get(position);
        container.addView(imageview);
        return imageview;
    }
}
}
