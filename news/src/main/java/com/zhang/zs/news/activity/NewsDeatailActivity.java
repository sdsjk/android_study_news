package com.zhang.zs.news.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhang.zs.news.R;
import com.zhang.zs.news.utils.UrlUtils;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by zs on 2016/7/3.
 */
public class NewsDeatailActivity extends Activity implements View.OnClickListener {
    private WebView webView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newsdetail);

        findViewById(R.id.ib_back).setVisibility(View.VISIBLE);
        findViewById(R.id.text_textsize).setVisibility(View.VISIBLE);
        findViewById(R.id.text_text_share).setVisibility(View.VISIBLE);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        webView = (WebView) findViewById(R.id.webview);
        Log.e("TAG", getIntent().getStringExtra("url") + "-------");
        webView.getSettings().setJavaScriptEnabled(true);
        String url= UrlUtils.url+getIntent().getStringExtra("url");
        webView.loadUrl(url);
        findViewById(R.id.ib_back).setOnClickListener(this);
        findViewById(R.id.text_textsize).setOnClickListener(this);
        findViewById(R.id.text_text_share).setOnClickListener(this);
        webView.setWebViewClient(new MyWebViewClient());


    }

    /**
     * 当页面加载完之后执行的方法
     */
    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            //当页面加载完成之后执行
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.text_textsize:
                Toast.makeText(this, "字体大小", Toast.LENGTH_SHORT).show();
                //修改字体
                changeTextSize();
                break;
            case R.id.text_text_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                showShare();
                break;

        }
    }

    private String items[] = {
            "超大号字体",
            "大号字体",
            "默认字体",
            "小号字体",
            "超小号字体"
    };

    int postion;
    int realpostion = 2;

    private void changeTextSize() {
        new AlertDialog.Builder(this)
                .setTitle("修改字体")
                .setSingleChoiceItems(items, realpostion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postion = which;

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                           修改字体
                        realpostion = postion;
                        setTextSize();
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }




    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

  // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("我正在和美女没聊天.......");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.qiushibaike.com/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("糗事百科");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.qiushibaike.com/");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("糗事百科１");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    private void setTextSize() {
        WebSettings settings = webView.getSettings();
        switch (realpostion) {
            case 0:
                settings.setTextZoom(200);
                break;
            case 1:
                settings.setTextZoom(150);
                break;
            case 2:
                settings.setTextZoom(100);
                break;
            case 3:
                settings.setTextZoom(75);
                break;
            case 4:
                settings.setTextZoom(50);
                break;
        }

    }
}
