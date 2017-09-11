package com.egen.sensor.wtracker.repository.impl;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.egen.sensor.wtracker.beans.Metrics;
import com.egen.sensor.wtracker.repository.IMetricsRepository;

@Repository
public class MetricsRepositoryImpl implements IMetricsRepository {
	@Autowired
	Datastore datastore;

	@Override
	public void create(Metrics metrics) {
		datastore.save(metrics);
	}

	@Override
	public List<Metrics> read() {
		Query<Metrics> empQuery =  datastore.find(Metrics.class);
		return empQuery.asList();
	}

	@Override
	public List<Metrics> readByTimeRange(long fromTimeStamp, long toTimeStamp) {
		Query<Metrics> empQuery =  datastore.find(Metrics.class)
				 							.filter("timeStamp >=", fromTimeStamp)
											.filter("timeStamp <=", toTimeStamp);
		return empQuery.asList();
	}

}
