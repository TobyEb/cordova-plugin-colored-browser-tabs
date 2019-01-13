# cordova-plugin-colored-browser-tabs

Cordova Plugin to open Chrome Custom Tabs on Android or Safaris SFSafariViewController on iOS

# **Still in Progress**

@ionic-native bridge is already written, it will be published as soon this plugin is ready for use

## Features

You can define the Toolbar color of the browser tabs, and set if browser tabs should open with an animation or not (Several animations are only available on Android, on iOS you can just define if the default animation should be shown on start)

You don't have to add http or https at the beginning of your url, the plugin will handle this for you, because the tabs require it

## How to use it

```
  constructor(
    private browserTabs: ColoredBrowserTabs
  ) {}

  ...
  this.browserTabs.openTab("https://www.google.com").subscribe();
  or
  this.browserTabs.openTab("https://www.google.com", "#ff0000").subscribe();

  ...
  this.browserTabs.openTabWithAnimation("https://www.google.com", "slide_x").subscribe();
  or
  this.browserTabs.openTabWithAnimation("https://www.google.com", "slide_y").subscribe();
  or
  this.browserTabs.openTabWithAnimation("https://www.google.com", "fade").subscribe();

  this.browserTabs.openTabWithAnimation("https://www.google.com", "slide_x", "#ff0000").subscribe();
```
