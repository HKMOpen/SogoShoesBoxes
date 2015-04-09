package com.hb.hkm.hypebeaststore.datamodel.V1;

/**
 * Created by hesk on 2/3/15.
 */
public class Image {
    private int id;
//    private String path;
    private int position;
//    private boolean is_model;
    private TimeWrap create_at;
    private String small_url;
    private String medium_url;
    private String large_url;
    private String full_url;

    public Image() {
    }

    public int getPosition() {
        return position;
    }

    public String getS_url() {
        return small_url;
    }

    public String getM_url() {
        return medium_url;
    }

    public String getL_url() {
        return large_url;
    }

    public String getF_url() {
        return full_url;
    }
}
