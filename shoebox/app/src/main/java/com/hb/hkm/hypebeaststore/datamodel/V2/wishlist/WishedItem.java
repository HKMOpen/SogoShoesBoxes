package com.hb.hkm.hypebeaststore.datamodel.V2.wishlist;

import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.Product;

import io.realm.RealmObject;

/**
 * Created by hesk on 3/9/15.
 */
public class WishedItem extends RealmObject {
    private String updated;
    private String link;
    private String small_image_path;
    private String brand;
    private String product_title;
    private String price;


    public WishedItem() {

    }

    public WishedItem(Product product) {
        // newItem(iproduct);
        link = product.getSingleEndPoint();
        small_image_path = product.get_cover_image();
        brand = product.get_brand_name();
        product_title = product.getTitle();
        price = product.getPrice();
    }

    /*    public void newItem(Product product) {
            link = product.getSingleEndPoint();
            small_image_path = product.get_cover_image();
            brand = product.get_brand_name();
            product_title = product.getTitle();
            price = product.getPrice();
        }*/
    public void setBrand(String mbrand) {
        brand = mbrand;
    }

    public String getBrand() {
        return brand;
    }

    public void setProduct_title(String title) {
        product_title = title;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setSmall_image_path(String path) {
        small_image_path = path;
    }

    public String getSmall_image_path() {
        return small_image_path;
    }

    public void setPrice(String p) {
        price = p;
    }

    public String getPrice() {
        return price;
    }

    public void setLink(String l) {
        link = l;
    }

    public String getLink() {
        return link;
    }

    public void setUpdated(String time) {
        updated = time;
    }

    public String getUpdated() {
        return updated;
    }
}
