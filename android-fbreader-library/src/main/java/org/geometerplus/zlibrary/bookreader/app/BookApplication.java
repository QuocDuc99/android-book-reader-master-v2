package org.geometerplus.zlibrary.bookreader.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;

import com.github.axet.androidlibrary.app.MainApplication;

import org.geometerplus.zlibrary.library.ZLAndroidApplication;

public class BookApplication /*extends MainApplication*/ {
    public static String PREFERENCE_THEME = "theme";
    public static String PREFERENCE_FONTFAMILY_FBREADER = "fontfamily_fb";
    public static String PREFERENCE_FONTSIZE_FBREADER = "fontsize_fb";
    public static String PREFERENCE_FONTSIZE_REFLOW = "fontsize_reflow";
    public static float PREFERENCE_FONTSIZE_REFLOW_DEFAULT = 0.8f;
    public static String PREFERENCE_LIBRARY_LAYOUT = "layout_";
    public static String PREFERENCE_SCREENLOCK = "screen_lock";
    public static String PREFERENCE_VOLUME_KEYS = "volume_keys";
    public static String PREFERENCE_LAST_PATH = "last_path";
    public static String PREFERENCE_ROTATE = "rotate";
    public static String PREFERENCE_VIEW_MODE = "view_mode";
    public static String PREFERENCE_STORAGE = "storage_path";
    public static String PREFERENCE_SORT = "sort";
    public static String PREFERENCE_LANGUAGE = "tts_pref";
    public static String PREFERENCE_IGNORE_EMBEDDED_FONTS = "ignore_embedded_fonts";
    public static String PREFERENCE_FONTS_FOLDER = "fonts_folder";

    public ZLAndroidApplication zlib;
    public TTFManager ttf;

    public static MainApplication from(Context context) {
        return MainApplication.from(context);
    }

    public static int getTheme(Context context, int light, int dark) {
        return MainApplication.getTheme(context, PREFERENCE_THEME, light, dark);
    }
    public static String formatSize(Context context,long s){
        return MainApplication.formatSize(context,s);
    }

    public BookApplication(Context context) {
//        zlib = new ZLAndroidApplication() {
//            {
//                attachBaseContext(BookApplication.this);
//                onCreate();
//            }
//        };
        ttf = new TTFManager(context);
        if (Build.VERSION.SDK_INT >= 21) {
            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
            String fonts = shared.getString(BookApplication.PREFERENCE_FONTS_FOLDER, "");
            if (fonts != null && !fonts.isEmpty()) {
                Uri u = Uri.parse(fonts);
                Storage.takePersistableUriPermission(context, u, Storage.SAF_RW);
                ttf.setFolder(u);
            }
        }
        ttf.preloadFonts();
    }
   /* @Override
    public void onCreate() {
        super.onCreate();
        zlib = new ZLAndroidApplication() {
            {
                attachBaseContext(BookApplication.this);
                onCreate();
            }
        };
        ttf = new TTFManager(this);
        if (Build.VERSION.SDK_INT >= 21) {
            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
            String fonts = shared.getString(BookApplication.PREFERENCE_FONTS_FOLDER, "");
            if (fonts != null && !fonts.isEmpty()) {
                Uri u = Uri.parse(fonts);
                Storage.takePersistableUriPermission(this, u, Storage.SAF_RW);
                ttf.setFolder(u);
            }
        }
        ttf.preloadFonts();
    }*/
}
