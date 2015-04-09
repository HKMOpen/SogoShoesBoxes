package com.hb.hkm.hypebeaststore.endpointmanagers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by hesk on 2/27/15.
 */
public abstract class HTMLDomReader extends AsyncTask<String, Void, String> {
    private callback me;
    protected Context ctx;
    protected Document bigDoc;
    protected boolean debug = false;

    public interface callback {
        public void parserDone(final String data, final HTMLDomReader tasker);

        public void parserDoneFailure(final String message);

        public void requestStart(final HTMLDomReader task);
    }

    public HTMLDomReader(Context ccc, callback cb) {
        ctx = ccc;
        me = cb;
    }

    protected Document getDoc() throws Exception {
        if (bigDoc == null) {
            throw new Exception("document is not constructed");
        } else {
            return bigDoc;
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuffer buffer = new StringBuffer();
        try {
            if (debug) Log.d("JSwa", "Connecting to [" + strings[0] + "]");
            bigDoc = Jsoup.connect(strings[0]).followRedirects(true).get();
            if (debug) {
                Log.d("JSwa", "Connected to [" + strings[0] + "]");
                // Get document (HTML page) title
                String title = bigDoc.title();
                Log.d("JSwA", "Title [" + title + "]");
                buffer.append("Title: " + title + "\r\n");
                // Get meta info
                Elements metaElems = bigDoc.select("meta");
                buffer.append("META DATA\r\n");
                for (Element metaElem : metaElems) {
                    String name = metaElem.attr("name");
                    String content = metaElem.attr("content");
                    buffer.append("name [" + name + "] - content [" + content + "] \r\n");
                }

                Elements topicList = bigDoc.select("h2.topic");

                buffer.append("Topic list\r\n");
                for (Element topic : topicList) {
                    String data = topic.text();
                    buffer.append("Data [" + data + "] \r\n");
                }
            } else {
                buffer.append("Data success");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return buffer.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (me != null) me.parserDone(s, this);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (me != null) me.requestStart(this);
    }

}