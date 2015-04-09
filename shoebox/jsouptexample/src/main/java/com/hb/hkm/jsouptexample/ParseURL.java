package com.hb.hkm.jsouptexample;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by hesk on 2/27/15.
 */
public class ParseURL extends AsyncTask<String, Void, String> {

    private enhancedLife me;

    public ParseURL setLifeCycle(enhancedLife e) {
        me = e;
        return this;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuffer buffer = new StringBuffer();
        try {
            Log.d("JSwa", "Connecting to [" + strings[0] + "]");
            Document doc = Jsoup.connect(strings[0]).get();
            Log.d("JSwa", "Connected to [" + strings[0] + "]");
            // Get document (HTML page) title
            String title = doc.title();
            Log.d("JSwA", "Title [" + title + "]");
            buffer.append("Title: " + title + "\r\n");

            // Get meta info
            Elements metaElems = doc.select("meta");
            buffer.append("META DATA\r\n");
            for (Element metaElem : metaElems) {
                String name = metaElem.attr("name");
                String content = metaElem.attr("content");
                buffer.append("name [" + name + "] - content [" + content + "] \r\n");
            }

            Elements topicList = doc.select("h2.topic");
            buffer.append("Topic list\r\n");
            for (Element topic : topicList) {
                String data = topic.text();

                buffer.append("Data [" + data + "] \r\n");
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return buffer.toString();
    }

    private EditText respText;

    public ParseURL setTextField(EditText e) {
        respText = e;
        return this;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        respText.setText(s);
        if (me != null) me.parserDone(this);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (me != null) me.parserStart(this);
    }

}