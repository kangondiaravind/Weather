package weather.kangondiaravind.com.weather.data;

import org.json.JSONObject;

/**
 * Created by Aravindraj on 01-10-2017.
 */

public class Channel implements JsonPopulator {

    private Units units;
    private Item item;

    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public void populator(JSONObject data) {

        units = new Units();
        units.populator(data.optJSONObject("units"));

        item = new Item();
        item.populator(data.optJSONObject("item"));
    }
}
