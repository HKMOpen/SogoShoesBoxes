package com.hb.hkm.hypebeaststore.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hb.hkm.hypebeaststore.CartWebView;
import com.hb.hkm.hypebeaststore.CartWebViewStyleTopBar;
import com.hb.hkm.hypebeaststore.HBWishList;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.endpointmanagers.asyclient;
import com.hb.hkm.hypebeaststore.life.retent;

/**
 * Created by hesk on 2/6/15.
 */
public class BasicSupportActionBarHKM extends ActionBarActivity {
    protected ActionBar action;
    private final static String TAG = "BASIC_SUPPORT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        action = getSupportActionBar();
      /*  action.setHomeButtonEnabled(true);
        action.setDisplayHomeAsUpEnabled(true);*/
        //  action.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        // action.setDisplayShowCustomEnabled(true);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, action.toString());
        super.onBackPressed();
    }

    public void actionAsUp() {
      /*
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        getWindow().setBackgroundDrawable(colorDrawable);
       */
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            // This activity is NOT part of this app's task, so create a new task
            // when navigating up, with a synthesized back stack.
            // upIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            // upIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            upIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            TaskStackBuilder.create(this)
                    // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                    .startActivities();
        } else {
            // This activity is part of this app's task, so simply
            // navigate up to the logical parent activity.
            NavUtils.navigateUpTo(this, upIntent);
        }
        transitionOut();
    }

    protected void transitionOut() {
        overridePendingTransition(R.anim.enter_from_small, R.anim.exit_out_right);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                actionAsUp();
                return true;
            case R.id.closing_webview:
                finish();
                return true;
            case R.id.shopcart:
                Intent h = new Intent(this, CartWebViewStyleTopBar.class);
                startActivity(h);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private int shoppingItems = 0;

    protected int getMenuId() {
        return -1;
    }

    protected void NotifyCountShoppingItems(int count) {
        shoppingItems = count;
        supportInvalidateOptionsMenu();
    }

    protected void NotifyShoppingCount() {
        shoppingItems = retent.appSettings.getShopping_bag_current_item();
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (getMenuId() > -1) {
            getMenuInflater().inflate(getMenuId(), menu);
            try {
                MenuItem menuItem = menu.findItem(R.id.shopcart);
                menuItem.setIcon(buildCounterDrawable(shoppingItems, R.drawable.shoppingbag));
            } catch (Exception e) {

            }

        }


        return true;
    }


    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.menu_bag_icon, null);
        view.setBackgroundResource(backgroundImageId);
        //  View counterTextPanel = view.findViewById(R.id.counterValuePanel);
        View count_view = view.findViewById(R.id.countview);
        TextView textView = (TextView) view.findViewById(R.id.count);
        if (count == 0) {
            count_view.setVisibility(View.GONE);
            // counterTextPanel.setVisibility(View.GONE);
        } else {
            //  counterTextPanel.setVisibility(View.VISIBLE);
            textView.setText("" + count);
            count_view.setVisibility(View.VISIBLE);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

}
