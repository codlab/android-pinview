package eu.codlab.pin;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kevinleperf on 09/05/2014.
 */
class PinUpdateController extends BroadcastReceiver {
    private final static int LATEST = 0;
    private final static int NEW = 1;
    private int _current;

    private String _ok;
    private String _error;

    private int userEntered;

    private boolean keyPadLockedFlag = false;

    private TextView titleView;

    private TextView pinBox0;
    private TextView pinBox1;
    private TextView pinBox2;
    private TextView pinBox3;
    private TextView pinBox4;

    private TextView[] pinBoxArray;

    private TextView statusView;

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonExit;
    private Button buttonDelete;

    private Fragment _fragment;
    private android.support.v4.app.Fragment _fragment_support;
    private Activity _activity;


    void onPostExecute() {

        reset();

        keyPadLockedFlag = false;
    }

    private Activity getActivity(){
        Activity activity = null;
        if(_activity != null)
            activity = _activity;
        else if(Build.VERSION.SDK_INT>=11 && _fragment != null && _fragment.getActivity() != null)
            activity = _fragment.getActivity();
        else if(_fragment_support != null && _fragment_support.getActivity() != null)
            activity = _fragment_support.getActivity();
        return activity;
    }
    private void check() {
        Activity activity = getActivity();

        if (activity != null) {

            //just to clear
            LocalBroadcastManager.getInstance(activity).unregisterReceiver(this);
            LocalBroadcastManager.getInstance(activity).registerReceiver(this,
                    new IntentFilter(Constants.EVENT_UPDATE_PONG));


            Intent intent = new Intent(Constants.EVENT_UPDATE_PING);
            intent.putExtra(Constants.EVENT_UPDATE_CONTENT, userEntered);
            LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        }
    }

    private void onNew(){
        Activity activity = getActivity();

        if (activity != null) {

            //just to clear
            LocalBroadcastManager.getInstance(activity).unregisterReceiver(this);
            LocalBroadcastManager.getInstance(activity).registerReceiver(this,
                    new IntentFilter(Constants.EVENT_UPDATE_PONG));


            Intent intent = new Intent(Constants.EVENT_UPDATE_PING);
            intent.putExtra(Constants.EVENT_UPDATE_RESULT, userEntered);
            LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        }
    }

    private void onOk() {
        statusView.setTextColor(Color.GREEN);
        statusView.setText(_ok);
        onCorrect();
    }

    private void onError() {
        statusView.setTextColor(Color.RED);
        statusView.setText(_error);
        keyPadLockedFlag = true;

        new LockKeyPadOperation(PinUpdateController.this).execute("");
    }

    private void onExit() {
        Activity activity = getActivity();

        if (activity != null) {
            Intent intent = new Intent(Constants.EVENT_UPDATE_EXIT);
            intent.putExtra(Constants.EVENT_UPDATE_EXIT, userEntered);
            LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
        }
        if (_activity != null) _activity.finish();
    }

    private void onDelete() {
        if (keyPadLockedFlag == true) {
            return;
        }

        if (userEntered > 0) {
            pinBoxArray[getIndex()].setText("");
            userEntered/=10;
        }
    }

    private int getIndex() {
        if (userEntered < 10) return 0;
        else if (userEntered < 100) return 1;
        else if (userEntered < 1000) return 2;
        else if (userEntered < 10000) return 3;
        else if (userEntered < 100000) return 4;
        return 0;
    }

    private void onClick(Button pressedButton) {
        if (keyPadLockedFlag == true) {
            return;
        }


        if (userEntered <= 10000) {
            userEntered = userEntered * 10 + Integer.parseInt(pressedButton.getText().toString());

            //Update pin boxes

            pinBoxArray[getIndex()].setText("8");
            if (userEntered >= 10000) {
                if(_current == LATEST) {
                    check();
                }else{
                    onNew();
                }
            }
        } else {
            reset();
            userEntered = userEntered * 10 + Integer.parseInt(pressedButton.getText().toString());
            pinBoxArray[getIndex()].setText("8");

        }
    }

