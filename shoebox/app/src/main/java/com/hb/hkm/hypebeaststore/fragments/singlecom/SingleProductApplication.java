package com.hb.hkm.hypebeaststore.fragments.singlecom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.asynhkm.productchecker.Util.Tool;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.hb.hkm.hypebeaststore.CartWebView;
import com.hb.hkm.hypebeaststore.CartWebViewStyleTopBar;
import com.hb.hkm.hypebeaststore.ProductSingle;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.components.dialogcom.RunLDialogs;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.Image;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.Product;
import com.hb.hkm.hypebeaststore.endpointmanagers.AddCartManager;
import com.hb.hkm.hypebeaststore.endpointmanagers.SingleBuilder;
import com.hb.hkm.hypebeaststore.endpointmanagers.WishListManagr;
import com.hb.hkm.hypebeaststore.endpointmanagers.asyclient;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hb.hkm.hypebeaststore.zoomimage;
import com.hkm.ui.processbutton.ProcessButton;
import com.hkm.ui.processbutton.iml.ActionProcessButton;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import me.drakeet.materialdialog.MaterialDialog;

import static com.hb.hkm.hypebeaststore.fragments.singlecom.SingleProductApplication.RequestTag.ADDCART;
import static com.hb.hkm.hypebeaststore.fragments.singlecom.SingleProductApplication.RequestTag.ADDTOWISH;
import static com.hb.hkm.hypebeaststore.fragments.singlecom.SingleProductApplication.RequestTag.READSINGLE;

/**
 * Single View Holder to Display A SINGLE PRODUCT INFOMATION
 * Created by hesk on 2/5/15.
 */
