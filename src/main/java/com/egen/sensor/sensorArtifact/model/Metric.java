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
@Entity("metrics")

public class Metric {
	@Id
	private long id;
	private long timeStamp;
	private int value;
	public Metric(){
		
	}
	public Metric( long timeStamp, int value){
		System.out.println("TimeStamp colleced is in data2"+timeStamp);
		this.timeStamp  = timeStamp;
		this.value = value;		
	}
	public Metric(long id, long timeStamp, int value){
		System.out.println("TimeStamp colleced is in data3"+timeStamp);
		this.timeStamp  = timeStamp;
		this.value = value;
		this.id = id;
		
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = Long.valueOf(timeStamp);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
    public String toString() {
        return "Data{" +
                "timestamp='" + timeStamp + '\'' +
                ", value=" + value +
                '}';
    }
}
