package com.hb.hkm.hypebeaststore;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.asynhkm.productchecker.Util.Tool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hb.hkm.hypebeaststore.life.Config;
import com.hb.hkm.hypebeaststore.life.retent;
import com.hb.hkm.hypebeaststore.datamodel.V1.outputV1ProductWrap;
import com.hb.hkm.hypebeaststore.datamodel.gsontool.TermFactory;
import com.hb.hkm.hypebeaststore.components.dialogcom.RunLDialogs;
import com.hb.hkm.hypebeaststore.endpointmanagers.ListQueryManager;
import com.hb.hkm.hypebeaststore.endpointmanagers.asyclient;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application>
        implements

        asyclient.callback {
    private Application mApplication;
    private ListQueryManager sync;
    private final GsonBuilder gb = new GsonBuilder();
    private Gson g;
    String rtt;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // final RenamingMockContext mockContext = new RenamingMockContext(
        // getContext());
        // setContext(mockContext);
        createApplication();
        mApplication = getApplication();
        rtt = mApplication.getString(R.string.mock_dt);


    }

    public static void test_json_reading() throws Exception {

    }


    @Override
    public void onSuccess(String data) {
        //RunLDialogs.strDemo2(StoreFront.this, data);
        Tool.trace(mApplication, "added items");
    }

    @Override
    public void onFailure(String message) {
        RunLDialogs.strDemo2(mApplication, message);
    }

    @Override
    public void beforeStart(asyclient task) {

    }

    /**
     * Test basic startup/shutdown of Application
     */
    @MediumTest
    public void jsonTest1() {
        sync = new ListQueryManager(mApplication);
        sync.setPageView(1)
                .setURL(Config.newarrivals)
                .execute();


    }

    @MediumTest
    public void jsonTest2() {

        gb.disableHtmlEscaping();
        //gb.registerTypeAdapter(Term.class, new TermDe());
        gb.registerTypeAdapterFactory(new TermFactory());


        //gb.registerTypeAdapterFactory(new GTool.NullStringToEmptyAdapterFactory());
        //gb.setVersion(1.0);

        g = gb.create();


        final outputV1ProductWrap output_product = g.fromJson(rtt, outputV1ProductWrap.class);


        retent.current_product_list.addAll(output_product.getProducts());
        retent.result_total_pages = output_product.totalpages();
        retent.result_current_page = output_product.current_page();
    }

    @MediumTest
    public void jsonTest3() {
        final outputV1ProductWrap output_product = g.fromJson(rtt, outputV1ProductWrap.class);
        retent.result_total_pages = output_product.totalpages();
        retent.result_current_page = output_product.current_page();
    }

    @SmallTest
    public void testPreconditions() {


    }

}