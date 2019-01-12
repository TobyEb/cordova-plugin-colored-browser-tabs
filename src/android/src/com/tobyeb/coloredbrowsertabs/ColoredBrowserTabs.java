package com.tobyeb.coloredbrowsertabs;

import android.app.Application;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;
import android.webkit.URLUtil;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ColoredBrowserTabs extends CordovaPlugin {

  private final static String SLIDE_X = "slide_x";
  private final static String SLIDE_Y = "slide_y";
  private final static String FADE = "fade";

  private final String TAG = "ColoredBrowserTabs";

  private Application mApp;

  @Override
  protected void pluginInitialize() {
    super.pluginInitialize();
  }

  @Override
  public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
    final JSONObject object = data.optJSONObject(0);
    if (action.equals("openTab")) {
      return this.openTab(object);
    }
    return false;
  }

  private boolean openTab(final JSONObject object) throws JSONException {
    if (object == null) {
      return false;
    }
    final String url = object.getString("link");
    final String color = object.optString("tabColor");
    final String animation = object.optString("animation");
    if (TextUtils.isEmpty(url) && !validUrl(url)) {
      return false;
    }

    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
    if (!TextUtils.isEmpty(color)) {
      builder.setToolbarColor(Color.parseColor(color));
    }
    if (!TextUtils.isEmpty(animation)) {
      this.addAnimation(animation, builder);
    }
    CustomTabsIntent customTabsIntent = builder.build();
    customTabsIntent.launchUrl(this.cordova.getActivity(), Uri.parse(url));
    return true;
  }

  private boolean validUrl(String url) {
    if (!url.startsWith("https://") && !url.startsWith("http://")) {
      url = "https://".concat(url);
    }
    return URLUtil.isValidUrl(url);
  }

  private void addAnimation(final String animation, final CustomTabsIntent.Builder builder) {
    if (SLIDE_X.equals(animation)) {
      builder.setStartAnimations(this.cordova.getContext(), this.getAnimRes("slide_in_right"),
          this.getAnimRes("slide_out_left"));
      builder.setExitAnimations(this.cordova.getContext(), this.getAnimRes("slide_in_left"),
          this.getAnimRes("slide_out_right"));
    } else if (SLIDE_Y.equals(animation)) {
      builder.setStartAnimations(this.cordova.getContext(), this.getAnimRes("slide_up"), this.getAnimRes("fade_out"));
      builder.setExitAnimations(this.cordova.getContext(), this.getAnimRes("fade_in"), this.getAnimRes("slide_down"));
    } else if (FADE.equals(animation)) {
      builder.setStartAnimations(this.cordova.getContext(), this.getAnimRes("fade_in"), this.getAnimRes("fade_out"));
      builder.setExitAnimations(this.cordova.getContext(), this.getAnimRes("fade_in"), this.getAnimRes("fade_out"));
    }
  }

  private int getAnimRes(final String name) {
    if (this.cordova != null && this.cordova.getActivity() != null) {
      return this.cordova.getActivity().getResources().getIdentifier(name, "anim",
          this.cordova.getActivity().getPackageName());
    }
    return -1;
  }
}
