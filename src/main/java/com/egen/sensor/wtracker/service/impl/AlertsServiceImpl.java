package com.egen.sensor.wtracker.service.impl;


import java.util.List;

import org.easyrules.api.RulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egen.sensor.wtracker.beans.Alerts;
import com.egen.sensor.wtracker.repository.IAlertsRepository;
import com.egen.sensor.wtracker.service.IAlertsService;
import com.egen.sensor.wtracker.service.exceptions.NoContentException;

@Service
public class AlertsServiceImpl implements IAlertsService{
	
	@Autowired
	RulesEngine rulesEngine ;
	
	@Autowired
	private IAlertsRepository alertssRepository;
	

	@Override
	public void create(Alerts alert) {
		alertssRepository.create(alert);
	}

	@Override
	public List<Alerts> read() {	
		List<Alerts> alertsList = alertssRepository.read();
		if(alertsList.isEmpty()){
			throw new NoContentException("No Alerts Data Found in the Database");
		}
		return alertsList;
	}

	@Override
	public List<Alerts> readByTimeRange(long fromTimeStamp, long toTimeStamp) {		
		List<Alerts> alertsList = alertssRepository.readByTimeRange(fromTimeStamp, toTimeStamp);
		if(alertsList.isEmpty()){
			throw new NoContentException("No Alerts Data Found in the Database");
		}
		return alertsList;
	}
	
}
