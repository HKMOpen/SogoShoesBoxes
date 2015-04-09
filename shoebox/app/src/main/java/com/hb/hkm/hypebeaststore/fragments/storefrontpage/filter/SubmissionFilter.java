package com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hb.hkm.hypebeaststore.life.retent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.StoreFrontV1UISupport.brand_key;
import static com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.StoreFrontV1UISupport.cate_key;
import static com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.StoreFrontV1UISupport.price_key;
import static com.hb.hkm.hypebeaststore.fragments.storefrontpage.filter.StoreFrontV1UISupport.size_key;

/**
 * Created by hesk on 2/16/15.
 */
public class SubmissionFilter {
    public static final String TAG = "submissionFilter";
    public static final String price_pattern = "((?:AED|AFN|ALL|AMD|ANG|AOA|ARS|AUD|AWG|AZN|BAM|BBD|BDT|BGN|BHD|BIF|BMD|BND|BOB|BRL|BSD|BTN|BWP|BYR|BZD|CAD|CDF|CHF|CLP|CNY|COP|CRC|CUP|CVE|CZK|DJF|DKK|DOP|DZD|EGP|ERN|ETB|EUR|FJD|FKP|GBP|GEL|GHS|GIP|GMD|GNF|GTQ|GYD|HKD|HNL|HRK|HTG|HUF|IDR|ILS|INR|IQD|IRR|ISK|JMD|JOD|JPY|KES|KGS|KHR|KMF|KPW|KRW|KWD|KYD|KZT|LAK|LBP|LKR|LRD|LTL|LYD|MAD|MDL|MGA|MKD|MMK|MNT|MOP|MRO|MUR|MWK|MXN|MYR|MZN|NAD|NGN|NIO|NOK|NPR|NZD|OMR|PAB|PEN|PGK|PHP|PKR|PLN|PYG|QAR|RON|RSD|RUB|RWF|SAR|SBD|SCR|SDG|SEK|SGD|SHP|SLL|SOS|SRD|SSP|STD|SYP|SZL|THB|TJS|TMT|TND|TOP|TRY|TTD|TWD|TZS|UAH|UGX|USD|UYU|UZS|VEF|VND|VUV|WST|XAF|XCD|XOF|XPF|YER|ZAR|ZMW)|(?:원|RMB|руб|руб\\.|Lt|ر\\.ق\\.‏|р\\.|د\\.ب\\.‏|TSh|din\\.|Rp|ر|WS\\$|Rs|T\\$|S\\/\\.|SR|Bs\\.|NOK|CF|Fdj|£|¤|¥|SEK|Br|Bs|MTn|د\\.أ\\.‏|ден|den|RUB|أ\\.م\\.‏|лв\\.|नेरू|DA|zł|Nfk|дин|дин\\.|din|din\\.|ر\\.ي\\.‏|US\\$|Ksh|د\\.ت\\.‏|CFA|DT|MAD|B\\/\\.|NT\\$|FCFA|soʻm|UM|Db|CVE|man\\.|EC\\$|PLN|රු\\.|ر\\.س\\.‏|ج\\.م\\.‏|ر\\.ع\\.‏|￥|CA\\$|ALL|Kč|د\\.إ\\.‏|դր\\.|៛|د\\.ك\\.‏|ل\\.ل\\.‏|Afl\\.|сом|LEI|kn|kr|kr\\.|KM|Ft\\.|VT|FC|ف\\.ج\\.ق\\.‏|Fr\\.|SFr\\.|FCFP|m\\.|ریال|FG|ج\\.س\\.|د\\.ج\\.‏|КМ|R\\$|Lekë|৳|د\\.ل\\.‏|ل\\.س\\.‏|Nu\\.|ман\\.|₡|฿|₦|₩|ብር|₪|₫|€|₭|₮|₱|\\$|S\\$|₲|GEL|TRY|₴|₸|₹|₺|₽|Kz|LS|RF|MOP\\$|GH₵|D|E|د\\.ع\\.‏|FBu|G|د\\.م\\.‏|Ft|K|RM|L|USh|P|Q|Le|R|S|Rs\\.|NAf\\.|DKK|؋|Ar|C\\$|MK))[ ]?((?:[0-9]{1,3}[ \\.,]?)*[ \\.,]?[0-9]+)";
    public static final String new_price = "((?:[0-9]{1,3}[\\.,]?)*[\\.,]?[0-9]+)";

    public interface callbackfiltertrigger {
        public void applyfilter();
    }

    public static class PriceF {
        private final String from;
        private final String to;

        public PriceF(String x1, String x2) {
            from = x1;
            to = x2;
        }

        public static PriceF[] gen(String a, String b) {
            float ha = Float.parseFloat(a) * 100;
            float hb = Float.parseFloat(b) * 100;
            return new PriceF[]{new PriceF((int) ha + "", (int) hb + "")};
        }
    }

    private int
            icat = -1, ibrand = -1, isize = -1,
            price_range_index = -1;
    private boolean newFilter = false;
    private query q = new query();
    private String current_selection_operation;

    static class query {
        String[] brand = new String[0];
        String[] category = new String[0];
        String[] size = new String[0];
        PriceF[] price = new PriceF[0];

        public query() {
        }
    }

