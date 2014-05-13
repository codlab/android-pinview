package eu.codlab.pin;

import android.content.Context;

/**
 * How to use :
 *
 * onExit() > the user requested an exit, must be managed by the app
 *
 * onPinChanged(int) > the new pin
 *
 * getListenerContext() must return a valid Context for the time of the operations
 *
 * Created by kevinleperf on 09/05/2014.
 */
public interface IPinUpdateListener {
    public boolean onPinEntered(int pin);
    public boolean onPinChanged(int pin);
    public void onExit();
    public Context getListenerContext();
}
