package weather.kangondiaravind.com.weather.data;

import org.json.JSONObject;

/**
 * Created by Aravindraj on 01-10-2017.
 */

public class Units implements JsonPopulator {

    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populator(JSONObject data) {
        temperature=data.optString("temperature");
    }
}
