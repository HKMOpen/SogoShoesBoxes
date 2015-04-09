package com.hb.hkm.hypebeaststore.datamodel.V1;

/**
 * Created by hesk on 2/3/15.
 */
public class Option {
    //not use
    //the id of option
    private int id;
    //not use
    //the presentation of the option label
    private String presentation;
    //the name of the product
    private String name;
    //the value of option in specific measurements
    private int value;

    public Option() {
    }

    public String getName() {
        return name;
    }

    public String getPresentation() {
        return presentation;
    }

    public int getVal() {
        return value;
    }
}
