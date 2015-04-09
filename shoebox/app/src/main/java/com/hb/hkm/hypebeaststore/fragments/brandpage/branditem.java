package com.hb.hkm.hypebeaststore.fragments.brandpage;

import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.taxonomy;
import com.hb.hkm.hypebeaststore.life.retent;

import java.util.ArrayList;

/**
 * Created by hesk on 3/17/15.
 */
public class branditem extends BrandListManager<taxonomy> {


    public static branditem newInstance(Callbacks b) {
        branditem h = new branditem();
        h.attach(b);
        return h;
    }

    @Override
    protected ArrayList<taxonomy> getSource() {
        new SortSimpleAdapterData().sortA2Za2z(retent.brand_list);
        return retent.brand_list;
    }
}
