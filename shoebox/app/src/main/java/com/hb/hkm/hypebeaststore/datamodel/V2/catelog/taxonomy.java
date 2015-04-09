package com.hb.hkm.hypebeaststore.datamodel.V2.catelog;

import com.hb.hkm.hypebeaststore.fragments.brandpage.itemDisplay;

/**
 * Created by hesk on 2/23/15.
 */
public class taxonomy extends itemDisplay {
    private String name;
    private String slug;
    private String permalink;
    private String description;

    public taxonomy() {
    }

    public String getcodename() {
        return name;
    }

    public String getslug() {
        return slug;
    }

    public String getpermalink() {
        return permalink;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
