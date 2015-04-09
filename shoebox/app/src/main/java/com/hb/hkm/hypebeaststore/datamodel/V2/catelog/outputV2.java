package com.hb.hkm.hypebeaststore.datamodel.V2.catelog;

import com.hb.hkm.hypebeaststore.datamodel.V1.outputV1Facets;

/**
 * Created by hesk on 2/6/15.
 */
public class outputV2 {

    private int page;
    private int limit;
    private int pages;
    private int total;
    private wraperV2embeded _embedded;
    private outputV1Facets facets;


    public int totalpages() {
        return pages;
    }

    public int current_page() {
        return page;
    }

    public wraperV2embeded getProducts() {
        return _embedded;
    }

    public outputV1Facets getfacets() {
        return facets;
    }
}
