package com.hb.hkm.hypebeaststore.datamodel.gsontool;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hb.hkm.hypebeaststore.datamodel.V1.TermWrap;

import java.lang.reflect.Type;

/**
 * Created by hesk on 2/13/15.
 */
public class TermDe implements
        JsonDeserializer<TermWrap>,
        InstanceCreator<TermWrap> {

    @Override
    public TermWrap deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject j = (JsonObject) json;
        return new TermWrap(j.has("term") ? j.get("term").getAsString() : "",
                j.get("count").getAsInt());
    }

    @Override
    public TermWrap createInstance(Type type) {
        return new TermWrap("", 0);
    }
}
