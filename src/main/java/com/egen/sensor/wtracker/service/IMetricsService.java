package com.egen.sensor.wtracker.service;

import java.util.List;

import com.egen.sensor.wtracker.beans.Metrics;

public interface IMetricsService {
	public void create(Metrics metrics);

	public List<Metrics> read();

	public List<Metrics> readByTimeRange(long fromTimeStamp, long toTimeStamp);

}
