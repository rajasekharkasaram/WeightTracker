package com.egen.sensor.wtracker.utility;

import org.springframework.stereotype.Component;

import com.egen.sensor.wtracker.beans.Metrics;

@Component
public class MetricsUtility{
	private static final ThreadLocal<Metrics> metricsContext = new ThreadLocal<>();
	
	public static Metrics getMetrics() {
		return metricsContext.get();
	}
	public static void setMetrics(Metrics metrics) {
		metricsContext.set(metrics);
	}
	public static void removeMetrics() {
		metricsContext.remove();
	}

}