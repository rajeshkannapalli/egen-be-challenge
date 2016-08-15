/**
 * 
 */
package com.egen.sensor.sensorArtifact.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * @author Rajeshkumar
 *
 */
@XmlRootElement
@Entity("alerts")
public class Alert {
	@Id
	private long id;
	
	private long metricId;
	private String alertMessage;
	
	public Alert(){
		
	}
	public Alert( long metricId, String alertMessage){
		this.metricId = metricId;
		this.alertMessage = alertMessage;
	}
	public Alert(long id, long metricId, String alertMessage){
		this.id = id;
		this.metricId = metricId;
		this.alertMessage = alertMessage;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMetricId() {
		return metricId;
	}
	public void setMetricId(long metricId) {
		this.metricId = metricId;
	}
	public String getAlertMessage() {
		return alertMessage;
	}
	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	@Override
    public String toString() {
        return "Alert{" +
                "id='" + id + '\'' +
                "metricId='" + metricId + '\'' +
                ", alertMessage=" + alertMessage +
                '}';
    }
}
