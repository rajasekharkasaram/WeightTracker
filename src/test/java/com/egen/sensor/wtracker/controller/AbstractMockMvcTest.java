package com.egen.sensor.wtracker.controller;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public abstract class AbstractMockMvcTest {

    protected final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();

    protected HttpServletRequest mockRequest;

    protected MockMvc client;

    protected abstract Object getController();

    @Before
    public void setupClient() {
        this.mockRequest = mock(HttpServletRequest.class);
        this.client = standaloneSetup(getController()).build();
    }


    protected RequestBuilder requestBuilder() {
        return new RequestBuilder().mockRequest(mockRequest);
    }

    protected <T> String serialize(T toSerialize) {
        if (toSerialize == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(toSerialize);
        } catch (JsonProcessingException e) {
            fail("Failed to serialize object in test");
            return null;
        }
    }

    public class RequestBuilder {
        private final Map<String, String[]> queryParams = new HashMap<>();
        private String path;
        private HttpServletRequest mockRequest;

        private RequestBuilder() {
        }

        public RequestBuilder path(String path) {
            this.path = path;
            return this;
        }

        public RequestBuilder queryParam(String name, Object... values) {
            if (name == null || values == null) {
                throw new IllegalArgumentException("Cannot add invalid queryParam. name=" + name + ", values=" + values);
            }
            String[] stringValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    stringValues[i] = values[i].toString();
                }
            }
            queryParams.put(name, stringValues);
            return this;
        }

        /**
         * Pass a mock of a HttpServletRequest which you want to return the correct query params
         */
        public RequestBuilder mockRequest(HttpServletRequest mockRequest) {
            this.mockRequest = mockRequest;
            return this;
        }

        public String build() {
            if (mockRequest != null) {
                when(mockRequest.getParameterMap()).thenReturn(queryParams);
            }
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance().path(this.path);
            queryParams.entrySet().forEach(entry -> Arrays.stream(entry.getValue()).forEach(value -> uriComponentsBuilder.queryParam(entry.getKey(), value)));
            return uriComponentsBuilder.toUriString();
        }
    }

}
