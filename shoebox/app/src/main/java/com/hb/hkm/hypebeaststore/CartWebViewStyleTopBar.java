package com.hb.hkm.hypebeaststore;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hb.hkm.hypebeaststore.components.webviewclients.ChromeLoader;
import com.hb.hkm.hypebeaststore.components.webviewclients.HBCart;
import com.hb.hkm.hypebeaststore.fragments.BasicSupportActionBarHKM;

/**
 * Created by hesk on 31/3/15.
 */
public class CartWebViewStyleTopBar extends BasicSupportActionBarHKM {
    protected ProgressBar creatProgressBar() {
        // create new ProgressBar and style it
        final ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 24));
        //progressBar.setProgress(65);

// retrieve the top view of our application
        final FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        decorView.addView(progressBar);

        ViewTreeObserver observer = progressBar.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float density = CartWebViewStyleTopBar.this.getResources().getDisplayMetrics().density;
                View contentView = decorView.findViewById(android.R.id.content);
                progressBar.setY(contentView.getY() + 20 * density);

                ViewTreeObserver observer = progressBar.getViewTreeObserver();
                observer.removeGlobalOnLayoutListener(this);
            }
        });
        return progressBar;
    }

    //@DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ProgressBar progressBar = creatProgressBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_store_top_bar);
        final WebView webview = (WebView) findViewById(R.id.store_webview);
        final HBCart hcart = new HBCart(this, webview);
        final ChromeLoader chromeLoader = new ChromeLoader(this, progressBar).setShowHideWebViewOnload(false);
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

    @Override
    protected int getMenuId() {
        return R.menu.cart;
    }
}
