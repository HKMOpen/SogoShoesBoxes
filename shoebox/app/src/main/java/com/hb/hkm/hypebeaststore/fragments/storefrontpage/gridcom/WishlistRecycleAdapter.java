package com.hb.hkm.hypebeaststore.fragments.storefrontpage.gridcom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.hb.hkm.hypebeaststore.ProductSingle;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.V2.wishlist.WishedItem;
import com.hb.hkm.hypebeaststore.endpointmanagers.WishListManagr;
import com.hb.hkm.hypebeaststore.fragments.singlecom.SingleProductApplication;
import com.hb.hkm.hypebeaststore.life.LifeCycleApp;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.neopixl.pixlui.components.textview.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hesk on 3/9/15.
 */


public class WishlistRecycleAdapter extends RecyclerSwipeAdapter<WishlistRecycleAdapter.SimpleViewHolder> {
    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        final SwipeLayout swipeLayout;
        final TextView textViewBrand;
        final TextView textViewProductTitle;
        final ImageView buttonDelete;
        final TextView buttonview;
        final ImageView iviewm;
        final CircleProgressBar pbar;
        private String url;
        final private View clickview;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            textViewBrand = (TextView) itemView.findViewById(R.id.brand);
            textViewProductTitle = (TextView) itemView.findViewById(R.id.product_title);
            buttonDelete = (ImageView) itemView.findViewById(R.id.delete);
            buttonview = (TextView) itemView.findViewById(R.id.see);
            iviewm = (ImageView) itemView.findViewById(R.id.iv_image_done);
            pbar = (CircleProgressBar) itemView.findViewById(R.id.loadingb);
            pbar.setColorSchemeResources(R.color.blue_400);
            clickview = iviewm;
        }
    }

    final private Context mContext;
    final private ArrayList<WishedItem> mDataset;
    final protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);
    final private Picasso loader;
    final private LifeCycleApp appcontext;
    final private WishListManagr manager;

    public WishlistRecycleAdapter(Context context, ArrayList<WishedItem> objects) {
        mContext = context;
        mDataset = objects;
        appcontext = (LifeCycleApp) context.getApplicationContext();
        loader = appcontext.getInstancePicasso();
        manager = new WishListManagr(context, null);
    }


    public void notifySwipeManager() {
        mItemManger.closeAllItems();
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishlist, parent, false);
        return new SimpleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final WishedItem item = mDataset.get(position);
        //viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //  YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });

        /**
         *
         * as this is not working there..

         viewHolder.swipeLayout.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        Intent i = new Intent(mContext, ProductSingle.class);
        i.putExtra(SingleProductApplication.url_direct, item.getLink());
        mContext.startActivity(i);
        }
        });

         */
        viewHolder.swipeLayout.setOnLongClickListener(new SwipeLayout.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                viewHolder.swipeLayout.toggle(true);
                return false;
            }
        });
        viewHolder.buttonview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ProductSingle.class);
                i.putExtra(SingleProductApplication.url_direct, item.getLink());
                mContext.startActivity(i);
                //start to see the single product view
            }
        });
        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                WishedItem i = mDataset.remove(position);
                manager.notifyItemRemoved(i);
                //manager.removeItem(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());
                mItemManger.closeAllItems();
                // Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.textViewBrand.setText(item.getBrand());
        viewHolder.textViewProductTitle.setText(item.getProduct_title());

        loader.load(item.getSmall_image_path()).into(viewHolder.iviewm, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.pbar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


}