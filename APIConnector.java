/**
 * Created by dawilco on 10/7/17.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.*;
import org.apache.http.protocol.HTTP;

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
    /**
     * JSONObject sends a post request to the sent endpoint. It requires a JSON formatted payload request
     * @param urlString the url endpoint
     * @return JsonObject returned
     * @requires [valid url endpoint & valid payload for endpoint]
     * @ensures [payload is sent to endpoint and response returned at a JsonObject]
     */
    public static JsonObject postJSON(String urlString, JsonObject payload) {

        JsonObject jObject = null;

        String url = urlString;

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("Content-Type", "text/json");
        if(accessCode != null) post.setHeader("Authorization", accessCode);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

        StringEntity entity = new StringEntity(payload.toString(), HTTP.UTF_8);

        post.setEntity(entity);
        try {
            HttpResponse response = client.execute(post);
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + post.getEntity());
            System.out.println("Response Code : " +
                    response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            System.out.println(result.toString());
            jObject = new JsonParser().parse(response.toString()).getAsJsonObject();
        } catch (Exception e) {
            System.err.println(e);
        }

        return jObject;
    }

}
