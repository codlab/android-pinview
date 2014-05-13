package eu.codlab.pin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by kevinleperf on 09/05/2014.
 */
public class PinUpdateHelper extends BroadcastReceiver{
    private IPinUpdateListener _listener;

    PinUpdateHelper(IPinUpdateListener listener){
        _listener = listener;
    }
    public void register(Context context){
        LocalBroadcastManager.getInstance(context).registerReceiver(this,
                new IntentFilter(Constants.EVENT_UPDATE_PING));
        LocalBroadcastManager.getInstance(context).registerReceiver(this,
                new IntentFilter(Constants.EVENT_UPDATE_EXIT));
    }

    public void unregister(Context context, BroadcastReceiver broadcastreceiver){
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
    }

    public void startInput(Activity activity){
        Intent intent = new Intent(activity, PinEntryActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Constants.EVENT_UPDATE_PING.equals(intent.getAction()) && intent.hasExtra(Constants.EVENT_UPDATE_CONTENT)){
            Intent send = new Intent(Constants.EVENT_UPDATE_PONG);
            send.setAction(Constants.EVENT_UPDATE_PONG);
            send.putExtra(Constants.EVENT_UPDATE_CONTENT, _listener.onPinEntered(intent.getIntExtra(Constants.EVENT_CONTENT,0)) ?
                    Constants.EVENT_PONG_OK: Constants.EVENT_PONG_ERROR);
            Log.d("PinView",send.getAction());
            LocalBroadcastManager.getInstance(_listener.getListenerContext()).sendBroadcast(send);
        }else if(Constants.EVENT_UPDATE_PING.equals(intent.getAction()) && intent.hasExtra(Constants.EVENT_UPDATE_RESULT)){
            _listener.onPinChanged(intent.getIntExtra(Constants.EVENT_UPDATE_RESULT,0));
            Intent send = new Intent(Constants.EVENT_UPDATE_PONG);
            send.setAction(Constants.EVENT_UPDATE_PONG);
            send.putExtra(Constants.EVENT_UPDATE_CONTENT, _listener.onPinEntered(intent.getIntExtra(Constants.EVENT_UPDATE_RESULT,0)) ?
                    Constants.EVENT_PONG_OK: Constants.EVENT_PONG_ERROR);
            LocalBroadcastManager.getInstance(_listener.getListenerContext()).sendBroadcast(send);


        }else if(Constants.EVENT_UPDATE_EXIT.equals(intent.getAction())){
            _listener.onExit();
        }
    }
}
