package weather.kangondiaravind.com.weather;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import weather.kangondiaravind.com.weather.data.Channel;
import weather.kangondiaravind.com.weather.data.Item;
import weather.kangondiaravind.com.weather.services.ServiceResponse;
import weather.kangondiaravind.com.weather.services.YahooWeatherService;

public class MainActivity extends AppCompatActivity implements ServiceResponse {

    ImageView weatherIcon;
    TextView temperature;
    TextView condition;
    TextView location;

    ProgressDialog dialog;
    YahooWeatherService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherIcon = (ImageView)findViewById(R.id.WeatherIconimg);
        temperature = (TextView)findViewById(R.id.temperatureTV);
        condition=(TextView)findViewById(R.id.conditionTV);
        location=(TextView)findViewById(R.id.locationTV);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...!");
        dialog.show();

        service = new YahooWeatherService(this);
        service.refreshWeather("Bangalore, India");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();
        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_"+ item.getCondition().getCode(), null ,getPackageName());
        @SuppressWarnings("deprecation") Drawable weatherDrawableIcon = getResources().getDrawable(resourceId);
        weatherIcon.setImageDrawable(weatherDrawableIcon);
        location.setText(service.getLocation());
        temperature.setText(item.getCondition().getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        condition.setText(item.getCondition().getDescribtion());
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
