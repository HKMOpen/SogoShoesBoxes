package com.hb.hkm.hypebeaststore;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hb.hkm.hypebeaststore.fragments.BasicSupportActionBarHKM;
import com.hb.hkm.hypebeaststore.fragments.singlecom.SingleProductApplication;
import com.hb.hkm.hypebeaststore.life.retent;

/**
 * Created by hesk on 2/5/15.
 */
public class ProductSingle extends BasicSupportActionBarHKM {
    private SingleProductApplication vh;
    public static final String TAG = "Product Single Intent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setContentView(R.layout.act_product_single);
            vh = new SingleProductApplication(this, getIntent().getExtras(),
                    new SingleProductApplication.OnNotifyShoppingItem() {
                        public void itemNotify(final int k) {
                            ProductSingle.this.NotifyCountShoppingItems(retent.appSettings.getShopping_bag_current_item());
                        }
                    });
        }
        //else {
        // final int pos = (int) savedInstanceState.getSerializable(Config.single_id);
        //}
        overridePendingTransition(R.anim.enter_from_right, R.anim.fadeoutto50);
    }

    @Override
    protected int getMenuId() {
        return R.menu.nagbar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Check which request we're responding to
            if (requestCode == SelectView.ACTION_ON_SELECT && data != null) {
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    // The user picked a contact.
                    // The Intent's data Uri identifies which contact was selected.

                    Bundle b = data.getExtras();

                    int getPos = b.getInt(SelectView.pos, -1);
                    int kind = b.getInt(SelectView.kind, 0);
                    String displayVal = b.getString(SelectView.selection_val, "");
                    // Do something with the contact here (bigger example below)
                    switch (kind) {
                        case SelectView.SIZE:
                            // load = DataBank.filter_list_size;
                            vh.getOptionUIs().setOption(displayVal, getPos, kind);
                            break;
                        case SelectView.COLOR:
                            vh.getOptionUIs().setOption(displayVal, getPos, kind);
                            // load = DataBank.filter_list_cat;
                            break;
                        case SelectView.QTY:
                            vh.getOptionUIs().setQty(displayVal);
                            //  load = DataBank.filter_list_size;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "this error from result" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        actionAsUp();

    }

}
