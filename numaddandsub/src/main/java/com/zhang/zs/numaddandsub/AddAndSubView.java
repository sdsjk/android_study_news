package com.zhang.zs.numaddandsub;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zs on 2016/7/10.
 */
public class AddAndSubView extends LinearLayout implements View.OnClickListener {
    /**
     * 显示的组件
     *
     * @param context
     */

    private Button btn_add;

    private Button btn_sub;

    private TextView textview;

    private LinearLayout  linear;

    /**
     * 当前的textview显示的值
     *
     * @param context
     */

    private int currentNum = 1;


    private int maxValue = 10;

    private int minValue = 1;

    public int getCurrentNum() {
   String values= textview.getText().toString();

        if(!"".equals(values)&&values!=null){
            currentNum=Integer.parseInt(values);
        }
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        textview.setText(currentNum+"");
        this.currentNum = currentNum;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }





    public AddAndSubView(Context context) {
        this(context, null);
    }

    public AddAndSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public AddAndSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.num_add_sub, this);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_sub = (Button) findViewById(R.id.btn_sub);
        textview = (TextView) findViewById(R.id.textview);
        linear = (LinearLayout)findViewById(R.id.liner);

        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);

        getCurrentNum();

        //得到属性

        if(attrs!=null){

            TintTypedArray a=TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.AddAndSubView);

            int current = a.getInt(R.styleable.AddAndSubView_current, 0);
            if(current>0){
                setCurrentNum(current);
            }

            int minValue=a.getInt(R.styleable.AddAndSubView_minValue, 1);
            if(minValue>0){
                setMinValue(minValue);
            }

            int maxValue=a.getInt(R.styleable.AddAndSubView_maxValue, 2);
             if(maxValue>0){
                 setMaxValue(maxValue);
             }
            Drawable dra=a.getDrawable(R.styleable.AddAndSubView_numberAddSubViewBackground);
            if(dra!=null){

                linear.setBackground(dra);
            }

            Drawable btnadd=a.getDrawable(R.styleable.AddAndSubView_btnAddBackgroud);
            if(btnadd!=null){

                btn_add.setBackground(btnadd);
            }

            Drawable btnsub=a.getDrawable(R.styleable.AddAndSubView_btnSubBackgroud);
            if(btnsub!=null){

                btn_sub.setBackground(btnsub);
            }




        }






    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if (currentNum < maxValue) {

                    currentNum++;
                }

                textview.setText(currentNum + "");
                if (onButtonClickListener != null) {
                    onButtonClickListener.onAddNumberClick(v, currentNum);
                }

                break;
            case R.id.btn_sub:

                if (currentNum > minValue) {

                    currentNum--;
                }
                textview.setText(currentNum + "");

                if (onButtonClickListener != null) {
                    onButtonClickListener.onSubNumberClick(v, currentNum);
                }
                break;
        }
    }


    public interface OnButtonClickListener {

        /**
         * 当点击减按钮的时候回调这个方法
         *
         * @param view
         * @param value
         */
        public void onSubNumberClick(View view, int value);

        /**
         * 当点击加按钮的时候回调这个方法
         *
         * @param view
         * @param value
         */
        public void onAddNumberClick(View view, int value);

    }


    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
}
