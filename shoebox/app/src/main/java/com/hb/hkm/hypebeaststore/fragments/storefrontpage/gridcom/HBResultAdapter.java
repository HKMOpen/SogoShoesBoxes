package com.hb.hkm.hypebeaststore.fragments.storefrontpage.gridcom;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.comcast.freeflow.utils.ViewUtils;
import com.hb.hkm.hypebeaststore.ProductSingle;
import com.hb.hkm.hypebeaststore.R;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.Product;
import com.hb.hkm.hypebeaststore.fragments.singlecom.SingleProductApplication;
import com.hb.hkm.hypebeaststore.life.retent;
import com.neopixl.pixlui.components.textview.TextView;
import com.squareup.picasso.Picasso;

/**
 * Created by hesk on 2/3/15.
 */


public class HBResultAdapter extends ArrayAdapter<Product> {

    private final LayoutInflater inflater;
    private final Resources res;
    private final int itemLayout;
    private final Picasso pica = Picasso.with(getContext());
    public static final String TAG = "product list adapter";
    private final Point mview_size;
    private final int[] fsize;

    public HBResultAdapter(Context context, int mitemLayout, Point view_size) {
        super(context, mitemLayout, R.id.description, retent.current_product_list2);
        inflater = LayoutInflater.from(context);
        res = context.getResources();
        itemLayout = mitemLayout;
        mview_size = view_size;
        fsize = resizeConfig(context);
    }

    private int[] resizeConfig(Context c) {
        float padding = ViewUtils.dipToPixels(c, c.getResources().getDimension(R.dimen.grid_item_padding));
        float width = mview_size.x / 2 - padding;
        float height = width * 11 / 10;
        float padding_top = (mview_size.x / 2 - width) / 2;
        float A2 = padding_top * 2;
        return new int[]{
                (int) width, (int) height, (int) padding_top, (int) A2
        };
    }

    private void setConfigMargin(ImageView im) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) im.getLayoutParams();
        // (Ma) param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // param.setM
        params.topMargin = fsize[2];
        im.setLayoutParams(params);
    }

    private void setBrandPriceMargins(TextView tv) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
        params.topMargin = fsize[2];
        params.leftMargin = fsize[2];
        params.rightMargin = fsize[2];
        params.bottomMargin = fsize[2];
        tv.setLayoutParams(params);
    }

    private void setBrandTitleMargins(TextView tv) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
        params.topMargin = fsize[3];
        params.leftMargin = fsize[2];
        params.rightMargin = fsize[2];
        params.bottomMargin = 0;
        tv.setLayoutParams(params);
    }

    private void feedData(final ViewHolder vh, final int position) {
        // final Product p = DataBank.current_product_list.get(position);
        final Product p = getItem(position);
        //final String test = p.get_cover_image();

        // vh.description.title.setText(p.getTitle());
        vh.text.setText(p.get_brand_name());
        vh.price_tag.setText(p.getPrice());
        //vh.text.set
        //   int colorResId = position % 2 == 0 ? R.color.even : R.color.odd;
        //   holder.text.setBackgroundColor(res.getColor(colorResId));
        try {
            // Log.d(TAG, test);
            vh.iv.setMinimumWidth(fsize[0]);
            vh.iv.setMinimumHeight(fsize[1]);
            vh.iv.setPadding(0, 0, 0, 0);
            setConfigMargin(vh.iv);
            setBrandTitleMargins(vh.text);
            setBrandPriceMargins(vh.price_tag);
            pica
                    .load(p.get_cover_image())
                            //.load("http://store.hypebeast.com/bundles/hypebeasteditorial/images/hypebeast-logo.png")
                            //.resizeDimen(R.dimen.dialog_title, R.dimen.dialog_title)
                            //.resize(fsize[0], fsize[1])
                    .fit().centerInside()
                    //  .placeholder(R.drawable.hbstoreicon)
                    .error(R.drawable.hbstoreicon)
                    .into(vh.iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        vh.backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(getContext(), ProductSingle.class);
                i.putExtra(SingleProductApplication.single_id, position);
                getContext().startActivity(i);
            }
        });
    }

  /*  @Override
    public int getCount() {
        return DataBank.current_product_list.size();
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(itemLayout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        feedData(holder, position);
        return convertView;
    }


}