package com.robert.tool.view.webview;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.robert.tool.R;
import com.robert.tool.base.BaseActivity;
import com.robert.tool.constant.ArouterPathConstant;
import com.robert.tool.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author bobo
 * <p>
 * function：JS与android交互
 * <p>
 * create_time：2018/9/10 15:09
 * update_by：
 * update_time:
 */
@Route(path = ArouterPathConstant.PATH_TO_WEBVIEW)
public class JSWebViewActivity extends BaseActivity {


    WebView webView;

    @Override
    protected int layoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void setViews() {
        webView = findViewById(R.id.web_view);
        setTitle(getResources().getString(R.string.webview));

        initWebView();
    }

    private void initWebView() {
        webViewSetting();
        try {
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }

                @Override
                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                    super.onReceivedHttpError(view, request, errorResponse);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

        webView.setWebChromeClient(new WebChromeClient(){
            // 允许js Alert弹窗
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        webView.addJavascriptInterface(this, "demo"); //传入JS页面对象
        webView.loadUrl("https://github.com/rebertBen"); // 加载web页
    }

    private void webViewSetting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 为webView使能JavaScript
        webSettings.setDomStorageEnabled(true); // 设置webview可以缓存数据
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        webSettings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @JavascriptInterface
    public void android2Js(){
        ToastUtil.showShort(this, "js调用android方法");
    }

    /**
     * android调用js方法, skipToBefore()为js中的方法
     */
    private void js2Android(){
        webView.evaluateJavascript("javascript:skipToBefore()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                ToastUtil.showShort(JSWebViewActivity.this, "Android调用js方法");
            }
        });
    }

}
