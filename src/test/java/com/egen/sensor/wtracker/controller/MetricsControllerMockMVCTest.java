package com.egen.sensor.wtracker.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;

import com.egen.sensor.wtracker.beans.Metrics;
import com.egen.sensor.wtracker.service.IMetricsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class MetricsControllerMockMVCTest extends AbstractMockMvcTest {
	
	@InjectMocks
	private MetricsController metricsController ;
	
	@Mock
	private IMetricsService metricsService;
	
	private static final String END_POINT_PATH ="/metrics/";
	
	
	@Override
	protected Object getController() {
		return metricsController;
	}
	
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
		
		RequestBuilder requestBuilder = requestBuilder().path(END_POINT_PATH);
	
		client.perform(post(requestBuilder.build()).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(json)).andExpect(status().isCreated());
		
		ArgumentCaptor<Metrics> argumentCaptor = ArgumentCaptor.forClass(Metrics.class);
        verify(metricsService, times(1)).create(argumentCaptor.capture());
        assertEquals(metrics.getValue(), argumentCaptor.getValue().getValue());
        assertEquals(metrics.getTimeStamp(), argumentCaptor.getValue().getTimeStamp());
		
	}
	


}
