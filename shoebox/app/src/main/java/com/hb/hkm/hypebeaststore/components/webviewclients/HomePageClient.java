package com.hb.hkm.hypebeaststore.components.webviewclients;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.asynhkm.productchecker.Util.Tool;
import com.hb.hkm.hypebeaststore.StoreFrontV1;
import com.hb.hkm.hypebeaststore.endpointmanagers.networkBrowserClient;
import com.hb.hkm.hypebeaststore.goldengate.clientWebInterface;
import com.hb.hkm.hypebeaststore.life.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by hesk on 2/25/15.
 */
public class HomePageClient extends WebViewClient implements networkBrowserClient.callback {
    private static final String TAG = "HomePageClient";
    private final WebView mWebView;
    private boolean loadingFinished = true;
    private boolean redirect = false;
    private static final String showHTMLHack = "javascript:window.HtmlViewer.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');";
    private static final String blockr_downloadapp = "https://m.facebook.com/click.php?redir_url=market%3A%2F%2Fdetails%3Fid%3Dcom.facebook.katana&app_id=350685531728&cref=mb&no_fw=1&refid=9";
    private static final String blockr_helper = "https://m.facebook.com/help/?refid=9";
    private static final String blockr_createnewaccount = "https://m.facebook.com/r.php?loc=bottom";
    private Context ctx;

    @SuppressLint("AddJavascriptInterface")
    public HomePageClient(final WebView webunit) {
        ctx = webunit.getContext();
        webunit.getSettings().setUserAgentString(Config.setting.useragent);
        webunit.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webunit.getSettings().setJavaScriptEnabled(true);
        webunit.addJavascriptInterface(new clientWebInterface(ctx), "HtmlViewer");
        webunit.addJavascriptInterface(new com.hb.hkm.hypebeaststore.goldengate.Log(ctx), "Log");
        mWebView = webunit;
    }

    public String convertToString(InputStream inputStream) {
        final StringBuffer string = new StringBuffer();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                string.append(line + "\n");
            }
        } catch (IOException e) {
        }
        return string.toString();
    }



    /*
    protected void getURL(final String urlConection) {


        HttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = entity.getContent();
                    htmlContent = convertToString(inputStream);
                }
            }
        } catch (Exception e) {
        }

        mWebView.loadData(htmlContent, "text/html", "utf-8");
    }*/

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        //    Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
        //  Tool.trace();
        Log.d(TAG, "errorCode: " + errorCode);
    }

    private boolean urlcheck(final String t) {
        for (String tt : Config.wv.categories) {
            String h = "categories/" + tt;
            if (t.contains(h)) {
                return true;
            }
        }

        for (String tt : Config.wv.promotions) {
            String h = tt;
            if (t.contains(h)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {

        try {
            URL urlObj = new URL(urlNewString);
           /* if (TextUtils.equals(urlObj.getHost(), "192.168.1.34")) {

                if (!loadingFinished) {
                    redirect = true;
                } else loadingFinished = false;

                return false;
            } else {
                boolean t = false;
                if (urlNewString.equalsIgnoreCase(blockr_downloadapp))
                    t = true;
                if (urlNewString.equalsIgnoreCase(blockr_helper))
                    t = true;
                if (urlNewString.equalsIgnoreCase(blockr_createnewaccount))
                    t = true;


                Log.d(TAG, "shouldOverride result: " + t);
                return t;
            }*/

            Log.d(TAG, "shouldOverrideUrlLoading: " + urlNewString);
            if (urlNewString.equalsIgnoreCase(Config.wv.domain_start)) {
                mWebView.loadUrl(urlNewString);
                return false;
            }
            boolean t = urlcheck(urlNewString);
            Log.d(TAG, "shouldOverride result: " + t);
            if (t) {
                Intent intent = new Intent(mWebView.getContext(), StoreFrontV1.class);
                // intent.setData(Uri.parse(url));
                final Bundle f = new Bundle();
                f.putString("uri", urlNewString);
                intent.putExtras(f);
                mWebView.getContext().startActivity(intent);
                //Tell the WebView you took care of it.
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        loadingFinished = false;
        //SHOW LOADING IF IT ISNT ALREADY VISIBLE
        // Log.d(TAG, view.getH)
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
        //mWebView.loadUrl(showHTMLHack);


    }

    //networkBrowserClient.callback
    @Override
    public void onSuccess(String data) {
        mWebView.loadData(data, "text/html", "utf-8");
        loadingFinished = true;
        Tool.trace(mWebView.getContext(), "url request data complete: " + data);
        Log.d(TAG, data);
    }

    @Override
    public void onFailure(String message) {
        Tool.trace(mWebView.getContext(), "error from server: " + message);
        Log.e(TAG, "error from server: " + message);
        loadingFinished = false;
    }

    @Override
    public void beforeStart(networkBrowserClient task) {
        Tool.trace(mWebView.getContext(), "get url status start async! now");
    }
}
