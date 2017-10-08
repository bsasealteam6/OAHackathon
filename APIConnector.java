/**
 * Created by dawilco on 10/7/17.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONObject;
import com.google.gson.*;


public class APIConnector {

    private static String baseURL;
    private static String accessCode = null;

    /**
     * Create an unauthenticated endpoint
     * @param baseURL Base URL we will call our endpoints on
     */
    public APIConnector(String baseURL) {
        this.baseURL = baseURL;
    }

    /**
     * Create an authenticated endpoint
     * @param baseURL Base URL we will call our endpoints on
     * @param accessCode Access token for authenticated endpoint
     */
    public APIConnector(String baseURL, String accessCode) {
        this.baseURL= baseURL;
        this.accessCode = accessCode;
    }

    /**
     * JSONObject returns a json object from the requested api endpoint.
     * @param urlString the url endpoint
     * @return JsonObject returned
     * @requires [valid url endpoint]
     * @ensures [json from the endpoint requested is returned]
     */
    public static JsonObject getJSON(String urlString) {

        JSONObject json = null;
        JsonObject jObject = null;

        URL url;
        try {

            url = new URL(baseURL + urlString);
            System.out.println(url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set headers
            conn.setRequestMethod("GET");
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
            jObject = new JsonParser().parse(response.toString()).getAsJsonObject();
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }

        return jObject;

    }



}
