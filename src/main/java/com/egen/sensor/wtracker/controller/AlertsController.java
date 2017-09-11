package com.egen.sensor.wtracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egen.sensor.wtracker.beans.Alerts;
import com.egen.sensor.wtracker.beans.Metrics;
import com.egen.sensor.wtracker.service.IAlertsService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Api(
        basePath = "/alerts", 
        value = "Alerts Read Operations", 
        description = "Read Operations on Alerts Data", 
        consumes = "application/json" ,
        produces = "application/json"        
        )
@RestController
@RequestMapping("/alerts")
public class AlertsController {

	@Autowired
	IAlertsService alertsService;
	
	@ApiOperation(nickname = "listAllAlerts", value = "List All Alerts", notes = "This Api retrieves all Alerts.", response = Metrics.class)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content Found", response = java.lang.String.class),
			@ApiResponse(code = 400, message = "Invalid request", response = com.egen.sensor.wtracker.service.exceptions.ErrorResponse.class),
			@ApiResponse(code = 500, message = "Error processing request", response = com.egen.sensor.wtracker.service.exceptions.ErrorResponse.class), })
	@RequestMapping(method=RequestMethod.GET)
	public List<Alerts> read() {
			return alertsService.read();
	}
	
	@ApiOperation(nickname = "listAllTimeRangeAlerts", value = "List All Alerts in the given timestamp range", notes = "This Api retrieves all Alerts for the given timestamp range.", response = Metrics.class)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content Found", response = java.lang.String.class),
			@ApiResponse(code = 400, message = "Invalid request", response = com.egen.sensor.wtracker.service.exceptions.ErrorResponse.class),
			@ApiResponse(code = 500, message = "Error processing request", response = com.egen.sensor.wtracker.service.exceptions.ErrorResponse.class), })
	@RequestMapping(value="/time", method = RequestMethod.GET)
	public List<Alerts> readByTimeRange(@RequestParam("fromTimeStamp") long fromTimeStamp,
			@RequestParam("toTimeStamp") long toTimeStamp) {
			return alertsService.readByTimeRange(fromTimeStamp, toTimeStamp);
	}

}
