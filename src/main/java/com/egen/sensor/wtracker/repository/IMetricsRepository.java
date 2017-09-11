package com.egen.sensor.wtracker.repository;

import java.util.List;

import com.egen.sensor.wtracker.beans.Metrics;

public interface IMetricsRepository {
	public void create(Metrics metrics);

	public List<Metrics> read();

	public List<Metrics> readByTimeRange(long fromTimeStamp, long toTimeStamp);

}
