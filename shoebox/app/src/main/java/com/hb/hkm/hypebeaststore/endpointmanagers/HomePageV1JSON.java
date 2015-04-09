package com.hb.hkm.hypebeaststore.endpointmanagers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.hb.hkm.hypebeaststore.datamodel.V2.homepage.elementHome;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;

import org.json.JSONException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;

/**
 * Created by hesk on 3/10/15.
 */
public class HomePageV1JSON extends asyclient {
    public static String TAG = "HomePageV1Render";

    public HomePageV1JSON(Context ccc, callback cb) {
        super(ccc, cb);
        try {
            setURL(Config.home_v1);
        } catch (Exception e) {
        }
    }

    @Override
    protected void GSONParser(String data) throws JSONException, JsonSyntaxException, JsonIOException, JsonParseException {
        Log.d(TAG, data);
        Log.d(TAG, "success curl: " + url);
        if (retent.home_elements.size() == 0) {
            // final GsonBuilder gb = new GsonBuilder();
            //  final Gson g = gb.create();
            final JsonReader reader = new JsonReader(new StringReader(data.trim()));
            reader.setLenient(true);
            final TypeToken<List<elementHome>> typ = new TypeToken<List<elementHome>>() {
            };
            final Collection<elementHome> elementHomes = getGB().create().fromJson(reader, typ.getType());
            // Open a transaction to store items into the realm
            // Use copyToRealm() to convert the objects into proper RealmObjects managed by Realm.
           Realm.getInstance(ctx).executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Collection<elementHome> elementHomeCollection = realm.copyToRealm(elementHomes);
                    retent.home_elements.clear();
                    retent.home_elements.addAll(elementHomes);

                   // realm.commitTransaction();
                }
            });


        }
    }

    @Override
    protected void ViewConstruction() {

    }
}
