package com.hb.hkm.hypebeaststore.life;

import com.hb.hkm.hypebeaststore.datamodel.V1.CartProduct;
import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.Product;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.outputV2;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.taxonomy;
import com.hb.hkm.hypebeaststore.datamodel.V2.homepage.APPSettings;
import com.hb.hkm.hypebeaststore.datamodel.V2.homepage.elementHome;
import com.hb.hkm.hypebeaststore.datamodel.V2.wishlist.WishedItem;
import com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.SubmissionFilter;

import java.util.ArrayList;

/**
 * Created by hesk on 2/4/15.
 */
public class retent {
    public static String loaded = "";
    public static outputV2 prodcut_master_list;
    public static Product product_single2;
    public static com.hb.hkm.hypebeaststore.datamodel.V1.Product product_single;
    public static final ArrayList<Product> current_product_list2 = new ArrayList<Product>();
    public static final ArrayList<com.hb.hkm.hypebeaststore.datamodel.V1.Product> current_product_list = new ArrayList<com.hb.hkm.hypebeaststore.datamodel.V1.Product>();
    public static final ArrayList<CartProduct> my_cart = new ArrayList<CartProduct>();
    public static final ArrayList<TermWrap> filter_list_size = new ArrayList<TermWrap>();
    public static final ArrayList<TermWrap> filter_list_cat = new ArrayList<TermWrap>();
    public static final ArrayList<TermWrap> filter_list_price = new ArrayList<TermWrap>();
    public static final ArrayList<TermWrap> filter_list_brand = new ArrayList<TermWrap>();
    //public static CTPrice filter_price = new CTPrice();
    public static final SubmissionFilter msubmissionfilter = new SubmissionFilter();
    public static int result_total_pages = 0;
    public static int result_current_page = 0;
    public static String queryBaseUrl;

    public static final ArrayList<taxonomy> brand_list = new ArrayList<taxonomy>();



    public static final ArrayList<WishedItem> WItems = new ArrayList<>();
    public static final ArrayList<elementHome> home_elements = new ArrayList<>();
    public static final APPSettings appSettings = new APPSettings();

}
