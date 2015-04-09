package com.hb.hkm.hypebeaststore.datamodel.V2.homepage;

import io.realm.RealmObject;

/**
 * Created by hesk on 2/4/15.
 */
public class APPSettings extends RealmObject {

    private int shopping_bag_current_item = 0;

    public APPSettings() {
    }

    public void setShopping_bag_current_item(int n) {
        shopping_bag_current_item = n;
    }

    public int getShopping_bag_current_item() {
        return shopping_bag_current_item;
    }
}
