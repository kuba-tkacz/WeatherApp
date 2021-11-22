package pl.discovery.client2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final RestTemplate microserviceClient;
    private final RestTemplate httpClient;

    public WeatherService(@Qualifier("microservice") RestTemplate microserviceClient,
                          @Qualifier("http") RestTemplate httpClient) {
        this.microserviceClient = microserviceClient;
        this.httpClient = httpClient;
    }

    public List<WeatherEntity> getWeather(String name){
        Config[] config = microserviceClient.getForObject("http://config-service/config", Config[].class);
        return Arrays.stream(config)
                .map(c -> {
                    WeatherEntity weatherEntity = new WeatherEntity();
                    switch (c.getServiceName()) {
                        case "openWeatherApi":
                            OpenWeather weather = httpClient.getForObject(String.format(c.getUrl(), name), OpenWeather.class);
                            weatherEntity.setServiceName(c.getServiceName());
                            weatherEntity.setTemperature(weather.getMain().getTemp());
                            weatherEntity.setPressure(weather.getMain().getPressure());
                            weatherEntity.setHumidity(weather.getMain().getHumidity());
                            break;
                        case "weatherStack":
                            WeatherStack weatherStack = httpClient.getForObject(String.format(c.getUrl(), name), WeatherStack.class);
                            weatherEntity.setServiceName(c.getServiceName());
                            weatherEntity.setTemperature(weatherStack.getCurrent().getTemperature());
                            weatherEntity.setPressure(weatherStack.getCurrent().getPressure());
                            weatherEntity.setHumidity(weatherStack.getCurrent().getHumidity());
                            break;
                    }
                    return weatherEntity;
                }).collect(Collectors.toList());
    }
}
