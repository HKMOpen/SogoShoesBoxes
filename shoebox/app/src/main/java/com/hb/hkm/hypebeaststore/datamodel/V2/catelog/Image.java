package com.hb.hkm.hypebeaststore.datamodel.V2.catelog;

/**
 * Created by hesk on 2/18/15.
 */
public class Image {
    private int id;
    private String path;
    private int position;
    private ImageV2core _links;

    public Image() {

    }

    public String getPath() {
        return path;
    }

    public int getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public String originalImage() {
        return _links.getO().gethref();
    }

    public String smallImage() {
        return _links.getS().gethref();
    }

    public String mediumImage() {
        return _links.getM().gethref();
    }

    public String largeImage() {
        return _links.getL().gethref();
    }
}
