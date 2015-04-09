package com.hb.hkm.hypebeaststore.fragments.storefrontpage.gridcom;

import android.view.View;
import android.widget.ImageView;
import com.neopixl.pixlui.components.textview.TextView;

import com.hb.hkm.hypebeaststore.R;

/**
 * Created by hesk on 2/11/15.
 */
public class ViewHolder {

   // final cardbox description;
    public final TextView price_tag;
    public final TextView text;
    public final ImageView iv;
    public final View backdrop;

    ViewHolder(View view) {
       // description = new cardbox((View) view.findViewById(R.id.description), "DESCRIPTION");
        iv = (ImageView) view.findViewById(R.id.imagevi);
        price_tag = (TextView) view.findViewById(R.id.price_tag);
        backdrop = view;
        text = (TextView) view.findViewById(R.id.description);
    }
}
