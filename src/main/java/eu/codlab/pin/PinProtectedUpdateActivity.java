package eu.codlab.pin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PinProtectedUpdateActivity extends Activity implements IPinUpdateListener {

    Button enterPin;
    Context appContext;
    Handler _handler;
    int _pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _handler = new Handler(Looper.getMainLooper());
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        appContext = this;

        _pin = 98632;
        setContentView(R.layout.activity_pin_protected_activity);

        enterPin = (Button) findViewById(R.id.buttonEnterPin);


        final PinUpdateHelper checker = new PinUpdateHelper(this);
        checker.register(this);

        enterPin.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            checker.startInput(PinProtectedUpdateActivity.this);
                                        }

                                    }
        );


    }


    @Override
    public boolean onPinEntered(int pin) {
        Log.d("PinView", _pin + " "+pin);
        return pin == _pin;
    }

    @Override
    public boolean onPinChanged(int pin) {
        _pin = pin;
        return _pin == pin;
    }

    @Override
    public void onExit() {
        _handler.post(new Runnable() {
            public void run() {
                Toast.makeText(PinProtectedUpdateActivity.this, "exited", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public Context getListenerContext() {
        return this;
    }

    @Override
    public boolean hasPreviousPin() {
        return _pin != 98632;
    }
}
