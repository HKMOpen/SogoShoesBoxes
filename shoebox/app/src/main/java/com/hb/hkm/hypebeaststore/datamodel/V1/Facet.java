package com.hb.hkm.hypebeaststore.datamodel.V1;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/15.
 */
public class Facet {
    private CTFilter size;
    private CTFilter brand;
    private CTFilter category;
    private CTPrice price;

    public Facet() {
    }

    public CTFilter getSize() {
        return size;
    }

    public CTFilter getBrand() {
        return brand;
    }

    public CTFilter getCategory() {
        return category;
    }

    public ArrayList<TermWrap> getPrice() {
        return price.getRanges();
    }
}
