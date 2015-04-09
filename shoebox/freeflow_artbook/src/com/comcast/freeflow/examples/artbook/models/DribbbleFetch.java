/*******************************************************************************
 * Copyright 2013 Comcast Cable Communications Management, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.comcast.freeflow.examples.artbook.models;

import android.os.AsyncTask;
import android.util.Log;

import com.comcast.freeflow.examples.artbook.ArtbookActivity;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;

public class DribbbleFetch {

    public static final String TAG = "DribbbleFetch";
    protected HttpParams httpParams;

    public DribbbleFetch() {
        httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
    }

    public void load(final ArtbookActivity caller, int itemsPerPage, int page) {

        new AsyncTask<String, Void, String>() {

            OkHttpClient client = new OkHttpClient();

            private Exception exception;

            private Response makeUrlRequest(String url) throws IOException {
                Request.Builder request = new Request.Builder()
                        .url(url)
                        .header("Accept", "application/json");

                final Response response = client.newCall(request.build()).execute();
                return response;
            }


            protected String doInBackground(String... urls) {
                try {
                    String body;
                    Response r = makeUrlRequest(urls[0]);
                    int res_code = r.code();
                    if (res_code == 200) {
                        body = r.body().string();
                        /**
                         I just solved the problem. It was somewhat misleading that the cache tests where failing when I tried to use OkHttp from source.
                         The problem was quite easy and it was that other of the request methods was getting a body on the response, and it wasn't closed at the end. That explains why I saw the ".tmp" file in the cache, but still a confusing and misleading because of the fact that this request method was consuming and closing the body from the response. Its like the lock or monitor for the cache editor is global for all requests, instead of being by request. I though it wasn't when I read the code, when it used a hash for the request as a key.
                         */
                        r.body().close();
                    } else {
                        Log.d(TAG, "error code: " + res_code);
                        Log.d(TAG, "found error on curl: " + urls[0]);
                        throw new Exception("server error: " + res_code);
                    }
                    return body;
                } catch (Exception e) {
                    this.exception = e;
                    Log.e(TAG, "Exception: " + e);
                    return null;
                }
            }

            protected void onPostExecute(String data) {
                DribbbleFeed feed = new Gson().fromJson(data, DribbbleFeed.class);
                caller.onDataLoaded(feed);
            }

        }.execute("http://api.dribbble.com/shots/popular?per_page=" + itemsPerPage + "&page=" + page);
    }

}
