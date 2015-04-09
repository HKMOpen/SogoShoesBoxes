package com.hb.hkm.hypebeaststore.endpointmanagers;

import android.content.Context;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.hb.hkm.hypebeaststore.life.Config;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;

/**
 * Created by hesk on 3/24/15.
 */
public class loginManager extends asyclient {
    public int request_method;
    private RequestBody rb;
    public static int LOGIN = 1, REG = 2;

    public loginManager(Context ccc, callback cb) {
        super(ccc, cb);


    }

    @Override
    protected void addHeaderParam(Request.Builder request) {
        request.post(rb);
    }


    public loginManager setToLogin(String user, String pass) {
        try {
            request_method = LOGIN;
            setURL(Config.login_endpoint);
            RequestBody formBody = new FormEncodingBuilder()
                    .add("_username", user)
                    .add("_password", pass)
                    .build();

            rb = formBody;
        } catch (Exception e) {

        }
        return this;
    }

    public loginManager setToRegister(String reg_email, String pass) {
        try {
            request_method = REG;
            setURL(Config.register_endpoint);
            RequestBody formBody = new FormEncodingBuilder()
                    .add("email", reg_email)
                    .add("plainPassword", pass)
                    .build();

            rb = formBody;
        } catch (Exception e) {

        }
        return this;
    }

    @Override
    protected void GSONParser(String data) throws JSONException, JsonSyntaxException, JsonIOException, JsonParseException {

    }

    @Override
    protected void ViewConstruction() {

    }
}
