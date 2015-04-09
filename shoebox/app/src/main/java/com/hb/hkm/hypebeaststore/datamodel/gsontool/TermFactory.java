package com.hb.hkm.hypebeaststore.datamodel.gsontool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;

import java.io.IOException;

/**
 * Created by hesk on 2/17/15.
 */


public class TermFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        //  if (type.getRawType() != Term.class || !(type instanceof ParameterizedType))
        if (!type.getRawType().equals(TermWrap.class))
            return null;
        final TypeAdapter<TermWrap> defaultAdapter = (TypeAdapter<TermWrap>) gson.getDelegateAdapter(this, type);
        return (TypeAdapter<T>) new TermAdapter(defaultAdapter);
    }

    class TermAdapter extends TypeAdapter<TermWrap> {
        protected TypeAdapter<TermWrap> defaultAdapter;

        /**
         * @param defaultAdapter
         */
        public TermAdapter(TypeAdapter<TermWrap> defaultAdapter) {
            this.defaultAdapter = defaultAdapter;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(JsonWriter out, TermWrap value) throws IOException {
            defaultAdapter.write(out, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public TermWrap read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.STRING) {
                in.skipValue();
                return null;
            }
            final GsonBuilder gb = new GsonBuilder();
            gb.registerTypeAdapter(TermWrap.class, new TermDe());
            //return defaultAdapter.read(in);
            final Gson g = gb.create();
            return g.fromJson(in, TermWrap.class);
            /* return new Term(j.has("term") ? j.get("term").getAsString() : "",
                    j.get("count").getAsInt());*/
        }
    }
}

