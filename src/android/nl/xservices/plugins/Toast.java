package nl.xservices.plugins;

import android.os.Handler;
import android.view.Gravity;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

/*
    // TODO nice way for the Toast plugin to offer a longer delay than the default short and long options
    // TODO also look at https://github.com/JohnPersano/Supertoasts
    new CountDownTimer(6000, 1000) {
      public void onTick(long millisUntilFinished) {toast.show();}
      public void onFinish() {toast.show();}
    }.start();

    Also, check https://github.com/JohnPersano/SuperToasts
 */
public class Toast extends CordovaPlugin {

  private static final String ACTION_SHOW_EVENT = "show";
  private static android.widget.Toast _Toast;

  private android.widget.Toast mostRecentToast;

  // note that webView.isPaused() is not Xwalk compatible, so tracking it poor-man style
  private boolean isPaused;

  @Override
  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (ACTION_SHOW_EVENT.equals(action)) {

      if (this.isPaused) {
        return true;
      }

      final String message = args.getString(0);
      final String duration = args.getString(1);
      final String position = args.getString(2);
      final String closeQueue = args.length() > 3 ? args.getString(3) : null;
	  //LOG.d("a", "INSIDE execute(), OUTSIDE run()");
	  //LOG.d("a", args.toString());
	  //LOG.d("a", closeQueue != null ? "closeQueue = OK" : "closeQueue NOT OK");
	  //LOG.d("a", Toast._Toast != null ? "toast = OK" : "toast NOT HERE");

      cordova.getActivity().runOnUiThread(new Runnable() {
          
  		public void run() {
		  //LOG.d("a", "INSIDE run()");
  		  //LOG.d("a", closeQueue != null ? "closeQueue = OK" : "closeQueue NOT OK");
  		  //LOG.d("a", Toast._Toast != null ? "toast = OK" : "toast NOT HERE");
  		  if (closeQueue != null && Toast._Toast != null) {// && Toast._Toast.getView() != null && Toast._Toast.getView().isShown()) {
  			 Toast._Toast.cancel();
  			 //Toast._Toast.setText(message);
  		  }
          
    		  new Handler().postDelayed(new Runnable() {
    	    	 	public void run() {
    	    	          android.widget.Toast toast = android.widget.Toast.makeText(webView.getContext(), message, 0);
						  Toast._Toast = toast;

    	    	            if ("top".equals(position)) {
    	    	              toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 20);
    	    	            } else  if ("bottom".equals(position)) {
    	    	              toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 20);
    	    	            } else if ("center".equals(position)) {
    	    	              toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
    	    	            } else {
    	    	              callbackContext.error("invalid position. valid options are 'top', 'center' and 'bottom'");
            return;
    	    	            }

    	    	            if ("short".equals(duration)) {
    	    	              toast.setDuration(android.widget.Toast.LENGTH_SHORT);
    	    	            } else if ("long".equals(duration)) {
    	    	              toast.setDuration(android.widget.Toast.LENGTH_LONG);
    	    	            } else {
    	    	              callbackContext.error("invalid duration. valid options are 'short' and 'long'");
            return;
    	    	            }

    	    	            toast.show();
          mostRecentToast = toast;
    	    	            callbackContext.success();
  	 		
    	    	 	}
    	      }, 10);
    	  }
      });
    

      return true;
    } else {
      callbackContext.error("toast." + action + " is not a supported function. Did you mean '" + ACTION_SHOW_EVENT + "'?");
      return false;
    }
  }

  @Override
  public void onPause(boolean multitasking) {
    if (mostRecentToast != null) {
      mostRecentToast.cancel();
    }
    this.isPaused = true;
  }

  @Override
  public void onResume(boolean multitasking) {
    this.isPaused = false;
  }
}