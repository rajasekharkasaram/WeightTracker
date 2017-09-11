package com.egen.sensor.wtracker.repository.impl;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.egen.sensor.wtracker.beans.Alerts;
import com.egen.sensor.wtracker.repository.IAlertsRepository;

@Repository
public class AlertsRepositoryImpl implements IAlertsRepository {
	@Autowired
	Datastore datastore;

	@Override
	public void create(Alerts alert) {
		datastore.save(alert);
	}

	@Override
	public List<Alerts> read() {
		Query<Alerts> empQuery =  datastore.find(Alerts.class);
		return empQuery.asList();
	}

	@Override
	public List<Alerts> readByTimeRange(long fromTimeStamp, long toTimeStamp) {
		Query<Alerts> empQuery =  datastore.find(Alerts.class)
				 							.filter("timeStamp >=", fromTimeStamp)
											.filter("timeStamp <=", toTimeStamp);
		return empQuery.asList();
	}

}
