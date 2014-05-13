package eu.codlab.pin;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kevinleperf on 13/05/2014.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PinEntryFragment extends Fragment {

    private PinController _controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.activity_pin_entry_view, container, false);
        _controller = new PinController();
        _controller.init(this, v);
        return v;
    }

}
