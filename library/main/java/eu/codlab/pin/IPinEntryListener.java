package eu.codlab.pin;

import android.content.Context;

/**
 * How to use :
 *
 * onExit() > the user requested an exit, must be managed by the app
 *
 * onPinEntered(int) > the listener must check if the result is ok AND return if true of false
 * to notify the manager it is ok
 *
 * getListenerContext() must return a valid Context for the time of the operations
 *
 * Created by kevinleperf on 09/05/2014.
 */
public interface IPinEntryListener {
    public boolean onPinEntered(int pin);
    public void onExit();
    public Context getListenerContext();
}
