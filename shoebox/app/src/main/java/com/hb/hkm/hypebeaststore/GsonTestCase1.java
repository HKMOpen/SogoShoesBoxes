package com.hb.hkm.hypebeaststore;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

import com.asynhkm.productchecker.Util.Tool;
import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hb.hkm.hypebeaststore.endpointmanagers.ListQueryManager;
import com.hb.hkm.hypebeaststore.endpointmanagers.asyclient;
import com.hb.hkm.hypebeaststore.components.dialogcom.RunLDialogs;

public class GsonTestCase1 extends ActionBarActivity implements asyclient.callback {
    private ListQueryManager sync;
    public static String TAG = "store front here";
    private Bundle msavedInstanceState;

    private int page = 1;

    @Override
    public void onSuccess(String data) {
        //RunLDialogs.strDemo2(StoreFront.this, data);
        //DataBank.result_total_pages
        if (page < 3) {
            page++;
            for (final TermWrap tm : retent.filter_list_cat) {
                addTextBody(tm.theTerm());
            }
        } else {
            page = 1;
        }
        loadingList(page);
        Tool.trace(getApplicationContext(), "success page @ " + page);
    }

    @Override
    public void onFailure(String message, int code) {
        RunLDialogs.strDemo2(GsonTestCase1.this, message);
    }

    @Override
    public void beforeStart(asyclient task) {

    }

    public void loadingList(final int page) {
        // final Bundle instance = savedInstanceState;
        try {
            sync = new ListQueryManager(this);
            sync.setPageView(page)
                    .setURL(Config.clothing)
                    .execute();
            addTextBody2(Config.clothing);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextView result1, result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_v1);

        loadingList(page);
        result1.setMovementMethod(new ScrollingMovementMethod());
        result2.setMovementMethod(new ScrollingMovementMethod());
    }

    private void addTextBody2(String mstring) {
        String w1 = result1.getText().toString() + mstring;
        result1.setText(w1);
    }

    private void addTextBody(String mstring) {
        String w1 = result2.getText().toString() + mstring;
        result2.setText(w1);
        result1.setText(mstring);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_front, menu);
        return true;
    }


}
