package com.hb.hkm.hypebeaststore.datamodel.V2.brandlist;

import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.taxonomy;
import com.hb.hkm.hypebeaststore.fragments.brandpage.SortSimpleAdapterData;
import com.hb.hkm.hypebeaststore.life.retent;

import java.util.ArrayList;

/**
 * Created by hesk on 3/17/15.
 */
public class wrapper {
    private ArrayList<taxonomy> brands;

    public wrapper() {
    }

    public ArrayList<taxonomy> getBrands() {
        new SortSimpleAdapterData().sortA2Za2z(brands);
        return brands;
    }
}
