package eu.codlab.pin;

import android.os.AsyncTask;

/**
 * Created by kevinleperf on 09/05/2014.
 */
class LockKeyPadOperation extends AsyncTask<String, Void, String> {

    private final PinController _controller;
    private final PinUpdateController _controller2;

    LockKeyPadOperation(PinController controller){
        _controller = controller;
        _controller2 = null;
    }

    public LockKeyPadOperation(PinUpdateController pinUpdateController) {
        _controller2 = pinUpdateController;
        _controller = null;
    }

    @Override
    protected String doInBackground(String... params) {
        for (int i = 0; i < 2; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        if(_controller != null) _controller.onPostExecute();
        if(_controller2 != null) _controller2.onPostExecute();
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}