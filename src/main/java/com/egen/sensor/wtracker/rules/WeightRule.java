package com.egen.sensor.wtracker.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.egen.sensor.wtracker.beans.Alerts;
import com.egen.sensor.wtracker.beans.Metrics;
import com.egen.sensor.wtracker.service.IAlertsService;
import com.egen.sensor.wtracker.utility.MetricsUtility;

@Component
@Rule(name = "Detects Under or Over weight ", description = "if the weight of the person shoots 10% over his base weight")
public class WeightRule {

	/**
	 * The user input which represents the data that the rule will operate on.
	 */
	@Value("#{environment[\'weights.overweightpercentage\'] ?: 10}")
	private int overweightpercentage;
	@Value("#{environment[\'weights.underweightpercentage\'] ?: 10}")
	private int underweightpercentage;
	@Value("#{environment[\'weights.baseWeight\'] ?: 150}")
	private int baseWeight;
	@Autowired
	IAlertsService alertsService;
	public WeightRule() {
	}

	@Condition
	public boolean validateWeight() {
		Metrics metrics = MetricsUtility.getMetrics();
		return (Float.valueOf(metrics.getValue()) > (baseWeight + (baseWeight * (overweightpercentage / 100f)))) || (Float.valueOf(metrics.getValue()) <= (baseWeight - (baseWeight * (underweightpercentage / 100f))));
	}

	@Action
	public void logToDB() throws Exception {
		Metrics metrics = MetricsUtility.getMetrics();		
		String status = Float.valueOf(metrics.getValue()) <= (baseWeight - (baseWeight * (underweightpercentage / 100f)))?"UnderWeight":"OverWeight";
		Alerts alert = new Alerts(metrics.getValue(), metrics.getTimeStamp(), status);
		alertsService.create(alert);
	}

}
