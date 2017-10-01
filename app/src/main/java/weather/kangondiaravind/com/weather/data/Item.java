package weather.kangondiaravind.com.weather.data;

import org.json.JSONObject;

/**
 * Created by Aravindraj on 01-10-2017.
 */

public class Item implements JsonPopulator {

    private String title;
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populator(JSONObject data) {

        condition = new Condition();
        condition.populator(data.optJSONObject("condition"));
        title=data.optString("title");
    }
}
