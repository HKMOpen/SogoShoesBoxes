package com.hb.hkm.hypebeaststore.datamodel.V1;

import java.util.ArrayList;

/**
 * Created by hesk on 2/17/15.
 */
public class outputV1ProductWrap {
    private ArrayList<Product> products;
    private int current_page;
    private int pages;

    public int totalpages() {
        return pages;
    }

    public int current_page() {
        return current_page;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}
