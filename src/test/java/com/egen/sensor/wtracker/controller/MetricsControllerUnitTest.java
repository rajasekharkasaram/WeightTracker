package com.egen.sensor.wtracker.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.egen.sensor.wtracker.beans.Metrics;
import com.egen.sensor.wtracker.service.IMetricsService;

@RunWith(MockitoJUnitRunner.class)
public class MetricsControllerUnitTest {

	@InjectMocks
	private MetricsController  metricsController;
	@Mock
	private IMetricsService metricsService;
	
	@Test
	public void create() {
		Metrics metrics = new Metrics(System.currentTimeMillis(),159);
		
		doAnswer(new Answer<Void>() {
		    public Void answer(InvocationOnMock invocation) {
		      Object[] args = invocation.getArguments();
		      System.out.println("called with arguments: " + Arrays.toString(args));
		      return null;
		    }
		}).when(metricsService).create(metrics);
		
		metricsController.create(metrics);
		
		ArgumentCaptor<Metrics> argumentCaptor = ArgumentCaptor.forClass(Metrics.class);
        verify(metricsService, times(1)).create(argumentCaptor.capture());
        assertEquals(metrics.getValue(), argumentCaptor.getValue().getValue());
        assertEquals(metrics.getTimeStamp(), argumentCaptor.getValue().getTimeStamp());
		
		
	}

}
