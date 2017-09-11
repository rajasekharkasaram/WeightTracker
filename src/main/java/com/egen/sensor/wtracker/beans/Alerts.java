package com.egen.sensor.wtracker.beans;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("alerts")
public class Alerts {
	@Id
	private ObjectId id;
	private int weight;
	private long timeStamp;
	private String status;

	public Alerts() {
	}

	public Alerts(int weight, long timeStamp, String status) {
		super();
		this.weight = weight;
		this.timeStamp = timeStamp;
		this.status = status;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Alerts [id=" + id + ", weight=" + weight + ", timeStamp=" + timeStamp + "]";
	}

}
