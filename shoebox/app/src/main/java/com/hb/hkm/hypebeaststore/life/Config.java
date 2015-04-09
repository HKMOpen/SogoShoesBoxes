package com.hb.hkm.hypebeaststore.life;

import android.os.Build;

/**
 * Android App settings
 * Created by hesk on 2/2/15.
 */
public class Config {

    public static class wv {

        public static final String hb_login = "http://hypebeast.com/login/facebook?return_format=json&target_path=http://store.hypebeast.com";
        public static final String domain_start = "https://store.hypebeast.com/";
        public static final String domain_home = "http://store.hypebeast.com/";
        public static final String domain = "http://hypebeast.com/";
        //this is the rule set for cookies read and write. DO NOT EDIT THIS
        public static final String cookie_domain = "https://.hypebeast.com";
        public static final String brands = "brands";
        public static String single_request_route = domain_start + brands + "/";

        public static String brand_list = domain_start + "brands";
        public static String by_brand = "?category=%s";
        public final static String[] categories = new String[]{
                "grooming",
                "home-tech",
                "shoes",
                "accessories",
                "clothing",
                "gift-cards",
                "print",
                "sale"
        };

        public final static String[] promotions = new String[]{
                "new-arrivals",
                "special",
                "accessories",
                "clothing",
                "gift-cards",
                "print",
                "sale"
        };
    }

    public static final String login_endpoint = wv.domain + "/login_check";
    public static final String register_endpoint = wv.domain + "/register";
    public static final String by_brand = wv.domain_home + "brands/";
    public static final String home_v1 = wv.domain + "api/v1/mobile/ios/homepage";
    public static final String newarrivals = wv.domain_start + "new-arrivals";
    public static final String hometech = wv.domain_start + "categories/" + wv.categories[1];
    public static final String clothing = wv.domain_start + "categories/" + wv.categories[4];
    public static final String shoes = wv.domain_start + "categories/" + wv.categories[2];
    public static final String grooming = wv.domain_start + "categories/" + wv.categories[0];
    public static final String accessories = wv.domain_start + "categories/" + wv.categories[3];
    public static final String checkout_V1 = wv.domain_start + "cart";
    public static final String addtocart = wv.domain_start + "cart/add?variant_id=%s&quantity=%s";
    public static final String checkoutstep2 = wv.domain_start + "checkout/addressing";
    public static final String checkoutstep3 = wv.domain + "login?_target_path=https%3A%2F%2Fstore.hypebeast.com%2Fcheckout%2Faddressing";
    public static final String return_exchange_policy = "http://store.hypebeast.com/returns";

    public static class setting {
        public static float drag_sensitivity = 0.4f;
        public static double list_expand_factor = 0.65d;
        public static int target_total = 1000;
        public static int single_page_items = 18;
        public static int APIversion = 2;
        public static String wv_usergent = "HypebeastStoreApp/1.0";
        public static String useragent = "HypebeastStoreApp/1.0 Android" + Build.VERSION.SDK_INT;
    }

    public static String save_pref_private_id = "HBAUTHENTICATION";
    public static String save_login = "LOGIN";
    public static String save_password = "PASSWORD";
    public static String PARSETAG = "com.hb.parse";
    /**
     * using hesk.kam@101medialab.com    @parse.com
     */

    public static final String PARSE_APPLICATION_ID = "mH86nzB3wbcR8CqVsuOw3scV0YCOK33pRVsCZieJ";
    public static final String PARSE_CLIENT_KEY = "GbxRAwvs6X7rdkVICyQe2MMsEXxVGBe8RAX41dCt";
}
