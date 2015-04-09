package com.hb.hkm.hypebeaststore.components.webviewclients;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hb.hkm.hypebeaststore.CartWebView;
import com.hb.hkm.hypebeaststore.CartWebViewStyleTopBar;
import com.hb.hkm.hypebeaststore.ProductSingle;
import com.hb.hkm.hypebeaststore.StoreFrontHB;
import com.hb.hkm.hypebeaststore.StoreFrontV1;
import com.hb.hkm.hypebeaststore.fragments.singlecom.SingleProductApplication;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.LifeCycleApp;

import org.apache.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 3/5/15.
 */
public class HBCart extends WebViewClient {
    private String loginCookie;
    private final Context mContext;
    private boolean redirect = false;
    private boolean loadingFinished = true;
    private final WebView mWebView;
    public static String TAG = "hbcartclient";
    //store.hypebeast.com/cart
    private List<Cookie> sessionCookie;

    private final ArrayList<String> allowing = new ArrayList<>();
    private final ArrayList<String> startfrom = new ArrayList<>();

    public HBCart(final Context context, final WebView fmWebView) {
        super();
        mContext = context;
        mWebView = fmWebView;

        WebSettings settings = fmWebView.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + " " + Config.setting.wv_usergent);
        settings.setJavaScriptEnabled(true);
        // Log.d(TAG, settings.getUserAgentString());
        CookieManager.getInstance().setAcceptCookie(true);
        fmWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setSaveFormData(true);
        try {
            LifeCycleApp.getCookieStore().setWebKitCookieManagerSync(Config.wv.cookie_domain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fmWebView.loadUrl(Config.checkout_V1);
        allowing.add(Config.checkout_V1);
        allowing.add(Config.checkoutstep2);
        allowing.add(Config.checkoutstep3);
        /**
         * will be the product of page
         */
        startfrom.add("http://store.hypebeast.com/brands/");
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        //    Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
        //  Tool.trace();
        Log.d(TAG, "errorCode: " + errorCode);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
        if (!loadingFinished) {
            redirect = true;
        }
        Log.d(TAG, "client url:" + urlNewString);
        if (urlNewString.equalsIgnoreCase(Config.wv.domain_home)) {
            ((CartWebViewStyleTopBar) mContext).finish();
            return true;
        }

        for (final String url : allowing) {
            if (urlNewString.startsWith(url)) {
                loadingFinished = false;
                mWebView.loadUrl(urlNewString);
                return false;
            }
        }


        for (final String url : startfrom) {
            if (urlNewString.startsWith(url)) {


                Intent i = new Intent(mContext, ProductSingle.class);
                i.putExtra(SingleProductApplication.url_direct, urlNewString);
                mContext.startActivity(i);

                return true;
            }
        }
        return true;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        loadingFinished = false;
        //SHOW LOADING IF IT ISNT ALREADY VISIBLE
        super.onPageStarted(view, url, favicon);
        //  CustomLogger.showLog("Beta", "onPage Started url is" + url);

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (!redirect) {
            loadingFinished = true;
        }
        if (loadingFinished && !redirect) {
            //HIDE LOADING IT HAS FINISHED
        } else {
            redirect = false;
        }
        super.onPageFinished(view, url);

    }

}