public class SingleProductApplication
        implements
        BaseSliderView.OnSliderClickListener,
        asyclient.callback,
        View.OnClickListener {
    private static String TAG = "viewholder vh";
    private RequestTag request_tag;
    private int current_variant_selected = 0;
    public final static String single_id = "SINGLE_ID";
    public final static String url_direct = "DIRECT_URL";

    enum RequestTag {
        READSINGLE, ADDCART, ADDTOWISH
    }

    //  private ProgressDialog pd;
    private ProductSingle context;
    private SliderLayout sliderLayout;
    private PagerIndicator pi;
    private TextView tv_brand_title, tv_product_title, price, sales;
    private ActionProcessButton add_bag, add_wish, sp_color, sp_size, sp_cat, sp_qty;
    private Product product;
    private SingleBuilder lb;
    private AddCartManager acm;
    private cardbox card_description, card_size, card_hard;
    private SelectionSpinner SPinner;
    private SwipeRefreshLayout swipcontainer;
    private WishListManagr wishlistmgr;
    private CircleProgressBar progressbar;

    public SelectionSpinner getOptionUIs() {
        return SPinner;
    }

    private final OnNotifyShoppingItem notify;

    @SuppressLint("ResourceAsColor")
    public SingleProductApplication(final ProductSingle act, final Bundle b, final OnNotifyShoppingItem notify_object) {
        context = act;
        notify = notify_object;
        notify.itemNotify(retent.appSettings.getShopping_bag_current_item());
        progressbar = (CircleProgressBar) act.findViewById(R.id.progressBar);
        sliderLayout = (SliderLayout) act.findViewById(R.id.slider);
        pi = (PagerIndicator) act.findViewById(R.id.custom_indicator);
        tv_brand_title = (TextView) act.findViewById(R.id.brand_title);
        tv_product_title = (TextView) act.findViewById(R.id.product_title);
        price = (TextView) act.findViewById(R.id.price_tag);
        // sales = (TextView) act.findViewById(R.id.sale_price_tag);

        tv_brand_title.setVisibility(View.GONE);
        tv_product_title.setVisibility(View.GONE);
        pi.setVisibility(View.GONE);

        sliderLayout.setVisibility(View.GONE);
        sliderLayout.stopAutoCycle();
        price.setVisibility(View.GONE);
        //sales.setVisibility(View.GONE);

        SPinner = new SelectionSpinner(act);

        card_description = new cardbox(act.findViewById(R.id.description), act.getString(R.string.label_field_1));
        card_size = new cardbox(act.findViewById(R.id.size), act.getString(R.string.label_field_2));
        card_hard = new cardbox(act.findViewById(R.id.hardcode), act.getString(R.string.label_field_3));
        card_hard.enableOnClickContentWebURL(Config.return_exchange_policy, "RETURN & EXCHANGES");

        add_bag = (ActionProcessButton) act.findViewById(R.id.add_to_bag);
        add_wish = (ActionProcessButton) act.findViewById(R.id.add_to_wish);

        add_bag.setCompleteText(act.getResources().getString(R.string.button_3));
        add_wish.setCompleteText(act.getResources().getString(R.string.button_added_wish));

        add_bag.setText(act.getResources().getString(R.string.button_1));
        add_wish.setText(act.getResources().getString(R.string.button_2));

        add_bag.setMode(ActionProcessButton.Mode.ENDLESS);
        add_wish.setMode(ActionProcessButton.Mode.ENDLESS);

        add_bag.setOnCompleteColorButton(R.color.green_800, R.color.green_900);
        add_bag.setOnClickCompleteState(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(context, CartWebViewStyleTopBar.class);
                context.startActivity(h);
            }
        });


        init_wish_list_manager();
        /*swipcontainer = (SwipeRefreshLayout) act.findViewById(R.id.swiphandlesingle);
        swipcontainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
        swipcontainer.setEnabled(false);*/
        final int product_array_id = b.getInt(single_id, -1);
        if (product_array_id > -1) {
            from_position_id(product_array_id);
        } else {
            final String u = b.getString(url_direct, "");
            if (!u.isEmpty())
                from_direct_url(u);
            else {
                Tool.trace(context, "there is an issue from loading the data");
                Log.d(TAG, "cannot load the data from the given parameters");
            }
        }


    }


    public interface OnNotifyShoppingItem {
        public void itemNotify(final int items);
    }

    private void hideAll() {

    }

    private void showall() {

    }

    private void wish_button(final boolean item_found) {
        if (item_found) {

            /**
             * adding this product into the wish list
             */

            add_wish.setProgress(100);
            add_wish.setOnClickListener(null);
            add_wish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    request_tag = ADDTOWISH;

                    //context.startActivity(new Intent(context, HBWishList.class));
                    wishlistmgr.justRemoveItem(product);
                    add_wish.setProgress(0);
                    add_wish.setText(context.getResources().getString(R.string.button_2));
                    wish_button(false);

                }
            });
        } else {

            /**
             * adding to the product list
             */

            add_wish.setOnClickListener(null);
            add_wish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    request_tag = ADDTOWISH;
                    if (!wishlistmgr.addItem(product)) {
                        Tool.trace(context, "you have already added this item");
                        Log.d(TAG, "new item is added to the wishlist: " + product.getTitle());
                        add_wish.setProgress(-1);
                    }
                    wish_button(true);
                }
            });
        }
    }

    /**
     * start from the url of the request
     *
     * @param url
     */
    private void from_direct_url(final String url) {
        try {
            request_tag = READSINGLE;
            wish_button(wishlistmgr.check_existing_item(url));

            lb = new SingleBuilder(context, this);
            if (!url.isEmpty())
                lb.setURL(url).execute();
        } catch (NullPointerException e) {
            RunLDialogs.strDemo2(context, e.getMessage());
        } catch (Exception e) {
            RunLDialogs.strDemo2(context, e.getMessage());
        }
    }

    /**
     * start making the url request
     *
     * @param pos
     */
    private void from_position_id(final int pos) {
        try {
            request_tag = READSINGLE;
            final Product product_from_list = retent.current_product_list2.get(pos);
            final String singleurl = product_from_list.getSingleEndPoint();
            context.setTitle(product_from_list.getTitle());
            tv_brand_title.setText(product_from_list.get_brand_name());
            tv_product_title.setText(product_from_list.getTitle());
            wish_button(wishlistmgr.check_existing_item(singleurl));
            //loading point

            lb = new SingleBuilder(context, this);
            if (!singleurl.isEmpty())
                lb.setURL(singleurl).execute();
        } catch (NullPointerException e) {
            RunLDialogs.strDemo2(context, e.getMessage());
        } catch (Exception e) {
            RunLDialogs.strDemo2(context, e.getMessage());
        }
    }


    @Override
    public void onSuccess(String data) {
        switch (request_tag) {
            case READSINGLE:
                /**
                 * from_position_id loaded the json data from request url and now render the ui based on the retrieved data.
                 */
                product = retent.product_single2;
                setup_slider();
                setup_selection_UI();
                setup_textviews();

                progressbar.setVisibility(View.GONE);
                SPinner.setEnabled(true);

                tv_brand_title.setVisibility(View.VISIBLE);
                tv_product_title.setVisibility(View.VISIBLE);
                pi.setVisibility(View.VISIBLE);
                sliderLayout.setVisibility(View.VISIBLE);
                price.setVisibility(View.VISIBLE);

                Log.d(TAG, data);

                break;
            case ADDCART:

                add_bag.setProgress(ProcessButton.FULLBUTTON);
                SPinner.setEnabled(true);

                notify.itemNotify(retent.appSettings.getShopping_bag_current_item());
                break;
        }
    }

    /**
     * when the UI data is retrieved for list of selections like size, cate, and numbers
     */
    protected void setup_selection_UI() {
        if (!SPinner.foundSize()) card_size.setgone();
        if (product.hasVariance())
            try {
                SPinner.addVariance(product.getMappedVariants()).init();

            } catch (Exception e) {
                e.printStackTrace();
            }
        else
            SPinner.noVariance(product).init();
        add_bag.setEnabled(!product.hasVariance());
    }

    protected void setup_textviews() {
        //String simple = !act_product.hasVariants() ? act_product.getMasterVariant().stock_logic() : "";

        context.setTitle(product.getTitle());
        tv_brand_title.setText(product.get_brand_name());
        tv_product_title.setText(product.getTitle());


        card_description.setDesc(product.get_desc());
        card_size.setDesc("product size and all other items goes here.");
        card_hard.setDesc("terms and conditions goes here");
        if (!product.getSalesPrice().equalsIgnoreCase("")) {
            price.setText(product.getPrice());
            // sales.setText(product.getSalesPrice());
        } else {
            price.setText(product.getPrice());
            // sales.setText("");
        }
    }

    protected void setup_slider() {
        try {
            add_bag.setOnClickListener(this);
            NavigableMap<String, String> url_maps = new ConcurrentSkipListMap<>();
            if (product.get_product_images() == null) throw new Exception("no images found");
            for (Image image : product.get_product_images()) {
                url_maps.put(image.originalImage(), image.largeImage());
            }
            url_maps = url_maps.descendingMap();
            for (NavigableMap.Entry<String, String> entry : url_maps.entrySet()) {
                DefaultSliderView textSliderView = new DefaultSliderView(context);
                // initialize a SliderLayout
                textSliderView
                        // .description(entry.getKey())
                        .image(entry.getValue())
                        .setScaleType(BaseSliderView.ScaleType.CenterInside)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.getBundle()
                        .putString(zoomimage.URLKEY, entry.getKey());

                sliderLayout.addSlider(textSliderView);
            }

            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setCustomAnimation(new DescriptionAnimation());
            //sliderLayout.setDuration(4000);
            //sliderLayout.getCurrentSlider().getView();
        } catch (NullPointerException e) {
            RunLDialogs.strDemo2(context, e.getMessage());
        } catch (Exception e) {
            RunLDialogs.strDemo2(context, e.getMessage());
        }
    }

    /**
     * start activity to zoom image view
     *
     * @param baseSliderView
     */
    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
        // baseSliderView.getView().getNextFocusDownId()
        Log.d(TAG, baseSliderView.toString());
        final Intent i = new Intent(context, zoomimage.class);
        final Bundle b = baseSliderView.getBundle();
        i.putExtra(zoomimage.URLKEY, b.getString(zoomimage.URLKEY));
        i.putExtra(zoomimage.TITLE, product.get_brand_name() + " - " + product.getTitle());
        context.startActivity(i);
    }

    protected void init_wish_list_manager() {
        wishlistmgr = new WishListManagr(context, new asyclient.callback() {
            @Override
            public void onSuccess(String data) {

            }

            @Override
            public void onFailure(String message, int code) {

            }

            @Override
            public void beforeStart(asyclient task) {

            }
        });
    }


    @Override
    public void onFailure(String message, int code) {
        final MaterialDialog materialdialognow = new MaterialDialog(context);
        materialdialognow.setTitle("Error Found");
        materialdialognow.setMessage(message);
        materialdialognow.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialdialognow.dismiss();
                context.actionAsUp();
            }
        });
        materialdialognow.setNegativeButton("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialdialognow.dismiss();

            }
        });
        materialdialognow.show();
        add_bag.setProgress(-1);
        add_bag.setEnabled(false);
    }

    @Override
    public void beforeStart(asyclient task) {
        switch (request_tag) {
            case ADDCART:
                //RunLDialogs.strDemo2(context, data);
                add_bag.setEnabled(false);
                add_bag.setProgress(10);
                break;
            case READSINGLE:
                //  pd = new ProgressDialog(context);
                //    pd.setIndeterminateDrawable();
                // pd = ProgressDialog.show(context, null, context.getResources().getString(R.string.wait_button), true, false, null);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * adding this product to the bag
             */
            case R.id.add_to_bag:
                try {
                    request_tag = ADDCART;
                    acm = new AddCartManager(context, this);
                    acm.setItem(SPinner.theVarianceChoice(), SPinner.theQtyChoice());
                    acm.execute();
                    SPinner.setEnabled(false);
                } catch (Exception e) {
                }

                break;

        }
    }
}
