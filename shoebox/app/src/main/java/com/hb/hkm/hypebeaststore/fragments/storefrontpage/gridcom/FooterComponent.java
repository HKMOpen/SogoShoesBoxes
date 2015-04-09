package com.hb.hkm.hypebeaststore.fragments.storefrontpage.gridcom;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hb.hkm.hypebeaststore.R;
import com.hkm.ui.BounceScoller.BounceListener;
import com.hkm.ui.BounceScoller.BounceScroller;

/**
 * Created by hesk on 2/10/15.
 */
public class FooterComponent implements BounceListener {
    private TextView field1, field2, footer;
    private BounceScroller scroller;

    public FooterComponent(BounceScroller scl, View groupview, Context ctx) {

        scl.ifFooterBounce(true);
        scl.setListener(this);

        field1 = (TextView) groupview.findViewById(R.id.status_now);
        field2 = (TextView) groupview.findViewById(R.id.info_extra);

      /*
        field1 = new TextView(ctx);
        field1.setPadding(10, 20, 10, 20);
        field1.setText("Pullable Footer View");
        field1.setBackgroundColor(ctx.getResources().getColor(R.color.primary_pref_v2));
        field1.setTextColor(ctx.getResources().getColor(R.color.primary_pref));
        field1.setGravity(Gravity.CENTER);*/
        scl.setFooterView(groupview);
        //field1.setText("START TO LOAD MORE");
        scroller = scl;

    }

    public void nowt(int t) {
        field1.setText(t + "");
    }

    @Override
    public void onState(boolean header, BounceScroller.State state) {
        if (state == BounceScroller.State.STATE_SHOW) {
            field1.setText("Pullable Header Show");
        } else if (state == BounceScroller.State.STATE_OVER) {
            field1.setText("Pullable Header Over");
        } else if (state == BounceScroller.State.STATE_FIT_EXTRAS) {
            scroller.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scroller.fitContent();
                }
            }, 5000);
        }
    }

    @Override
    public void onOffset(boolean header, int offset) {
        //tvOffset.setText(position + " offset " + offset);
        if (!header)
            nowt(offset);

    }
}
