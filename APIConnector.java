/**
 * Created by dawilco on 10/7/17.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class APIConnector {
    private static String baseURL;
    private static String accessCode = null;

    public APIConnector(String baseURL) {
        this.baseURL = baseURL;
    }

    public APIConnector(String baseURL, String accessCode) {
        this.baseURL= baseURL;
        this.accessCode = accessCode;
    }

    public static JSONObject getJSON(String urlString) {
        JSONObject json = null;

        URL url;
        try {
            url = new URL(baseURL + urlString);
            System.out.println(url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            // set headers
            conn.setRequestProperty("Content-Type", "text/json");
            if(accessCode != null) conn.setRequestProperty("Authorization", accessCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                String temp = inputLine.replace("[", "").replace("]","");
                response.append(temp);
            }
            in.close();

            System.out.println(response.toString());

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        return json;

    }



}
