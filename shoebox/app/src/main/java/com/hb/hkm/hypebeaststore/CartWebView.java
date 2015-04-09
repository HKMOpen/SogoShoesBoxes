package com.hb.hkm.hypebeaststore;

import android.os.Bundle;
import android.webkit.WebView;

import com.hb.hkm.hypebeaststore.fragments.BasicSupportActionBarHKM;
import com.hb.hkm.hypebeaststore.components.webviewclients.ChromeLoader;
import com.hb.hkm.hypebeaststore.components.webviewclients.HBCart;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

/**
 * Created by hesk on 3/5/15.
 */
public class CartWebView extends BasicSupportActionBarHKM {

    //@DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.fragment_store_demo_webview);
        final WebView webview = (WebView) findViewById(R.id.store_webview);
        final CircleProgressBar cp = (CircleProgressBar) findViewById(R.id.progressBarcc);
        final HBCart hcart = new HBCart(this, webview);
        final ChromeLoader chromeLoader = new ChromeLoader(cp);
        chromeLoader.setShowHideWebViewOnload(false);
        webview.setWebViewClient(hcart);
        webview.setWebChromeClient(chromeLoader);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        //CookieSyncManager.getInstance().stopSync();
        super.onResume();
    }

    @Override
    public void onPause() {
        //  CookieSyncManager.getInstance().sync();
        super.onPause();
    }

}
