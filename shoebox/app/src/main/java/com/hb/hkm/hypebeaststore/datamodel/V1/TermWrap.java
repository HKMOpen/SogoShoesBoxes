package com.hb.hkm.hypebeaststore.datamodel.V1;

import com.hb.hkm.hypebeaststore.fragments.brandpage.itemDisplay;

/**
 * Created by hesk on 2/3/15.
 */
public class TermWrap extends itemDisplay {
    /**
     * from json
     */
    private String term = "";
    private int count = 0;

    /**
     * end json
     */
    public TermWrap() {
    }

    public TermWrap(String t) {
        term = t;
    }

    public TermWrap(String t, int c) {
        count = c;
        term = t;
    }

    public String theTerm() {
        return term;
    }

    @Override
    public String toString() {
        return term;
    }

    @Override
    public boolean isEnabled() {
        return count > 0;
    }

    public int getTheCount() {
        return count;
    }
}
