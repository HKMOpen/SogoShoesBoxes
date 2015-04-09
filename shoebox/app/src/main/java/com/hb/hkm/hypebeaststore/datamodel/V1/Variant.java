package com.hb.hkm.hypebeaststore.datamodel.V1;

import java.util.ArrayList;

/**
 * Created by hesk on 2/3/15.
 */
public class Variant {
    private int id;
    private String presentation;
    private String sku;
    private String price;
    private String sale_price;
    private boolean is_master;
    private int on_hand;
    private int on_hold;
    private int product_id;
    private ArrayList<Option> option_values;

    public Variant() {
    }

    public int getId() {
        return id;
    }

    public int getPID() {
        return product_id;
    }

    public ArrayList<Option> getOptions() {
        return option_values;
    }

    public String stock_logic() {
        int delta = on_hand - on_hold;
        if (delta == 1) {
            return "/ Last One Left";
        } else if (delta == 0) {
            return "/ Out of Stock";
        } else {
            return "";
        }
    }
}
