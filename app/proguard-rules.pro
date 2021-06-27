# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-dontskipnonpubliclibraryclasses
-verbose
-dontoptimize
-dontshrink
-ignorewarnings
-repackageclasses ''
-allowaccessmodification
-keeppackagenames doNotKeepAThing

-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile, LineNumberTable, *Annotation*, EnclosingMethod


-keep class android.** { *; }
-dontwarn android.**
-keep class androidx.** { *; }
-dontwarn androidx.**
-keep class com.google.** { *; }
-dontwarn com.google.**
-keep class com.facebook.ads.** { *; }
-dontwarn com.facebook.ads.**
-keep class com.airbnb.lottie.** { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

-keepclassmembers,allowshrinking,allowobfuscation class com.android.volley.NetworkDispatcher {
    void processRequest();
}
-keepclassmembers,allowshrinking,allowobfuscation class com.android.volley.CacheDispatcher {
    void processRequest();
}

-keep class com.github.florent37.** { *; }
-keep class com.jaredrummler.** { *; }
#-keep class com.getkeepsafe.taptargetview.** { *; }
-keep class net.alhazmy13.MediaPicker.** { *; }
-keep class com.downloader.** { *; }
-keep class nl.bravobit.ffmpeg.** { *; }
-keep class com.android.vending.billing.** { *; }