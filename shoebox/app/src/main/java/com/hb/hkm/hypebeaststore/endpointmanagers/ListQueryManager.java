package com.hb.hkm.hypebeaststore.endpointmanagers;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.hb.hkm.hypebeaststore.datamodel.V1.outputV1Adapter;
import com.hb.hkm.hypebeaststore.datamodel.V1.outputV1ProductWrap;
import com.hb.hkm.hypebeaststore.datamodel.V2.catelog.outV2sum;
import com.hb.hkm.hypebeaststore.fragments.brandpage.SortSimpleAdapterData;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by hesk on 2/4/15.
 */
public class ListQueryManager extends asyclient {
    private int page, limit;
    private boolean isReadingMore = false;
    protected static String TAG = "ListQueryManager";

    public ListQueryManager(Context ccc) {
        super(ccc, (callback) ccc);
        page = 1;
        limit = Config.setting.single_page_items;

    }

    public ListQueryManager setListLimit(final int new_limit) {
        limit = new_limit;
        return this;
    }

    public void configOkClient(OkHttpClient c) {
        //c.setCache(createHttpClientCache(ctx));
    }

    public ListQueryManager setPageView(final int page_to_view) {
        page = page_to_view;
        isReadingMore = page_to_view > 1;
        return this;
    }


    @Override
    public asyclient setURL(String e) throws IOException, MalformedURLException, URISyntaxException, Exception {
        final boolean hasQuestionMark = e.contains("?");
        final boolean hasSpace = e.contains(" ");

        final String has = "%s&page=%d&limit=%d";
        final String hasnt = "%s?page=%d&limit=%d";
        url = String.format(hasQuestionMark ? has : hasnt, e, page, limit);

        if (hasSpace) {

            // e.replace(" ", "%20");
            // url = Uri.encode(url);
            // URI uri = new URI(
            // url = Uriuri.toASCIIString()

            URL d = new URL(url);

            URI uri = new URI(d.getProtocol(), d.getUserInfo(), d.getHost(), d.getPort(), d.getPath(), d.getQuery(), d.getRef());

            url = uri.toURL().toString();

        }

        url += retent.msubmissionfilter.isEmpty() ? "" : retent.msubmissionfilter.getJson();

        Log.d(TAG, "request listing url:" + url);
        return this;
    }

    @Override
    protected GsonBuilder configBuilder(GsonBuilder gb) {
        super.configBuilder(gb);
        gb.disableHtmlEscaping();
        // gb.registerTypeAdapter(Term.class, new TermDe());
        // gb.registerTypeAdapterFactory(new TermFactory());
        gb.registerTypeAdapter(Float.class, new TypeAdapter<Float>() {

            @Override
            public Float read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                String stringValue = reader.nextString();
                try {
                    Float value = Float.valueOf(stringValue);
                    return value;
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public void write(JsonWriter writer, Float value) throws IOException {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            }

        });
        return gb;
    }

    @Override
    protected void ViewConstruction() {


    }


    @Override
    protected void GSONParser(final String data) throws JsonSyntaxException, JsonIOException, JsonParseException {
        final JsonReader reader = new JsonReader(new StringReader(data.trim()));
        reader.setLenient(true);
        switch (Config.setting.APIversion) {
            case 1:
                final outputV1ProductWrap output_product = getGson().fromJson(data, outputV1ProductWrap.class);
                retent.current_product_list.addAll(output_product.getProducts());
                retent.result_total_pages = output_product.totalpages();
                retent.result_current_page = output_product.current_page();
                //cgons.addTextBody(data);
                if (!isReadingMore) {
                    outputV1Adapter opadp = getGson().fromJson(data, outputV1Adapter.class);
                    //  Log.d(TAG, opadp.toString());
                    retent.filter_list_price.clear();
                    retent.filter_list_brand.clear();
                    retent.filter_list_price.addAll(opadp.getFacet().getPrice());
                    retent.filter_list_brand.addAll(opadp.getFacet().getBrand());
                    // retent.filter_price = opadp.getFacet().getPriceFilter();
                    opadp.sortedSize(retent.filter_list_size);
                    opadp.sortedCate(retent.filter_list_cat);
                }

                break;
            case 2:
                Log.d(TAG, data);
                final outV2sum outpro = getGson().fromJson(data, outV2sum.class);
                retent.current_product_list2.addAll(outpro.V2output().getProducts().getlist());
                retent.result_total_pages = outpro.V2output().totalpages();
                retent.result_current_page = outpro.V2output().current_page();
                if (!isReadingMore) {
                    retent.filter_list_price.clear();
                    retent.filter_list_brand.clear();
                    retent.filter_list_cat.clear();
                    retent.filter_list_size.clear();
                    retent.filter_list_price.addAll(outpro.V2output().getfacets().getPrice());
                    retent.filter_list_brand.addAll(outpro.V2output().getfacets().getBrand());
                    retent.filter_list_cat.addAll(outpro.V2output().getfacets().getCategory().getTerms());
                    retent.filter_list_size.addAll(outpro.V2output().getfacets().getSize().getTerms());
                    new SortSimpleAdapterData().sortA2Za2z(retent.filter_list_brand);
                    // retent.filter_price = outpro.V2output().getfacets().getPriceFilter();
                }
                break;
        }
    }
}
