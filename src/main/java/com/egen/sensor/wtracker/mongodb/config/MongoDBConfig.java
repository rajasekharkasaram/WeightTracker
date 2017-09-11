package com.egen.sensor.wtracker.mongodb.config;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.egen.sensor.wtracker.beans.Metrics;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

@Configuration
public class MongoDBConfig {
	@Value("#{environment[\'mongodb.host\'] ?: \'127.0.0.1\'}")
	private String DB_HOST;
	@Value("#{environment[\'mongodb.port\'] ?: 27017}")
	private int DB_PORT;
	@Value("#{environment[\'mongodb.database\'] ?: \'weighttracker\'}")
	private String DB_NAME;

	@Value("#{environment[\'mongodb.socketTimeout\'] ?: 60000}")
	private int socketTimeout;
	@Value("#{environment[\'mongodb.connectTimeout\'] ?: 15000}")
	private int connectTimeout;
	@Value("#{environment[\'mongodb.maxConnectionIdleTime\'] ?: 600000}")
	private int maxConnectionIdleTime;

	public MongoDBConfig() {
	}

	@Bean
	public Datastore dataStore() {
		MongoClientOptions mongoOptions = MongoClientOptions.builder().socketTimeout(socketTimeout)
				.connectTimeout(connectTimeout).maxConnectionIdleTime(maxConnectionIdleTime)
				.readPreference(ReadPreference.primaryPreferred()).build();

		MongoClient mongoClient = new MongoClient(new ServerAddress(DB_HOST, DB_PORT), mongoOptions);

		Datastore datastore = new Morphia().mapPackage(Metrics.class.getPackage().getName())
				.createDatastore(mongoClient, DB_NAME);
		datastore.ensureIndexes();
		datastore.ensureCaps();
		return datastore;
	}

}