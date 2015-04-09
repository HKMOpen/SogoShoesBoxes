package com.hb.hkm.hypebeaststore.endpointmanagers;

import android.content.Context;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.Product;
import com.hb.hkm.hypebeaststore.datamodel.V2.wishlist.WishedItem;
import com.hb.hkm.hypebeaststore.life.retent;

import org.json.JSONException;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by hesk on 3/9/15.
 */
public class WishListManagr extends asyclient {

    private Realm realm;

    public WishListManagr(Context ccc, callback cb) {
        super(ccc, cb);
        realm = Realm.getInstance(ccc);
    }

    public boolean addItem(Product p) {
        boolean canadd = !check_existing_item(p.getSingleEndPoint());
        if (canadd) {
            WishedItem wish = new WishedItem(p);
            retent.WItems.add(wish);
            realm.beginTransaction();
            realm.copyToRealm(wish);
            realm.commitTransaction();
        }
        return canadd;
    }

    public void justRemoveItem(Product product) {
        try {
            //realm.beginTransaction();
            WishedItem w = findProductUrlInWishList(product);
            int k = retent.WItems.indexOf(w);
            retent.WItems.remove(k);
            // realm.beginTransaction();
            // WishedItem item = retent.WItems.get(n_order);
            // item.removeFromRealm();
            // retent.WItems.remove(n_order);
            //  realm.commitTransaction();
        } catch (Exception e) {
        }
    }

    public void notifyItemRemoved(WishedItem item) {
        // realm.beginTransaction();
        // WishedItem item = retent.WItems.get(n_order);
        // item.removeFromRealm();
        // retent.WItems.remove(n_order);
        //  realm.commitTransaction();
    }

    public void asycnFromServer() {
        this.execute();
    }

    public ArrayList<WishedItem> getAllList() {
        return retent.WItems;
    }

    public WishedItem findProductUrlInWishList(Product product) throws Exception {
        for (WishedItem w : retent.WItems) {
            if (w.getLink().equalsIgnoreCase(product.getSingleEndPoint())) {
                return w;
            }
        }
        throw new Exception("item not found");
    }

    /**
     * false is not found
     * true is found
     *
     * @param product_single_link
     * @return
     */
    public boolean check_existing_item(String product_single_link) {
        if (retent.WItems.size() == 0) return false;
        for (WishedItem w : retent.WItems) {
            if (w.getLink().equalsIgnoreCase(product_single_link)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void GSONParser(String data) throws JSONException, JsonSyntaxException, JsonIOException, JsonParseException {

    }

    @Override
    protected void ViewConstruction() {

    }
}