    private void reset(){

        //Roll over
        pinBoxArray[0].setText("");
        pinBoxArray[1].setText("");
        pinBoxArray[2].setText("");
        pinBoxArray[3].setText("");
        pinBoxArray[4].setText("");

        userEntered = 0;

        statusView.setText("");
    }


    private void setToNew(){
        _current = NEW;
        if(_activity != null)((TextView)_activity.findViewById(R.id.titleBox)).setText(R.string.title_new);
        else if(_fragment_support != null)((TextView)_fragment_support.getView().findViewById(R.id.titleBox)).setText(R.string.title_new);
        else if(Build.VERSION.SDK_INT >=11 && _fragment != null)((TextView)_fragment.getView().findViewById(R.id.titleBox)).setText(R.string.title_new);
    }

    private void setToPrevious(){
        _current = LATEST;
        if(_activity != null)((TextView)_activity.findViewById(R.id.titleBox)).setText(R.string.title_previous);
        else if(_fragment_support != null)((TextView)_fragment_support.getView().findViewById(R.id.titleBox)).setText(R.string.title_previous);
        else if(Build.VERSION.SDK_INT >=11 && _fragment != null)((TextView)_fragment.getView().findViewById(R.id.titleBox)).setText(R.string.title_previous);
    }

    private void onCorrect() {
        if (_current == NEW && _activity != null && !_activity.isFinishing()) {
            _activity.finish();
        }else if(_current == LATEST){
            setToNew();
            reset();
            userEntered = 0;
        }
    }

    void init(android.support.v4.app.Fragment fragment, View activity) {
        _fragment_support = fragment;
        buttonExit = (Button) activity.findViewById(R.id.buttonExit);
        buttonDelete = (Button) activity.findViewById(R.id.buttonDeleteBack);
        titleView = (TextView) activity.findViewById(R.id.titleBox);

        pinBox0 = (TextView) activity.findViewById(R.id.pinBox0);
        pinBox1 = (TextView) activity.findViewById(R.id.pinBox1);
        pinBox2 = (TextView) activity.findViewById(R.id.pinBox2);
        pinBox3 = (TextView) activity.findViewById(R.id.pinBox3);
        pinBox4 = (TextView) activity.findViewById(R.id.pinBox4);

        button0 = (Button) activity.findViewById(R.id.button0);
        button1 = (Button) activity.findViewById(R.id.button1);
        button2 = (Button) activity.findViewById(R.id.button2);
        button3 = (Button) activity.findViewById(R.id.button3);
        button4 = (Button) activity.findViewById(R.id.button4);
        button5 = (Button) activity.findViewById(R.id.button5);
        button6 = (Button) activity.findViewById(R.id.button6);
        button7 = (Button) activity.findViewById(R.id.button7);
        button8 = (Button) activity.findViewById(R.id.button8);
        button9 = (Button) activity.findViewById(R.id.button9);
        buttonDelete = (Button) activity.findViewById(R.id.buttonDeleteBack);
        statusView = (TextView) activity.findViewById(R.id.statusMessage);

        endInit();
    }
    void init(Fragment fragment, View activity) {
        _fragment = fragment;
        buttonExit = (Button) activity.findViewById(R.id.buttonExit);
        buttonDelete = (Button) activity.findViewById(R.id.buttonDeleteBack);
        titleView = (TextView) activity.findViewById(R.id.titleBox);

        pinBox0 = (TextView) activity.findViewById(R.id.pinBox0);
        pinBox1 = (TextView) activity.findViewById(R.id.pinBox1);
        pinBox2 = (TextView) activity.findViewById(R.id.pinBox2);
        pinBox3 = (TextView) activity.findViewById(R.id.pinBox3);
        pinBox4 = (TextView) activity.findViewById(R.id.pinBox4);

        button0 = (Button) activity.findViewById(R.id.button0);
        button1 = (Button) activity.findViewById(R.id.button1);
        button2 = (Button) activity.findViewById(R.id.button2);
        button3 = (Button) activity.findViewById(R.id.button3);
        button4 = (Button) activity.findViewById(R.id.button4);
        button5 = (Button) activity.findViewById(R.id.button5);
        button6 = (Button) activity.findViewById(R.id.button6);
        button7 = (Button) activity.findViewById(R.id.button7);
        button8 = (Button) activity.findViewById(R.id.button8);
        button9 = (Button) activity.findViewById(R.id.button9);
        buttonDelete = (Button) activity.findViewById(R.id.buttonDeleteBack);
        statusView = (TextView) activity.findViewById(R.id.statusMessage);

        endInit();
    }

