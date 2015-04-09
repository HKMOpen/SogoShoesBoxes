package com.hb.hkm.hypebeaststore.datamodel.V2.catelog;

/**
 * Created by hesk on 2/23/15.
 */
public class ImageV2core {
    public ImageV2core() {
    }

    private LinkContainer full;
    private LinkContainer small;
    private LinkContainer medium;
    private LinkContainer large;

    public LinkContainer getO() {
        return full;
    }

    public LinkContainer getS() {
        return small;
    }

    public LinkContainer getM() {
        return medium;
    }

    public LinkContainer getL() {
        return large;
    }
}
