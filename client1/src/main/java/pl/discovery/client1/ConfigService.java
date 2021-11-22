package pl.discovery.client1;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ConfigService {

    public List<Config> getConfigList() {
        Config openWeatherApi = new Config(
                "openWeatherApi",
                "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=baa6ece140be0985d8bf8766fa381d1d&units=metric"
        );
        Config weatherStackApi = new Config(
                "weatherStack",
                "http://api.weatherstack.com/current?access_key=17ff09ba764b15dc96f5f8436421b30f&query=%s"
        );
        return Arrays.asList(openWeatherApi, weatherStackApi);
    }
}