    void init(Activity activity) {
        _activity = activity;
        buttonExit = (Button) activity.findViewById(R.id.buttonExit);
        buttonDelete = (Button) activity.findViewById(R.id.buttonDeleteBack);
        titleView = (TextView) activity.findViewById(R.id.titleBox);

        pinBox0 = (TextView) activity.findViewById(R.id.pinBox0);
        pinBox1 = (TextView) activity.findViewById(R.id.pinBox1);
        pinBox2 = (TextView) activity.findViewById(R.id.pinBox2);
        pinBox3 = (TextView) activity.findViewById(R.id.pinBox3);
        pinBox4 = (TextView) activity.findViewById(R.id.pinBox4);

        button0 = (Button) activity.findViewById(R.id.button0);
        button1 = (Button) activity.findViewById(R.id.button1);
        button2 = (Button) activity.findViewById(R.id.button2);
        button3 = (Button) activity.findViewById(R.id.button3);
        button4 = (Button) activity.findViewById(R.id.button4);
        button5 = (Button) activity.findViewById(R.id.button5);
        button6 = (Button) activity.findViewById(R.id.button6);
        button7 = (Button) activity.findViewById(R.id.button7);
        button8 = (Button) activity.findViewById(R.id.button8);
        button9 = (Button) activity.findViewById(R.id.button9);
        buttonDelete = (Button) activity.findViewById(R.id.buttonDeleteBack);
        statusView = (TextView) activity.findViewById(R.id.statusMessage);

        endInit();

    }

    private void endInit() {


        Activity activity = getActivity();
        setToPrevious();
        if(activity != null) {
            _ok = activity.getString(R.string.ok);
            _error = activity.getString(R.string.error);
        }
        pinBoxArray = new TextView[5];
        pinBoxArray[0] = pinBox0;
        pinBoxArray[1] = pinBox1;
        pinBoxArray[2] = pinBox2;
        pinBoxArray[3] = pinBox3;
        pinBoxArray[4] = pinBox4;


        buttonExit.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              onExit();
                                          }

                                      }
        );

        buttonDelete.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                onDelete();
                                            }

                                        }
        );


        View.OnClickListener pinButtonHandler = new View.OnClickListener() {
            public void onClick(View v) {
                PinUpdateController.this.onClick((Button) v);
            }
        };


        button0.setOnClickListener(pinButtonHandler);
        button1.setOnClickListener(pinButtonHandler);
        button2.setOnClickListener(pinButtonHandler);
        button3.setOnClickListener(pinButtonHandler);
        button4.setOnClickListener(pinButtonHandler);
        button5.setOnClickListener(pinButtonHandler);
        button6.setOnClickListener(pinButtonHandler);
        button7.setOnClickListener(pinButtonHandler);
        button8.setOnClickListener(pinButtonHandler);
        button9.setOnClickListener(pinButtonHandler);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Constants.EVENT_PONG.equals(intent.getAction()) && intent.hasExtra(Constants.EVENT_CONTENT)) {
            if (intent.getIntExtra(Constants.EVENT_CONTENT, Constants.EVENT_PONG_ERROR) == Constants.EVENT_PONG_OK) {
                onOk();
            } else {
                onError();
            }
        }
    }
}
