package weather.kangondiaravind.com.weather.services;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import weather.kangondiaravind.com.weather.data.Channel;

/**
 * Created by Aravindraj on 01-10-2017.
 */

public class YahooWeatherService {

    ServiceResponse serviceResponse;
    private String location ;
    private Exception error;

    public YahooWeatherService(ServiceResponse serviceResponse) {
        this.serviceResponse = serviceResponse;
    }

    public String getLocation() {
        return location;
    }

    public void refreshWeather(String l)
    {
        this.location = l;
        new AsyncTask<String, Void, String>(){

            @Override
            protected String doInBackground(String... strings) {
                String yql = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", strings[0]);
                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(yql));

                try {
                    URL url = new URL(endpoint);
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream =urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line= bufferedReader.readLine())!=null)
                    {
                        result.append(line);
                    }
                    return result.toString();
                } catch (MalformedURLException e) {
                    error = e;
                    return null;
                } catch (IOException e) {
                    error = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if(s==null&& error!=null){
                    serviceResponse.serviceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryresult = data.optJSONObject("query");
                    int count =queryresult.optInt("count");
                    if(count==0){
                        serviceResponse.serviceFailure(new LocationWeatherException("No Weather Information is Found for " +location));
                        return;
                    }

                    Channel channel = new Channel();
                    channel.populator(queryresult.optJSONObject("results").optJSONObject("channel"));
                    serviceResponse.serviceSuccess(channel);
                } catch (JSONException e) {
                    serviceResponse.serviceFailure(e);
                }

            }

        }.execute(location);
    }

    public class LocationWeatherException extends Exception{
        public LocationWeatherException(String message) {
            super(message);
        }
    }
}
