package com.hb.hkm.hypebeaststore.life;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.asynhkm.productchecker.Util.Tool;
import com.hb.hkm.hypebeaststore.datamodel.V1.Product;
import com.hb.hkm.hypebeaststore.datamodel.V2.homepage.APPSettings;
import com.hb.hkm.hypebeaststore.datamodel.V2.homepage.elementHome;
import com.hb.hkm.hypebeaststore.datamodel.V2.wishlist.WishedItem;
import com.hb.hkm.hypebeaststore.datamodel.cookiesupport.HBCookieHandler;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.squareup.picasso.Picasso;

import java.net.CookieManager;
import java.net.CookiePolicy;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by hesk on 2/2/15.
 */
public class LifeCycleApp extends Application {
    private SharedPreferences SP;
    private String
            licenseKey = "",
            productKey = "",
            mac_id = "",
            login = "",
            pass = "";
    public static String STANDARD_KEY_COOKIE = "COOKIESTD";
/*
    private static final DefaultHttpClient client = createClient();

    public static DefaultHttpClient getClient() {
        return client;
    }

    private static DefaultHttpClient createClient() {
        BasicHttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
        schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        DefaultHttpClient httpclient = new DefaultHttpClient(cm, params);
        httpclient.getCookieStore().getCookies();
        return httpclient;
    }*/

    private static CookieManager cookieManager;

    public static HBCookieHandler getCookieStore() {
        return (HBCookieHandler) cookieManager.getCookieStore();
    }

    public static CookieManager getHBCookieMgr() {
        return cookieManager;
    }

    protected Picasso pic;
    protected Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Crash Reporting
        ParseCrashReporting.enable(this);
        // ENABLE PARSE IN HERE
        Parse.enableLocalDatastore(this);
        // Enable and initialize the parse application
        Parse.initialize(this, Config.PARSE_APPLICATION_ID, Config.PARSE_CLIENT_KEY);


        final HBCookieHandler sp = new HBCookieHandler(this);
        sp.setCookiePresistance(Config.wv.cookie_domain);
        cookieManager = new CookieManager(sp, CookiePolicy.ACCEPT_ALL);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        //CookieHandler.setDefault(cookieManager);
        pic = Picasso.with(this);
        SP = getSharedPreferences(Config.save_pref_private_id, Context.MODE_PRIVATE);
        mac_id = Tool.get_mac_address(this);
        login = SP.getString(Config.save_login, "");
        pass = SP.getString(Config.save_password, "");
        realm = Realm.getInstance(this);
        RealmGetData();
    }

    public Picasso getInstancePicasso() {
        return pic;
    }

    private void RealmGetData() {
      /*  realm.beginTransaction();
        RealmQuery<WishedItem> wishedItemRealmQuery = realm.where(WishedItem.class);
        RealmResults<WishedItem> wishedItemRealmResults = wishedItemRealmQuery.findAll();
        retent.WItems.clear();
        retent.WItems.addAll(wishedItemRealmResults);
        realm.commitTransaction();*/
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmQuery<WishedItem> wishedItemRealmQuery = realm.where(WishedItem.class);
                    RealmResults<WishedItem> wishedItemRealmResults = wishedItemRealmQuery.findAll();
                    retent.WItems.clear();
                    retent.WItems.addAll(wishedItemRealmResults);


                    RealmQuery<elementHome> elementHomeRealmQuery = realm.where(elementHome.class);
                    RealmResults<elementHome> elementHomeRealmResults = elementHomeRealmQuery.findAll();
                    retent.home_elements.clear();
                    retent.home_elements.addAll(elementHomeRealmResults);

                    APPSettings appSettingsItem = realm.where(APPSettings.class).findAll().last();
                    if (appSettingsItem != null)
                        retent.appSettings.setShopping_bag_current_item(appSettingsItem.getShopping_bag_current_item());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initParse() {
        /*ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(Config.PARSETAG, "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e(Config.PARSETAG, "failed to subscribe for push", e);
                }
            }
        });*/
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void store_token(final String token) {

    }

    public void read_stored_authen() {
        login = SP.getString(Config.save_login, "");
        pass = SP.getString(Config.save_password, "");
    }

    public void store_authenication(final String _login, final String _password) {
        SP.edit().putString(Config.save_login, _login)
                .putString(Config.save_password, _password).apply();
    }

    public void addNewProductToCart(final Product product) {
//        DataBank.my_cart.add
    }
}
