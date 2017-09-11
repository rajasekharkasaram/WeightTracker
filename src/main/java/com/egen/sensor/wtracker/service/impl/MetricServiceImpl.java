package com.egen.sensor.wtracker.service.impl;

import java.util.List;

import org.easyrules.api.RulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egen.sensor.wtracker.beans.Metrics;
import com.egen.sensor.wtracker.repository.IMetricsRepository;
import com.egen.sensor.wtracker.rules.WeightRule;
import com.egen.sensor.wtracker.service.IMetricsService;
import com.egen.sensor.wtracker.service.exceptions.NoContentException;
import com.egen.sensor.wtracker.utility.MetricsUtility;

@Service
public class MetricServiceImpl implements IMetricsService{
	@Autowired
	private IMetricsRepository metricsRepository;
	@Autowired
	private RulesEngine rulesEngine;
	@Autowired
	private WeightRule weightRule;
	
	@Override
	public void create(Metrics metrics) {
		metricsRepository.create(metrics);
		MetricsUtility.setMetrics(metrics);
			weightRule.validateWeight();
			rulesEngine.fireRules();
		MetricsUtility.removeMetrics();	
	}

	@Override
	public List<Metrics> read() {	
		List<Metrics> metricsList = metricsRepository.read();
		if(metricsList.isEmpty()){
			throw new NoContentException("No Metrics Data Found in the Database");
		}
		return metricsList;
	}

	@Override
	public List<Metrics> readByTimeRange(long fromTimeStamp, long toTimeStamp) {		
		List<Metrics> metricsList = metricsRepository.readByTimeRange(fromTimeStamp, toTimeStamp);
		if(metricsList.isEmpty()){
			throw new NoContentException("No Metrics Data Found in the Database");
		}
		return metricsList;
	}
	

}
