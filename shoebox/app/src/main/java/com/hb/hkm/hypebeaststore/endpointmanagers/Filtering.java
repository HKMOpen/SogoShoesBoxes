package com.hb.hkm.hypebeaststore.endpointmanagers;

import android.content.Context;

import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hesk on 2/9/15.
 */

public class Filtering extends asyclient {

    public static String[] TermsAsListAlphabetical(final ArrayList<TermWrap> items) {
        final ArrayList<String> sl = new ArrayList<String>();
        sl.clear();
        for (TermWrap T : items) {
            sl.add(T.theTerm());
        }
        Collections.sort(sl, String.CASE_INSENSITIVE_ORDER);
        return sl.toArray(new String[sl.size()]);
    }

    public static String[] TermsAsList(final ArrayList<TermWrap> items) {
        final ArrayList<String> sl = new ArrayList<String>();
        sl.clear();
        for (TermWrap T : items) {
            sl.add(T.theTerm());
        }
        return sl.toArray(new String[sl.size()]);
    }

    /**
     * what's kind
     */
    public static String[] SIZE_ORDER_REF = new String[]{
            "Free Size", "XS", "S", "M", "L", "XL", "XXL", "28", "29", "30", "31", "32", "33", "34", "35",
            "36", "38", "40", "41", "42", "XS/S", "S/M", "M/L", "L/XL", "EU36-40", "EU41-46", "EU36", "EU 37", "EU 39",
            "EU 40", "EU 41", "EU 42", "EU 43", "EU 44", "EU 45", "EU 46", "7 1/4", "7 1/2", "7 3/4", "7", "7 1/8", "7 3/8",
            "7 5/8", "7 7/8", "JP 25.5", "JP 26.5", "JP 27.5", "JP 28.5", "JP 29.5", "UK 7", "UK 8", "UK 9", "UK 10", "8", "9",
            "10", "11", "0 = XS", "1 = XS", "1 = S", "2 = S", "2 = M", "3 = M", "3 = L", "4 = L", "4 = XL", "S = 15", "M = 15.5",
            "L = 16", "M = 58", "L = 59", "IT 44 = 28", "IT 46 = 30", "IT 48 = 32", "IT 50 = 34", "IT 46 = S", "IT 48 = M",
            "IT 50 = L", "US 7.5", "US 8", "US 8.5", "US 9", "US 9.5", "US 10", "US 10.5", "US 11", "US 11.5", "US 12", "6-8",
            "9-11", "IT44 = XS", "IT52 = XL", "5 = XXL", "5 = XXL", "39 = S", "40 = M", "41 = L", "42 = XL", "M = 7.5\"",
            "L = 8\"", "90", "95", "100", "38 = XS", "4 = US8.5-9", "5 = US9.5-10", "6 = US10.5-11", "US 8H", "US 9H", "US 10H"
    };


    public static String[] CAT_ORDER_REF = new String[]{
            "Pants", "Shorts", "T-Shirts", "Jackets", "Hoodies", "Jeans", "Shirts", "Underwear", "Swimwear", "Polos",
            "Knitwear", "Blazers", "Sweaters", "Vests", "Henleys", "Tank Tops", "Jerseys", "Keychains", "Bags", "Socks",
            "Watches", "Hats", "Wallets", "Stickers", "Belts", "Cases", "Jewelry", "Scarves", "Ties", "Skateboards",
            "Music", "Gloves", "Eyewear", "Boots", "Sneakers", "Casual Shoes", "Grooming", "Stationery", "Home",
            "Photography", "Audio", "Fragrances"
    };


    protected static String TAG = "filtering";

    public Filtering(Context cc, callback cb) {
        super(cc, cb);
    }

    @Override
    protected void GSONParser(String data) {

    }

    @Override
    protected void ViewConstruction() {

    }

    public static ArrayList<TermWrap> getSorted(final ArrayList<TermWrap> items, final String[] ref) {
        final ArrayList<TermWrap> newlist = new ArrayList<TermWrap>();
        for (String t : ref) {
            for (TermWrap T : items) {
                String strTerm = T.theTerm();
                if (strTerm.equalsIgnoreCase(t)) {
                    newlist.add(new TermWrap(t, T.getTheCount()));
                }
            }
        }
        return newlist;
    }

}
