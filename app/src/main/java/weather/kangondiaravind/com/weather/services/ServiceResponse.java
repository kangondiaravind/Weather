package weather.kangondiaravind.com.weather.services;

import weather.kangondiaravind.com.weather.data.Channel;

/**
 * Created by Aravindraj on 01-10-2017.
 */

public interface ServiceResponse {

    public void serviceSuccess(Channel channel);
    public void serviceFailure(Exception exception);
}
