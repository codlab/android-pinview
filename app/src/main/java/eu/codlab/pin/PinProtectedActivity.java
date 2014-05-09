package eu.codlab.pin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PinProtectedActivity extends Activity implements IPinEntryListener {

    Button enterPin;
    Context appContext;
    Handler _handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _handler = new Handler(Looper.getMainLooper());
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        appContext = this;

        setContentView(R.layout.activity_pin_protected_activity);

        enterPin = (Button) findViewById(R.id.buttonEnterPin);


        final PinCheckHelper checker = new PinCheckHelper(this);
        checker.register(this);

        enterPin.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            checker.startInput(PinProtectedActivity.this);
                                        }

                                    }
        );


    }


    @Override
    public boolean onPinEntered(int pin) {
        return pin == 98632;
    }

    @Override
    public void onExit() {
        _handler.post(new Runnable() {
            public void run() {
                Toast.makeText(PinProtectedActivity.this, "exited", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public Context getListenerContext() {
        return this;
    }
}
