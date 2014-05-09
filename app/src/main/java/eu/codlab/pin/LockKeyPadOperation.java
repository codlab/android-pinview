package eu.codlab.pin;

import android.os.AsyncTask;

/**
 * Created by kevinleperf on 09/05/2014.
 */
class LockKeyPadOperation extends AsyncTask<String, Void, String> {

    private final PinController _controller;

    LockKeyPadOperation(PinController controller){
        _controller = controller;
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
        _controller.onPostExecute();
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}