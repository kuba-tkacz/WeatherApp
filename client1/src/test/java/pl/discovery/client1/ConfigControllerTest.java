package pl.discovery.client1;

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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ConfigController.class)
class ConfigControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ConfigService configService;

    @Test
    void when_send_get_request_to_config_then_one_config_should_be_returned() throws Exception {
        //given
        List<Config> configs = Arrays.asList(new Config("c1", "c2"));
        Mockito.when(configService.getConfigList()).thenReturn(configs);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/config")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].serviceName", equalTo("c1")))
                .andExpect(jsonPath("$[0].url", equalTo("c2")));
    }

    @Test
    void when_send_get_request_to_config_then_empty_config_list_should_be_returned() throws Exception {
        //given
        List<Config> configs = Collections.emptyList();
        Mockito.when(configService.getConfigList()).thenReturn(configs);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/config").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
