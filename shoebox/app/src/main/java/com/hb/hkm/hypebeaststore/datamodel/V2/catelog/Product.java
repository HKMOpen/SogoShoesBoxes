package com.hb.hkm.hypebeaststore.datamodel.V2.catelog;

import java.util.ArrayList;

/**
 * Created by hesk on 2/18/15.
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private int price;
    private int sale_price;
    private String created_at;
    private String updated_at;

    private connection _links;
    private RelatedGroups _embedded;
    private ArrayList<Image> images = new ArrayList<Image>();
    private ArrayList<Variant> variants = new ArrayList<Variant>();

    public ArrayList<Image> get_product_images() {
        return images;
    }

    public String get_cover_image() {
        return images.get(0).mediumImage();
    }

    public String get_brand_name() {
        return _embedded.brands().get(0).getcodename();
    }

    public String getPrice() {
        return the_price(String.valueOf((float) price / (float) 100));
    }

    private String the_price(final String p) {
        return "$" + p + " USD";
    }

    public String getSingleEndPoint() {
        return _links == null ? "" : _links.getself().gethref();
    }

    public String getTitle() {
        return name;
    }

    public String get_desc() {
        return description;
    }

    public String getSalesPrice() {
        return "";
    }

    public boolean hasVariance() {
        return variants.size() > 1;
    }

    public ArrayList<Variant> getMappedVariants() throws Exception {
        if (!hasVariance()) throw new Exception("variance not found");
        ArrayList<Variant> h = new ArrayList<Variant>();
        for (int i = 1; i < variants.size(); i++) {
            Variant m = variants.get(i).init();
            h.add(m);
        }
        return h;
    }

    /**
     * n can only be bigger than 1
     * n > 1
     *
     * @param n
     * @return
     */
    public int getVariantID(int n) {

        return variants.get(n).getId();
    }
}
