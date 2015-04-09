package com.hb.hkm.hypebeaststore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.hb.hkm.hypebeaststore.components.dialogcom.RunLDialogs;
import com.hb.hkm.hypebeaststore.endpointmanagers.HTMLDomReader;
import com.hb.hkm.hypebeaststore.endpointmanagers.HomePageJQuery;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.MaterialColor;
import com.parse.ParseAnalytics;

import java.util.Map;

/**
 * this is th native home page
 * Created by hesk on 2/11/15.
 */
public class HBStoreV2 extends ActionBarActivity {
    final String[] sample_images = new String[]{
            "http://1.bp.blogspot.com/-OOr0aY6bIVw/VOC-CtmeaMI/AAAAAAAAk-o/Szc9_sOtwUE/s1600/Shot%2BOf%2BThe%2BWeek.jpg",
            "http://2.bp.blogspot.com/-Bmwh2nKu3Dg/VJxhEpmcL_I/AAAAAAAAkBQ/W4RNm2tEi4U/s1600/Jump%2BFesta%2B2015%2Bmain.png",
            "http://4.bp.blogspot.com/-FZE142qPpEw/URKaFmLmMyI/AAAAAAAAWY8/KgO4mq8b83U/s1600/Armoed+Franky+SA-MAX01+main.png",
            "http://2.bp.blogspot.com/-WCedbQmPqmU/Ud6Bep7c2EI/AAAAAAAAZV0/BPs6HV87ti8/s1600/Nico+Robin+EZ2+ZOOM00.png",
            "http://2.bp.blogspot.com/-6ECE0JJVi7k/Ur79GaghC7I/AAAAAAAAdZk/cGlOLqZAbm8/s1600/ZOOM+Kalifa+L15+main.png",
            "http://4.bp.blogspot.com/-ACcwF_O2sxA/VNkeKkLxadI/AAAAAAAAkz0/uMEM_sjkvO4/s1600/EXPO02%2BNeo%2BDX%2BMr.2%2BBon%2BKure%2Bmain.png"
    };
    final String[] image_title = new String[]{
            "Shot",
            "Jump Festa 2015",
            "Armoed Franky",
            "Nico Robin Edition-Z",
            "Kalifa - P.O.P Limited Edition",
            "Mr.2 Bon Kure Dome Tour Limitation Ver. - P.O.P Neo EX"
    };
    private String[] theItemList = new String[]{
            "pop",
            "handle",
            "spring",
            "summer"
    };
    final String[] ItemsTitle = new String[]{
            "Robin Nice!",
            "K-Kill!",
            "Luffy!",
            "O - Z!",
            "Zoro",
            "Sixth"
    };
/*
    protected void init_expandtabs(HomePageJQuery client) {
        final toggleWatcher tw = new toggleWatcher();
        final LinearLayout container = (LinearLayout) findViewById(R.id.expanded_menu_list);
        // Start with two views
        for (int i = 0; i < image_title.length; i++) {
            ArrayList<BasicDataBind> bb = new ArrayList<BasicDataBind>();
            for (int h = 0; h < ItemsTitle.length; h++) {
                bb.add(new BasicDataBind(ItemsTitle[h], "zxczx zxczxc"));
            }
            CatelogViewBuilder cb = new CatelogViewBuilder(this);
            cb.preset_src(sample_images[i], getResources().getDimension(R.dimen.home_collapsed))
                    .rndColor()
                    .setImageTitle(image_title[i])
                    .setDataList(bb);
            //cb.setWatcher(tw);
            container.addView(cb.create());
        }
        // tw.addContainer(container);
        tw.addContainer(container);
    }*/

