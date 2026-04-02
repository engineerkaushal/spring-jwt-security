package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.POJO.WeatherResponse;

public interface WeatherService {

    WeatherResponse getRecords(String city);
}
