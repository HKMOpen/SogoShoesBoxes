package com.hb.hkm.hypebeaststore.datamodel.V2.catelog;

/**
 * Created by hesk on 2/23/15.
 */
public class OptionsV2 {
    private OptionSingleV2 option;
    private String value;

    public OptionsV2() {
    }

    public String[] getpair() {

        return new String[]{
                option.getcodename(),
                value
        };
    }

}
