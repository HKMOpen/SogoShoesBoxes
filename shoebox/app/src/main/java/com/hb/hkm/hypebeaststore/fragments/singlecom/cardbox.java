package com.hb.hkm.hypebeaststore.fragments.singlecom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.hb.hkm.hypebeaststore.EasyURLredirect;
import com.hb.hkm.hypebeaststore.R;
import com.neopixl.pixlui.components.textview.TextView;

/**
 * Created by hesk on 2/11/15.
 */
public class cardbox {

    public final TextView _title;
    public final TextView content;
    public final String tag;
    public final View holder;

    public cardbox(View v, String tag) {
        _title = (TextView) v.findViewById(R.id.title);
        content = (TextView) v.findViewById(R.id.context_conten);
        _title.setText(tag);
        holder = v;
        this.tag = tag;
    }

    public void setTitle(final String title) {
        _title.setText(title);
    }

    public void setDesc(final String desc) {
        content.setText(desc);
    }

    public void setgone() {
        holder.setVisibility(View.GONE);
    }

    public void enableOnClickContentWebURL(final String url, final String title) {
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.getContext(), EasyURLredirect.class);
                Bundle b = new Bundle();
                b.putString(EasyURLredirect.INTENT_BUNDLE_URL, url);
                b.putString(EasyURLredirect.INTENT_BUNDLE_TITLE, title);
                i.putExtras(b);
                holder.getContext().startActivity(i);
            }
        });
    }
}
