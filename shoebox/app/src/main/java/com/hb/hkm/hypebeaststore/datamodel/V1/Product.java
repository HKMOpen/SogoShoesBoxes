package com.hb.hkm.hypebeaststore.datamodel.V1;

import com.hb.hkm.hypebeaststore.life.Config;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/15.
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private String price;
    private String sale_price;
    //private TimeWrap available_on;
    //private String sold_out_at;
    //private String back_in_stock_at;
    private String slug;
    private boolean has_variants;
    private Variant master;
    private ArrayList<Variant> variants;
    private ArrayList<Attribute> attributes;
    // ignore options
    private ArrayList<Category> categories;
    private ArrayList<Category> brands;
    private ArrayList<Image> images;
    private ArrayList<simple_product> product_group;

    public Product() {
    }

    public boolean hasVariants() {
        return has_variants;
    }

    public String[] getProductGroups() {
        ArrayList<String> a = new ArrayList<String>();
        if (product_group.size() == 0 || product_group == null) return new String[]{};
        for (simple_product s : product_group) {
            int pid = s.getId();
            Variant found = new Variant();
            for (Variant v : variants) {
                if (v.getPID() == pid) {
                    found = v;
                }
                a.add(s.getName() + found.stock_logic());

            }
        }
        String[] c = new String[a.size()];
        c = a.toArray(c);
        return c;
    }

    public String[] getSizeVariants() {
        ArrayList<String> a = new ArrayList<String>();
        if (variants.size() == 0 || variants == null) return new String[]{};
        for (Variant s : variants) {
            a.add(s.getOptions().get(0).getVal() + "");
        }
        String[] c = new String[a.size()];
        c = a.toArray(c);
        return c;
    }

    public Variant getMasterVariant() {
        return master;
    }

    public int getVariantID(final int i) {
        return variants.get(i).getId();
    }

    public int getMasterID() {
        return master.getId();
    }

    public String getSingleEndPoint() {
        String out = "";
        if (brands != null) {
            out = Config.wv.single_request_route + brands.get(0).getSlug() + "/" + slug;
        }
        return out;
    }

    public String getTitle() {
        return name;
    }

    public String getPrice() {


        return price == null || price.equalsIgnoreCase("0.00") ?
                "" :
                the_price(price);

    }

    public String getSalesPrice() {

        return sale_price == null || sale_price.equalsIgnoreCase("0.00") ?
                "" :
                the_price(sale_price);

    }

    public boolean onSale() {
        return sale_price != null;
    }

    private String the_price(final String p) {
        return "$" + p + " USD";
    }

    public String get_cover_image() {
        return images.get(0).getM_url();
    }

    public String get_brand_name() {
        return brands.get(0).getNameStr();
    }

    public String get_desc() {
        return description;
    }

    public ArrayList<Image> get_product_images() {
        return images;
    }
}
