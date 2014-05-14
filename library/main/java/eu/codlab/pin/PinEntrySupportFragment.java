package eu.codlab.pin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kevinleperf on 13/05/2014.
 */
public class PinEntrySupportFragment extends Fragment {

    private PinController _controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.activity_pin_entry_view, container, false);
        _controller = new PinController();
        _controller.init(this, v);
        return v;
    }
}
