package com.curtin.padraig.rasppiremote;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestPostUtil extends AsyncTask<String, String, Boolean> {
        @Override
        public Boolean doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            try {
                URL urlObject = new URL(params[0]);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                urlConnection.setRequestMethod("POST");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                convertStreamToString(in);
                urlConnection.disconnect();
            } catch(MalformedURLException malformed) {
                System.out.print("ERROR malformed url.");
                malformed.printStackTrace();
            } catch(IOException io) {
                System.out.print("ERROR IOException.");
                io.printStackTrace();
            } finally {
                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }
            }
            return true;
        }

        static String convertStreamToString(java.io.InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
}
