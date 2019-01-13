package com.tobyeb.coloredbrowsertabs;

/**
 MIT License
 
 Copyright (c) 2019 Tobias Ebert
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 
 ----------------------------------------------------------------------------------
 
 ColoredBrowserTabs.java
 cordova-plugin-colored-browser-tabs
 
 Created by Tobias Ebert on 12.01.19.
 Copyright © 2019 Tobias Ebert. All rights reserved.
 **/

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

  /**
   * open the chrome custom tabs
   * 
   * @param object
   * @return
   * @throws JSONException
   */
  private boolean openTab(final JSONObject object) throws JSONException {
    if (object == null) {
      return false;
    }
    String url = object.getString("link");
    final String color = object.optString("tabColor");
    final String animation = object.optString("animation");
    // Check if url is defined
    if (TextUtils.isEmpty(url)) {
      return false;
    }

    url = validateUrl(url);
    // Check if url is an valid url
    if (!URLUtil.isValidUrl(url)) {
      return false;
    }

    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
    if (!TextUtils.isEmpty(color)) {
      builder.setToolbarColor(Color.parseColor(color));
    } else {
      builder.setToolbarColor(Color.WHITE);
    }
    if (!TextUtils.isEmpty(animation)) {
      this.addAnimation(animation, builder);
    }
    CustomTabsIntent customTabsIntent = builder.build();
    customTabsIntent.launchUrl(this.cordova.getActivity(), Uri.parse(url));
    return true;
  }

  /**
   * validate the url, because tabs only can open urls which start with https or
   * http
   * 
   * @param url
   * @return
   */
  private String validateUrl(String url) {
    if (!url.startsWith("https://") && !url.startsWith("http://")) {
      url = "https://".concat(url);
    }
    return url;
  }

  /**
   * add an special animation to the tabs
   * 
   * @param animation
   * @param builder
   */
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

  /**
   * return the animation asset from the resources
   * 
   * @param name
   * @return
   */
  private int getAnimRes(final String name) {
    if (this.cordova != null && this.cordova.getActivity() != null) {
      return this.cordova.getActivity().getResources().getIdentifier(name, "anim",
          this.cordova.getActivity().getPackageName());
    }
    return -1;
  }
}
