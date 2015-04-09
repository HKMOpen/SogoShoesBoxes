package com.hb.hkm.hypebeaststore.components.dialogcom;

import android.content.Context;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by hesk on 2/3/15.
 */
public class RunLDialogs {
    public static void strDemo2(Context ctx, final String context) {
        final MaterialDialog materialdialognow = new MaterialDialog(ctx);
        materialdialognow.setTitle("Error Found");
        materialdialognow.setMessage(context);
        materialdialognow.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialdialognow.dismiss();
            }
        });
        materialdialognow.setNegativeButton("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialdialognow.dismiss();

            }
        });
        materialdialognow.show();
    }


}
