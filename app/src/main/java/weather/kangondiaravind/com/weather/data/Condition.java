package weather.kangondiaravind.com.weather.data;

import org.json.JSONObject;

/**
 * Created by Aravindraj on 01-10-2017.
 */

public class Condition implements JsonPopulator {

    private int code;
    private int temperature;
    private String describtion;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescribtion() {
        return describtion;
    }

    @Override
    public void populator(JSONObject data) {
        code=data.optInt("code");
        temperature=data.optInt("temp");
        describtion=data.optString("text");
    }
}
