# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/hesk/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# realm.io
-keepnames public class * extends io.realm.RealmObject
-keep class io.realm.** { *; }
-dontwarn javax.**
-dontwarn io.realm.**

# Twowayview
-keep class org.lucasr.twowayview.** { *; }
# Picasso
-dontwarn com.squareup.okhttp.**
# GoldenGate
-keep class * extends com.flipboard.goldengate.JavaScriptBridge { *; }
-keepattributes JavascriptInterface
-keepclassmembers class ** {
    @android.webkit.JavascriptInterface public *;
}

# android-retention-magic - this is required to keep the Annotations
-keepattributes *Annotation*

# android-retention-magic - keep relevant members in Activities
-keepclassmembers class * extends android.app.Activity{
    # optional, keep TAG fields if you use them for automatic namespacing
    # you don't need this line if don't use the "permanent" feature or
    # if you set the namespace like so:
    # @Retain(permanent = true, classNS = TAG)
    # or
    # @Retain(permanent = true, classNS = "someNameSpace")
    java.lang.String TAG;
    # optional, keep names of retained fields
    # you don't need this line if don't use the "permanent" feature or
    # if you set the key manually like in @Retain(key = "someKey");
    @org.dmfs.android.retentionmagic.annotations.* <fields>;
    # optional, keep names of fields considered for the instance names space, adjust to your needs
    # you don't need this line if don't use the "permanent" feature or
    # if you don't use per instance fields
    private java.lang.String instanceTag;
}

# android-retention-magic - same for Fragments
-keepclassmembers class * extends android.app.Fragment{
    java.lang.String TAG;
    @org.dmfs.android.retentionmagic.annotations.* <fields>;
    private java.lang.String instanceTag;
}

# android-retention-magic - same for support library Fragments
-keepclassmembers class * extends android.support.v4.app.Fragment{
    java.lang.String TAG;
    @org.dmfs.android.retentionmagic.annotations.* <fields>;
    private java.lang.String instanceTag;
}