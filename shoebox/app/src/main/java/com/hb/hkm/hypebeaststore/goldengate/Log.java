package com.hb.hkm.hypebeaststore.goldengate;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by hesk on 2/25/15.
 */
public class Log {
    private Context ctx;

    public Log(Context ctx) {
        this.ctx = ctx;
    }

    public void showHTML(String html) {
        new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
                .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
    }
}
