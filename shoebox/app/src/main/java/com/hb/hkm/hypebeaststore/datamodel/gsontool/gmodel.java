package com.hb.hkm.hypebeaststore.datamodel.gsontool;

/**
 * Created by hesk on 2/3/15.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by hesk on 1/24/2015.
 */
public abstract class gmodel {

    public String toJson() {
        final GsonBuilder gsonb = new GsonBuilder();
        String request_body = "";
        Gson gson = gsonb.create();
        request_body = gson.toJson(this);
        return request_body;
    }

    public abstract boolean checkComplete();
}
