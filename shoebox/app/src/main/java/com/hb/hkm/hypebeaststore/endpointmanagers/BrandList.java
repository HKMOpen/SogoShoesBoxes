package com.hb.hkm.hypebeaststore.endpointmanagers;

import android.content.Context;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.hb.hkm.hypebeaststore.datamodel.V2.brandlist.wrapper;
import com.hb.hkm.hypebeaststore.fragments.brandpage.SortSimpleAdapterData;
import com.hb.hkm.hypebeaststore.life.retent;

import org.json.JSONException;

import java.io.StringReader;

/**
 * Created by hesk on 3/17/15.
 */
public class BrandList extends asyclient {
    public BrandList(Context ccc, callback cb, String url) {
        super(ccc, cb);
        try {
            setURL(url);
        } catch (Exception e) {

        }
    }

    @Override
    protected void GSONParser(String data) throws JSONException, JsonSyntaxException, JsonIOException, JsonParseException {
        final JsonReader reader = new JsonReader(new StringReader(data.trim()));
        reader.setLenient(true);
        final wrapper outpro = getGson().fromJson(data, wrapper.class);
        retent.brand_list.clear();
        retent.brand_list.addAll(outpro.getBrands());
    }

    @Override
    protected void ViewConstruction() {

    }
}
