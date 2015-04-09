package com.hb.hkm.hypebeaststore.endpointmanagers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.wrap_product;
import com.hb.hkm.hypebeaststore.life.retent;

import java.io.StringReader;

/**
 * Created by hesk on 2/5/15.
 */
public class SingleBuilder extends asyclient {
    public static String TAG = "SingleBuilder";

    public SingleBuilder(Context ccc, callback cb) {
        super(ccc, cb);
    }

    @Override
    protected void GSONParser(String data) {
        Log.d(TAG, data);
        Log.d(TAG, "success curl: " + url);
        final GsonBuilder gb = new GsonBuilder();
        //gb.registerTypeAdapterFactory(new GTool.NullStringToEmptyAdapterFactory());
        final Gson g = gb.create();
        final JsonReader reader = new JsonReader(new StringReader(data.trim()));
        reader.setLenient(true);
        // wrap_product o = g.fromJson(reader, wrap_product.class);
        wrap_product o = g.fromJson(reader, wrap_product.class);
        Log.d(TAG, o.toString());
        // DataBank.act_product_single = o.getp();
        retent.product_single2 = o.getp();
    }


    @Override
    protected void ViewConstruction() {

    }
}
