##---------------Begin: proguard configuration common for all Android apps ----------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-dump build/outputs/class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses 'o'


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService


-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

# The official support library.
#-keep class android.support.** { *; }
#-keep class com.android.support.** { *; }
#-keep class android.support.v4.app.** { *; }
#-keep interface android.support.v4.app.** { *; }


# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


# Preserve all native method names and the names of their classes.
#-keepclasseswithmembernames class * {
#    native <methods>;
#}


-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}


-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}


# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


-keep public class * {
    public protected *;
}


-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##---------------End: proguard configuration common for all Android apps ----------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }


# Application classes that will be serialized/deserialized over Gson
-keep class enetviet.corp.qi.data.entity.** { *; }
#-keep class enetviet.corp.qi.config.** { *; }
#-keep class enetviet.corp.qi.infor.** { *; }
#-keep class enetviet.corp.qi.data.source.remote.service.** { *; }
#-keep class enetviet.corp.qi.data.source.remote.request.** { *; }
#-keep class enetviet.corp.qi.data.source.remote.response.** { *; }


##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for Third party library  ----------
-keep class com.google.** { *; }
-keep class com.github.** { *; }

-keep class com.bumptech.glide.** { *; }
-keep class com.crashlytics.** { *; }
-keep class com.marshalchen.ultimaterecyclerview.** { *; }
-keep class com.squareup.** { *; }
-keep class com.zhihu.matisse.** { *; }
-keep class org.codehaus.mojo.** { *; }
-keep class de.hdodenhof.** { *; }
-keep class com.nineoldandroids.** { *; }
-keep class me.leolin.** { *; }
-keep class com.arasthel.** { *; }
-keep class net.cachapa.expandablelayout.** { *; }
-keep class org.jetbrains.kotlin.** { *; }
-keep class io.reactivex.rxjava2.** { *; }
-keep class android.arch.** { *; }

# Suppress warnings if you are NOT using IAP:
-dontwarn com.google.**
-dontwarn com.github.**
-dontwarn android.support.**
-dontwarn okio.**
-dontwarn com.crashlytics.**
-dontwarn com.marshalchen.ultimaterecyclerview.**
-dontwarn com.zhihu.matisse.**
-dontwarn org.codehaus.mojo.**

-ignorewarnings
##---------------End: proguard configuration for Third party library  ----------