    protected void init_slider(HomePageJQuery client) {
        try {
            final SliderLayout sl = (SliderLayout) findViewById(R.id.slider);
            /*  final Map<String, String> url_maps = new HashMap<String, String>();
            for (int i = 0; i < 10; i++) {
                url_maps.put("sample #" + i, "http://media.salon.com/2013/08/shutterstock_64449766.jpg");
            }*/
            for (final Map.Entry<String, String> entry : client.getSliderImages().entrySet()) {
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .image(entry.getKey())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                onSliderClicked(entry);
                            }
                        });
                //add your extra information
                textSliderView.getBundle()
                        .putString("extra", entry.getKey());
                sl.addSlider(textSliderView);
            }
            sl.setPresetTransformer(SliderLayout.Transformer.Default);
            sl.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            //sl.setCustomAnimation(new DescriptionAnimation());
            sl.setDuration(4000);
        } catch (NullPointerException e) {
            RunLDialogs.strDemo2(this, e.getMessage());
        } catch (Exception e) {
            RunLDialogs.strDemo2(this, e.getMessage());
        }
    }

    private void onSliderClicked(Map.Entry<String, String> entry) {
        String urlNewString = entry.getValue();
        Log.d(HomePageJQuery.TAG, "Home Page here: " + urlNewString);
        Intent intent = new Intent(HBStoreV2.this, StoreFrontV1.class);
        // intent.setData(Uri.parse(url));
        final Bundle f = new Bundle();
        f.putString(StoreFrontV1.APP_INTENT_URI, urlNewString);
        f.putString(StoreFrontV1.APP_INTENT_TITLE, entry.getKey());
        intent.putExtras(f);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        HBStoreV2.this.startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_scale_to_small);
    }

    private HomePageJQuery process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.act_home_v2);
        // Track app opens.
        ParseAnalytics.trackAppOpened(getIntent());
        process = (HomePageJQuery) new HomePageJQuery(this, new HTMLDomReader.callback() {
            @Override
            public void parserDone(String data, HTMLDomReader tasker) {
                HomePageJQuery e = (HomePageJQuery) tasker;
                init_slider(e);
                //  init_expandtabs(e);
            }

            @Override
            public void parserDoneFailure(String message) {

            }

            @Override
            public void requestStart(HTMLDomReader task) {

            }
        }).execute(new String[]{Config.wv.domain_start});

    }

    /*

        static class MaterialColorAdapter extends WheelArrayAdapter<Map.Entry<String, Integer>> {
            MaterialColorAdapter(List<Map.Entry<String, Integer>> entries) {
                super(entries);
            }

            @Override
            public Drawable getDrawable(int position) {
                Drawable[] drawable = new Drawable[]{
                        createOvalDrawable(getItem(position).getValue()),
                        new TextDrawable(String.valueOf(position))
                };
                return new LayerDrawable(drawable);
            }

            private Drawable createOvalDrawable(int color) {
                ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
                shapeDrawable.getPaint().setColor(color);
                return shapeDrawable;
            }
        }
    */
    protected void init_wheel() {
       /*   final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.ex_item, R.id.header_text, theItemList);
        final ExpandableLayoutListView expandableLayoutListView = (ExpandableLayoutListView) findViewById(R.id.expandablelistview);
        expandableLayoutListView.setHeaderDividersEnabled(true);
        expandableLayoutListView.setAdapter(arrayAdapter);
        final ExpandableLayout single_exp = (ExpandableLayout) findViewById(R.id.single_expanableview);
       */
        //single_exp.setVerticalScrollbarPosition();
       /* final WheelView wheelView = (WheelView) findViewById(R.id.wheelview);
        final int ITEM_COUNT = 20;

        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(ITEM_COUNT);
        for (int i = 0; i < ITEM_COUNT; i++) {
            Map.Entry<String, Integer> entry = MaterialColor.random(this, "\\D*_500$");
            entries.add(entry);
        }

        wheelView.setAdapter(new MaterialColorAdapter(entries));*/
    }


    //get the materials darker contrast
    private int getContrastColor(Map.Entry<String, Integer> entry) {
        String colorName = MaterialColor.getColorName(entry);
        return MaterialColor.getContrastColor(colorName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.view_wish_list) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
