function Toast() {
}

Toast.prototype.show = function (message, duration, position, successCallback, errorCallback, closeQueue) {
	cordova.exec(successCallback, errorCallback, "Toast", "show", [message, duration, position, closeQueue]);
};

Toast.prototype.showShortTop = function (message, successCallback, errorCallback, closeQueue) {
	this.show(message, "short", "top", successCallback, errorCallback, closeQueue);
};

Toast.prototype.showShortCenter = function (message, successCallback, errorCallback, closeQueue) {
	this.show(message, "short", "center", successCallback, errorCallback, closeQueue);
};

Toast.prototype.showShortBottom = function (message, successCallback, errorCallback, closeQueue) {
	this.show(message, "short", "bottom", successCallback, errorCallback, closeQueue);
};

Toast.prototype.showLongTop = function (message, successCallback, errorCallback, closeQueue) {
	this.show(message, "long", "top", successCallback, errorCallback, closeQueue);
};

Toast.prototype.showLongCenter = function (message, successCallback, errorCallback, closeQueue) {
	this.show(message, "long", "center", successCallback, errorCallback, closeQueue);
};

Toast.prototype.showLongBottom = function (message, successCallback, errorCallback, closeQueue) {
	this.show(message, "long", "bottom", successCallback, errorCallback, closeQueue);
};

Toast.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.toast = new Toast();
  return window.plugins.toast;
};

cordova.addConstructor(Toast.install);