    public void reset(final String title) {
        if (title.equalsIgnoreCase(price_key)) {
            unsetPrice();
        } else if (title.equalsIgnoreCase(cate_key)) {
            unsetCate();
        } else if (title.equalsIgnoreCase(brand_key)) {
            unsetBrand();
        } else if (title.equalsIgnoreCase(size_key)) {
            unsetSize();
        }
    }

    public void setFilterOption(final String title, final String label, int which) {
        if (!title.equalsIgnoreCase("")) {
            if (title.equalsIgnoreCase(price_key)) {
                setPrice(label, which);
            } else if (title.equalsIgnoreCase(cate_key)) {
                setCate(label, which);
            } else if (title.equalsIgnoreCase(brand_key)) {
                setBrand(label, which);
            } else if (title.equalsIgnoreCase(size_key)) {
                setSize(label, which);
            }
        }

    }

    public SubmissionFilter() {
    }

    /**
     * when there is a trigger from another component, this will work
     * call back mechanism
     */
    private callbackfiltertrigger trigger;

    public void setOnFilterApply(callbackfiltertrigger t) {
        trigger = t;
    }

    public void filterapply() {
        if (trigger != null) trigger.applyfilter();
    }

    public void setOperation(String t) {
        current_selection_operation = t;
    }

    public int getIndexPrice() {
        return price_range_index;
    }

    public int getIndexCat(String[] ls) {
        return q.category.length > 0 ? getIndex(ls, q.category[0]) : -1;
    }

    public int getIndexBrand(String[] ls) {
        return q.brand.length > 0 ? getIndex(ls, q.brand[0]) : -1;
    }

    public int getIndexSize(String[] ls) {
        return q.size.length > 0 ? getIndex(ls, q.size[0]) : -1;
    }

    private int getIndex(String[] ls, String item) {
        for (int i = 0; i < ls.length; i++) {
            if (ls[i].equalsIgnoreCase(item)) {
                return i;
            }
        }
        return -1;
    }

    private boolean setPrice(String label, int which) {
        final Pattern p = Pattern.compile(new_price, Pattern.DOTALL);
        final Matcher m = p.matcher(label);
        //Log.d(TAG, "found! good start");
        int i = 0;
        isize = which;
        String e1 = "", e2 = "";
        while (m.find()) {
            i++;
            if (i == 1) e1 = m.group();
            if (i == 2) e2 = m.group();
        }
        if (i > 0) {
            if (label.contains("under")) {
                q.price = PriceF.gen("", e1);
                newfilter();
                return true;
            }
            if (label.contains("above")) {
                q.price = PriceF.gen(e1, "");
                newfilter();
                return true;
            }
            q.price = PriceF.gen(e1, e2);
            newfilter();
            return true;
        } else
            return false;
    }

    private void setCate(String b, int which) {
        if (icat != which) {
            q.category = new String[]{b};
            icat = which;
            newfilter();
        }
    }

    private void setSize(String b, int which) {
        if (isize != which) {
            q.size = new String[]{b};
            newfilter();
            isize = which;
        }

    }

    private void setBrand(String b, int which) {
        if (ibrand != which) {
            q.brand = new String[]{b};
            newfilter();
            ibrand = which;
        }
    }

    private void newfilter() {
        newFilter = true;
        retent.result_current_page = 1;
        retent.current_product_list.clear();
        retent.current_product_list2.clear();
    }

    public String getJson() {
        final GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        String filter = gson.toJson(this.q);
        String filter_url = Uri.encode(filter);
        if (newFilter) newFilter = false;
        return "&filter=" + filter_url;
    }


    public boolean reset() {
        boolean k = !isEmpty();
        if (k) {
            q.brand = new String[0];
            q.category = new String[0];
            q.size = new String[0];
            q.price = new PriceF[0];
            price_range_index = -1;
            icat = -1;
            ibrand = -1;
            isize = -1;
            newfilter();
        }
        return k;
    }

    public void unsetBrand() {
        q.brand = new String[0];
    }

    public void unsetCate() {
        q.category = new String[0];
    }

    public void unsetSize() {
        q.size = new String[0];
    }

    public void unsetPrice() {
        q.price = new PriceF[0];
    }

    public boolean justSet() {
        final boolean g = newFilter;
        if (newFilter) newFilter = false;
        return g;
    }

    public boolean isEmpty() {
        final boolean a = q.brand.length == 0;
        final boolean b = q.category.length == 0;
        final boolean c = q.size.length == 0;
        final boolean d = q.price.length == 0;
        return a && b && c && d;
    }

    public boolean isEmptyBrand() {
        return q.brand.length == 0;
    }

    public boolean isEmptyCat() {
        return q.category.length == 0;
    }

    public boolean isEmptySize() {
        return q.size.length == 0;
    }

    public boolean isEmptyPrice() {
        return q.price.length == 0;
    }
/*
    public void dialogSelection(final int which, final String title, String[] ls) {
        if (which > -1) {
            if (title.equalsIgnoreCase("price")) {
                price_range_index = which;
                setPrice(retent.filter_price.getRangeAt(which));
            } else if (title.equalsIgnoreCase("category")) {
                setCate(ls[which], which);
            } else if (title.equalsIgnoreCase("brand")) {
                setBrand(ls[which], which);
            } else if (title.equalsIgnoreCase("size")) {
                setSize(ls[which], which);
            }
        }
    }*/

}
