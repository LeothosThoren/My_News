package com.leothosthoren.mynews.model.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sofiane M. alias Leothos Thoren on 12/02/2018
 */
public class HttpUrlConnectivity {

    public static String startHttpRequest(String UrlString) {
        StringBuilder sStringBuilder = new StringBuilder();

        try {

            URL url = new URL(UrlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sStringBuilder.append(line);
            }

        } catch (MalformedURLException exception) {

        } catch (IOException exception) {

        } catch (Exception e) {

        }

        return sStringBuilder.toString();
    }


}
