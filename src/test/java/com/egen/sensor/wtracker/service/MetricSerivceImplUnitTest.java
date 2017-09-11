package com.egen.sensor.wtracker.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.easyrules.api.RulesEngine;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.egen.sensor.wtracker.beans.Metrics;
import com.egen.sensor.wtracker.repository.IMetricsRepository;
import com.egen.sensor.wtracker.rules.WeightRule;
import com.egen.sensor.wtracker.service.impl.MetricServiceImpl;
import com.egen.sensor.wtracker.utility.MetricsUtility;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MetricsUtility.class })
@Ignore
public class MetricSerivceImplUnitTest {

	@InjectMocks
	private MetricServiceImpl metricServiceImpl;
	@Mock
	private IMetricsRepository metricsRepository;
	@Mock
	private RulesEngine rulesEngine;
	@Mock
	private WeightRule weightRule;

	@Test

	public void create() throws Exception {
		Metrics metrics = new Metrics(System.currentTimeMillis(), 159);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				System.out.println("called with arguments: " + Arrays.toString(args));
				return null;
			}
		}).when(metricsRepository).create(metrics);

		PowerMockito.mockStatic(MetricsUtility.class);

		PowerMockito.doNothing().when(MetricsUtility.class, "setMetrics", metrics);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				System.out.println("called with arguments: " + Arrays.toString(args));
				return null;
			}
		}).when(weightRule).validateWeight();
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				System.out.println("called with arguments: " + Arrays.toString(args));
				return null;
			}
		}).when(rulesEngine).fireRules();
		PowerMockito.doNothing().when(MetricsUtility.class, "removeMetrics");

		metricServiceImpl.create(metrics);
		ArgumentCaptor<Metrics> argumentCaptor = ArgumentCaptor.forClass(Metrics.class);
		verify(metricServiceImpl, times(1)).create(argumentCaptor.capture());
		assertEquals(metrics.getValue(), argumentCaptor.getValue().getValue());
		assertEquals(metrics.getTimeStamp(), argumentCaptor.getValue().getTimeStamp());

	}

}
