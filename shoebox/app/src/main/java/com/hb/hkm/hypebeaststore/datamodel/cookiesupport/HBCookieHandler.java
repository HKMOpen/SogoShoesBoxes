package com.hb.hkm.hypebeaststore.datamodel.cookiesupport;

import android.content.Context;
import android.util.Log;

import com.hb.hkm.hypebeaststore.datamodel.V2.homepage.APPSettings;
import com.hb.hkm.hypebeaststore.endpointmanagers.AddCartManager;
import com.hb.hkm.hypebeaststore.life.retent;

import java.net.HttpCookie;
import java.net.URI;

import io.realm.Realm;

/**
 * Created by hesk on 1/4/15.
 */
public class HBCookieHandler extends PersistentCookieStore {
    public HBCookieHandler(Context ctx) {
        super(ctx);
    }

    protected void preprocesscookie(HttpCookie c, URI uri) {
        Log.d(AddCartManager.TAG, "SESSIONID:" + c.toString() + ", " + "URI ToString: " + uri.toString());
        String check = "_store_item_count=";
        if (c.toString().startsWith(check)) {

            Realm realm = Realm.getInstance(getContext());
            final String countStr = c.toString().substring(check.length(), c.toString().length()).toString();
            retent.appSettings.setShopping_bag_current_item(Integer.parseInt(countStr));
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    APPSettings appSettingsItem = realm.where(APPSettings.class).findAll().last();
                    if (appSettingsItem != null) {
                        realm.copyToRealm(retent.appSettings);
                    } else {
                        appSettingsItem.setShopping_bag_current_item(retent.appSettings.getShopping_bag_current_item());
                    }

                 /*
                    APPSettings user = realm.createObject(APPSettings.class);
                    user.setShopping_bag_current_item(Integer.parseInt(countStr));*/
                }
            });

        }
    }
}
