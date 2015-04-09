package com.hb.hkm.hypebeaststore.datamodel.V2.catelog;

import java.util.ArrayList;

/**
 * Created by hesk on 2/18/15.
 */
public class Variant {
    private int id;
    private boolean master;
    private ArrayList<OptionsV2> options;
    private int on_hand;
    private int on_hold;


    //below this line will be the internal use valuables not directly parsed from the json
    //these are post process and handled by the program and they are not from the json
    private String variant_type;
    private String option_name;
    private String stock_config;
    private boolean instock;
    private int stock_status;


    public static int OUT_OF_STOCK = -1, LAST_ONE = 1, NORMAL = 0;

    public Variant() {

    }

    public int getId() {
        return id;
    }

    /**
     * some pre-processing from the data
     *
     * @return
     */
    public Variant init() {
        variant_type = options.get(0).getpair()[0];
        option_name = options.get(0).getpair()[1];

        final int delta = on_hand - on_hold;
        if (delta == 1) {
            stock_config = "/ Last One Left";
            stock_status = LAST_ONE;
        } else if (delta == 0) {
            stock_config = "/ Out of Stock";
            stock_status = OUT_OF_STOCK;
        } else {
            stock_config = "";
            stock_status = NORMAL;
        }
        instock = delta > 0;

        return this;
    }

    public int stockStatus() {
        return stock_status;
    }

    public String getType() {
        return variant_type;
    }

    public String getMetaValueFromType() {
        return option_name;
    }

    public String stock_logic_configuration() {
        return stock_config;
    }

    public String displayLastItem() {
        return instock ? "Last Item" : "";
    }

    public boolean instock() {
        return instock;
    }
}
