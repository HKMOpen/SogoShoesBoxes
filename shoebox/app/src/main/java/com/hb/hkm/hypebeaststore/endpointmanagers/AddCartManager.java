package com.hb.hkm.hypebeaststore.endpointmanagers;

import android.content.Context;
import android.util.Log;

import com.hb.hkm.hypebeaststore.datamodel.cookiesupport.HBCookieHandler;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.LifeCycleApp;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpCookie;

/**
 * Created by hesk on 2/6/15.
 */
public class AddCartManager extends asyclient {
    public static String TAG = "add to the cart";
    protected int variant, quantity;

    public AddCartManager(Context ccc, callback cb) {
        super(ccc, cb);
    }

    @Override
    protected void configOkClient(OkHttpClient c) {
        c.setCookieHandler(LifeCycleApp.getHBCookieMgr());
    }

    // String cookieStr = sessionCookie.getName()+"="+sessionCookie.getValue();
    @Override
    protected void addHeaderParam(Request.Builder c) {
        try {
            HttpCookie hc = LifeCycleApp.getCookieStore().getCookies().get(0);
            c.header("Cookie", HBCookieHandler.cookieValBuilder(hc));
        } catch (Exception e) {
            Log.d(TAG, "cookie is not found then start new request");
        }
    }

    public void setItem(final int variant_id, final int q) throws Exception {
        variant = variant_id;
        quantity = q;
        setURL(String.format(Config.addtocart, variant_id + "", q + ""));
        Log.d(TAG, "variant ID:" + variant_id);
    }

    @Override
    protected void GSONParser(String data) throws JSONException {
        Log.d(TAG, data);

        JSONObject json = new JSONObject(data);
        if (json.has("error")) {
            setError(json.getString("error"));
        } else if (json.has("message")) {
            setError(json.getString("message"));
        }

    }

    @Override
    protected void ViewConstruction() {

    }
}
