package com.egen.sensor.wtracker.beans;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity("metrics")
public class Metrics {

	@Id
	private ObjectId id;
	@JsonProperty("timeStamp")
	@NotNull
	private long timeStamp;
	@JsonProperty("value")
	@NotNull
	private int value;

	public ObjectId getId() {
		return id;
	}

	public Metrics() {
	}

	public Metrics(long timeStamp, int value) {
		super();
		this.timeStamp = timeStamp;
		this.value = value;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Metrics [id=" + id + ", timeStamp=" + timeStamp + ", value=" + value + "]";
	}

}
