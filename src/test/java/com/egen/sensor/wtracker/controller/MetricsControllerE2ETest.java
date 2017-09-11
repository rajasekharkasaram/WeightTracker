package com.egen.sensor.wtracker.controller;

import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.egen.sensor.wtracker.WeightTrackerApplication;
import com.egen.sensor.wtracker.beans.Metrics;
import com.egen.sensor.wtracker.service.IMetricsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(SpringRunner.class)
@SpringBootTest(
  classes = WeightTrackerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application.properties")
public class MetricsControllerE2ETest {
	
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private IMetricsService metricsService;
	
	private long fromTimeStamp = System.currentTimeMillis(); 

	@Test
	public void testCreateMetrics() throws Exception {
		System.out.println("testCreateMetrics.......");
		Metrics metrics = new Metrics(System.currentTimeMillis(),159);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(metrics);
		
		doAnswer(new Answer<Void>() {
		    public Void answer(InvocationOnMock invocation) {
		      Object[] args = invocation.getArguments();
		      System.out.println("called with arguments: " + Arrays.toString(args));
		      return null;
		    }
		}).when(metricsService).create(metrics);
		
		
		this.mockMvc.perform(post("/metrics/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
				.andExpect(status().isCreated());
	}

	@Test
	public void testGetAllMetrics() throws Exception {
		System.out.println("testGetAllMetrics.......");
		this.mockMvc.perform(get("/metrics/")
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk());
	}
	
	@Test
	public void testGetAllMetricsWithinTimeRange() throws Exception {
		System.out.println("testGetAllMetricsWithinTimeRange.......");
		this.mockMvc.perform(get("/metrics/")
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.accept(MediaType.APPLICATION_JSON_VALUE)
								.param("fromTimeStamp", String.valueOf(fromTimeStamp))
								.param("toTimeStamp", String.valueOf(System.currentTimeMillis()))
								)
					.andExpect(status().isOk());
	}


	
}
