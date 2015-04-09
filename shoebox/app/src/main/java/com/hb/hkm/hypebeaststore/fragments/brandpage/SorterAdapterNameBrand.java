package com.hb.hkm.hypebeaststore.fragments.brandpage;

import android.content.Context;
import android.util.Log;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleArrayAdapter;

import java.util.List;

/**
 * Created by hesk on 30/3/15.
 */
public class SorterAdapterNameBrand extends StickyGridHeadersSimpleArrayAdapter {

    private SortSimpleAdapterData k;

    public SorterAdapterNameBrand(Context context, List<? extends itemDisplay> items, int headerResId, int itemResId) {
        super(context, items, headerResId, itemResId);
        k = new SortSimpleAdapterData();
    }

    private long onSharpGroup(long t) {
        for (int i = 0; i < SortSimpleAdapterData.SHARPGROUP.length; i++) {
            if (SortSimpleAdapterData.SHARPGROUP[i] == t) {
                return (long) 35;
            }
        }

        return t;
    }

    private CharSequence getCharHead(CharSequence r) {

        for (int i = 0; i < SortSimpleAdapterData.SHARPGROUP.length; i++) {
            if (SortSimpleAdapterData.SHARPGROUP[i] == r.charAt(0)) {
                return "#";
            }
        }


        return r.subSequence(0, 1);
    }

    @Override
    protected CharSequence displayHeaderLetter(CharSequence t) {
        return getCharHead(t);
    }

    //# http://unicode-table.com/en/#0023
    @Override
    protected long processHeaderId(char firstChar, long character_index_order) {
        long f = onSharpGroup(character_index_order);

        long ef = "#".charAt(0);

        Log.d("checker", f + ":[" + firstChar + "]");
        return f;
    }
}
