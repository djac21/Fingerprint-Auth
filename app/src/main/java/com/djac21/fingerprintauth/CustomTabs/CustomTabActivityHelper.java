package com.djac21.fingerprintauth.CustomTabs;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

public class CustomTabActivityHelper {

    public interface CustomTabFallback {
        void openUri(Context activity, Uri uri);
    }

    public static void openCustomTab(Context activity, CustomTabsIntent customTabsIntent,
                                     Uri uri, CustomTabFallback fallback) {
        String packageName = CustomTabsHelper.getPackageNameToUse(activity);

        if (packageName == null) {
            if (fallback != null) {
                fallback.openUri(activity, uri);
            }
        } else {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        }
    }
}