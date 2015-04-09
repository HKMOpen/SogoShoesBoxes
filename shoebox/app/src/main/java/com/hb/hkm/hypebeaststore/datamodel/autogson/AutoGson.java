package com.hb.hkm.hypebeaststore.datamodel.autogson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks an {@link AutoValue @AutoValue}-annotated type for proper Gson serialization.
 * <p/>
 * This annotation is needed because the {@linkplain Retention retention} of {@code @AutoValue}
 * does not allow reflection at runtime.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoGson {
    // A reference to the AutoValue-generated class (e.g. AutoValue_MyClass). This is
    // necessary to handle obfuscation of the class names.
    public Class autoValueClass();
}