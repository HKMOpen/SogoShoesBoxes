package com.hb.hkm.hypebeaststore.datamodel.V1;

import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.outputV1wrapAdapter;
import com.hb.hkm.hypebeaststore.endpointmanagers.Filtering;

import java.util.ArrayList;

/**
 * Created by hesk on 2/4/15.
 */
public class outputV1Adapter {

    private outputV1wrapAdapter adapter;

    public outputV1Adapter() {
    }


    public outputV1Facets getFacet() {
        return adapter.getfacets();
    }


    public void sortedSize(final ArrayList<TermWrap> existingList) {
        sortingWorker(
                existingList,
                Filtering.getSorted(adapter.getfacets().getSize().getTerms(),
                        Filtering.SIZE_ORDER_REF)
        );
    }

    public void sortedCate(final ArrayList<TermWrap> existingList) {
        sortingWorker(
                existingList,
                Filtering.getSorted(adapter.getfacets().getCategory().getTerms(),
                        Filtering.CAT_ORDER_REF)
        );
    }

    /*public ArrayList<Term> sortedBrand() {
        return Filtering.getSorted(adapter.getfacets().getSize().getTerms(), Filtering.);

    }*/
    private void sortingWorker(final ArrayList<TermWrap> existingList,
                               final ArrayList<TermWrap> mlist) {
        existingList.clear();
        existingList.addAll(mlist);
    }

}
