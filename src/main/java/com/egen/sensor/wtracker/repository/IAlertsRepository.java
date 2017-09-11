package com.egen.sensor.wtracker.repository;

import java.util.List;

import com.egen.sensor.wtracker.beans.Alerts;

public interface IAlertsRepository {
	
	public void create(Alerts alert);
	
	public List<Alerts> read();

	public List<Alerts> readByTimeRange(long fromTimeStamp, long toTimeStamp);
}
