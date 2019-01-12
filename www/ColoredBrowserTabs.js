/*global cordova, module*/
const MODULE = "ColoredBrowserTabs";

module.exports = {
  openTab: function(url, color, successCallback, errorCallback) {
    if (color) {
      cordova.exec(successCallback, errorCallback, MODULE, "openTab", [
        { link: url, tabColor: color }
      ]);
    }
  },

  openTabWithAnimation: function(
    url,
    anim,
    color,
    successCallback,
    errorCallback
  ) {
    if (color) {
      cordova.exec(successCallback, errorCallback, MODULE, "openTab", [
        { link: url, animation: anim, tabColor: color }
      ]);
    } else {
      cordova.exec(successCallback, errorCallback, MODULE, "openTab", [
        { link: url, animation: anim }
      ]);
    }
  }
};
