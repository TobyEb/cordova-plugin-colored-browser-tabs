# ⚠️ Deprecated and not longer maintained, feel free to fork and make improvements

# cordova-plugin-colored-browser-tabs

Cordova Plugin to open Chrome Custom Tabs on Android or Safaris SFSafariViewController on iOS

## Features

You can define the Toolbar color of the browser tabs, and set if browser tabs should open with an animation or not (Several animations are only available on Android, on iOS you can just define if the default animation should be shown on start)

You don't have to add http or https at the beginning of your url, the plugin will handle this for you (default is https)

The colors you pass in the plugin need to be hex colors, for example: '#000000'

## Installation

1. Install the Cordova and Ionic Native Plugins

```
$ ionic cordova plugin add cordova-plugin-colored-browser-tabs
$ npm install --save @ionic-native/colored-browser-tabs
```

2. [Add this plugin to your app's module](https://ionicframework.com/docs/native/#Add_Plugins_to_Your_App_Module)

## Supported Platforms

- Android
- iOS

## Usage

```
import { ColoredBrowserTabs } from '@ionic-native/colored-browser-tabs';

  constructor(
    private browserTabs: ColoredBrowserTabs
  ) {}

  ...
  // Without animation and default color
  this.browserTabs.openTab("https://www.google.com").subscribe(() => {
    // do whatever you want to do on success
  },
  (error) => {
    // handle the error callback here, e.g. open external default browser
  });
  or
  // Without animation and custom color
  this.browserTabs.openTab("https://www.google.com", "#ff0000").subscribe(() => {
    // do whatever you want to do on success
  },
  (error) => {
    // handle the error callback here, e.g. open external default browser
  });

  // With animation and default color
  this.browserTabs.openTabWithAnimation("https://www.google.com", "slide_x").subscribe(() => {
    // do whatever you want to do on success
  },
  (error) => {
    // handle the error callback here, e.g. open external default browser
  });
  or
  this.browserTabs.openTabWithAnimation("https://www.google.com", "slide_y").subscribe(() => {
    // do whatever you want to do on success
  },
  (error) => {
    // handle the error callback here, e.g. open external default browser
  });
  or
  this.browserTabs.openTabWithAnimation("https://www.google.com", "fade").subscribe(() => {
    // do whatever you want to do on success
  },
  (error) => {
    // handle the error callback here, e.g. open external default browser
  });
  or
  // With animation and custom color
  this.browserTabs.openTabWithAnimation("https://www.google.com", "slide_x", "#ff0000").subscribe(() => {
    // do whatever you want to do on success
  },
  (error) => {
    // handle the error callback here, e.g. open external default browser
  });
```

## Instance Members

```
openTab(url: string, color?: string)
```

opens Tab with specific url and color

| Param | Type | Details|
|-------|------|--------|
| url   | string | the url you want to open|
| color | string | (Optional) the color you want the tab to have |

```
openTabWithAnimation(url: string, anim: string, color?: string)
```

opens Tab with an animation

| Param | Type | Details|
|-------|------|--------|
| url | string | the url you want to open|
| anim | string | the animation you want to show - for android you can chose between "slide_x", "slide_y" and "fade" - iOS will always show the default animation |
| color | string | (Optional) the color you want the tab to have |
