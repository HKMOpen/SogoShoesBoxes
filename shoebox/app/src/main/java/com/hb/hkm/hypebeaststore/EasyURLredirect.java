package com.hb.hkm.hypebeaststore;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hb.hkm.hypebeaststore.fragments.BasicSupportActionBarHKM;
import com.hb.hkm.hypebeaststore.life.Config;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * Created by hesk on 3/10/15.
 */
public class EasyURLredirect extends BasicSupportActionBarHKM {

    public static String INTENT_BUNDLE_URL = "keyurl";
    public static String INTENT_BUNDLE_TITLE = "keytitle";

    private SlidrInterface slidrInf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.fragment_store_demo_webview);
        final WebView webview = (WebView) findViewById(R.id.store_webview);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setUserAgentString(Config.setting.useragent);
        final CircleProgressBar cp = (CircleProgressBar) findViewById(R.id.progressBarcc);
        // webview.reload();
        cp.setCircleBackgroundEnabled(false);
        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(getResources().getColor(R.color.primary_pref))
                .secondaryColor(getResources().getColor(R.color.primary_pref_v2))
                .position(SlidrPosition.RIGHT)
                .touchSize(100)
                .sensitivity(0.1f)
                .build();


        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            cp.setVisibility(View.GONE);


            webview.loadUrl(b.getString(INTENT_BUNDLE_URL, ""));
            setTitle(b.getString(INTENT_BUNDLE_TITLE, ""));
        }


        slidrInf = Slidr.attach(this, config);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_small, R.anim.abc_slide_out_bottom);
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
