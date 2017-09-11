package com.egen.sensor.wtracker.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.egen.sensor.wtracker.beans.Metrics;
import com.egen.sensor.wtracker.service.IMetricsService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Api(
        basePath = "/metrics", 
        value = "Metrics Create Read Operations", 
        description = "Create and Read Operations on Metrics Data", 
        consumes = "application/json" ,
        produces = "application/json"        
        )
@RestController
@RequestMapping("/metrics")
public class MetricsController {
	@Autowired
	IMetricsService metricService;
	
	@ApiOperation(nickname = "createMetric", value = "creates metric", notes = "This Api retrieves creates metric info.", response = Metrics.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Metrics created"),
			@ApiResponse(code = 400, message = "Invalid request", response = com.egen.sensor.wtracker.service.exceptions.ErrorResponse.class),
			@ApiResponse(code = 500, message = "Error processing request", response = com.egen.sensor.wtracker.service.exceptions.ErrorResponse.class), })
  
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody Metrics metrics) {
		metricService.create(metrics);
	}
	
	@ApiOperation(nickname = "listAllMetrics", value = "List All Metrics", notes = "This Api retrieves all Metrics.", response = Metrics.class)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content Found", response = java.lang.String.class),
			@ApiResponse(code = 400, message = "Invalid request", response = com.egen.sensor.wtracker.service.exceptions.ErrorResponse.class),
			@ApiResponse(code = 500, message = "Error processing request", response = com.egen.sensor.wtracker.service.exceptions.ErrorResponse.class), })
 
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Metrics>> read() {
		System.out.println("metric:read");
		return new ResponseEntity<List<Metrics>>(metricService.read(),HttpStatus.OK);
	}

	@ApiOperation(nickname = "listAllTimeRangeMetrics", value = "List All Metrics Based on given timestamp range", notes = "This Api retrieves all Metrics in the given timestamp range.", response = Metrics.class)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content Found", response = java.lang.String.class),
			@ApiResponse(code = 400, message = "Invalid request", response = com.egen.sensor.wtracker.service.exceptions.ErrorResponse.class),
			@ApiResponse(code = 500, message = "Error processing request", response = com.egen.sensor.wtracker.service.exceptions.ErrorResponse.class), })
 
	@RequestMapping(value="/time", method = RequestMethod.GET)
	public List<Metrics> readByTimeRange(@RequestParam("fromTimeStamp") long fromTimeStamp,
			@RequestParam("toTimeStamp") long toTimeStamp) {
		System.out.println("metric:readByTimeRange");
		return metricService.readByTimeRange(fromTimeStamp, toTimeStamp);
	}

}
