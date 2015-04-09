package com.hb.hkm.hypebeaststore.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.LifeCycleApp;
import com.hb.hkm.hypebeaststore.components.webviewclients.HBClient;

/**
 * Created by hesk on 2/2/15.
 */
public class HBWebview extends Fragment {

    public HBWebview() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.fragment_store_demo_webview, container, false);
        return rv;
    }


    private WebView wv;

    private void view_settings(WebView view, LifeCycleApp control) {
        // token_cookie = control.get_web_cookie();
        /*

        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(getActivity());
        cookieSyncManager.startSync();
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieSyncManager.sync();

        */
        // Enable Javascript
        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        wv = (WebView) view.findViewById(R.id.store_webview);
        view_settings(wv, (LifeCycleApp) getActivity().getApplication());
        wv.setWebViewClient(new HBClient(getActivity(), wv));
        wv.setWebChromeClient(new WebChromeClient());
        //wv.addJavascriptInterface(new JSInterfaceSupport(this, ac), "onecall_data_support");
        wv.loadUrl(Config.wv.domain_start);
    }

}
