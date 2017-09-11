package com.egen.sensor.wtracker;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.egen.sensor.wtracker.rules.WeightRule;

@Component
@Configuration
public class RulesEngineConfig {

	@Bean
	@Autowired
	public RulesEngine rulesEngine(WeightRule weightRule) {
		RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine().build();

		// register the rules
		rulesEngine.registerRule(weightRule);

		return rulesEngine;
	}
}
