package com.hb.hkm.hypebeaststore.fragments.brandpage;

import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;
import com.hb.hkm.hypebeaststore.life.retent;

import java.util.ArrayList;

/**
 * Created by hesk on 27/3/15.
 */
public class filterBrandItemList extends BrandListManager<TermWrap> {
    @Override
    protected ArrayList<TermWrap> getSource() {

        return retent.filter_list_brand;
    }

    public static filterBrandItemList newInstance(Callbacks b) {
        filterBrandItemList h = new filterBrandItemList();
        h.attach(b);
        return h;
    }
}
