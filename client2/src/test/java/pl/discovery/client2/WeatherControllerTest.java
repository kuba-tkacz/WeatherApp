package pl.discovery.client2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WeatherService weatherService;

    @Test
    void when_send_request_to_get_weather_for_szczecin_then_one_weather_should_be_returned() throws Exception {
        //given
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setServiceName("openWeather");
        weatherEntity.setPressure(1022);
        weatherEntity.setHumidity(20);
        weatherEntity.setTemperature(11.6f);
        Mockito.when(weatherService.getWeather("Szczecin")).thenReturn(Arrays.asList(weatherEntity));

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/Szczecin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].serviceName", equalTo("openWeather")));
    }

    @Test
    void when_send_request_to_get_weather_for_szczecin_then_empty_list_should_be_returned() throws Exception {
        //given
        Mockito.when(weatherService.getWeather("Szczecin")).thenReturn(Arrays.asList());

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/weather/Szczecin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
