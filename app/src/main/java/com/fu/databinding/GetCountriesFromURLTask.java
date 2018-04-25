package com.fu.databinding;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetCountriesFromURLTask extends AsyncTask<Void, Void, String> {

    private MainActivity activity;
    private String url;
    private ProgressDialog dialog;

    public GetCountriesFromURLTask(MainActivity activity, String url) {
        super();
        this.activity = activity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create a progress dialog

        dialog = new ProgressDialog(activity);
        dialog.setTitle("Loading");
        dialog.setMessage("Loading data from URL...");
        dialog.setIndeterminate(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        // call load JSON from url method
        return loadJSON(this.url);
    }

    @Override
    protected void onPostExecute(String result) {
        activity.parseJsonResponse(result);
        dialog.dismiss();
    }

    public String loadJSON(String url) {
        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();

        // getting JSON string from URL
        return jParser.getJSON(url, 20000);
    }

    private class JSONParser {
        public String getJSON(String url, int timeout) {
            HttpURLConnection urlConnection = null;
            try {
                URL u = new URL(url);
                urlConnection = (HttpURLConnection) u.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-length", "0");
                urlConnection.setUseCaches(false);
                urlConnection.setAllowUserInteraction(false);
                urlConnection.setConnectTimeout(timeout);
                urlConnection.setReadTimeout(timeout);
                urlConnection.connect();
                int status = urlConnection.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        return sb.toString();
                }

            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (urlConnection != null) {
                    try {
                        urlConnection.disconnect();
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return null;
        }
    }
}
