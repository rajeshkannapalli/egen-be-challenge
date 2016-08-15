/**
 * 
 */
package com.egen.sensor.sensorArtifact.service;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.egen.sensor.sensorArtifact.model.Alert;
import com.egen.sensor.sensorArtifact.model.Metric;
import com.mongodb.MongoClient;

/**
 * @author Rajeshkumar
 *
 */
public class AlertService {
	private static final Morphia morphia = new Morphia();
	private static final Datastore datastore = morphia.createDatastore(new MongoClient(), "egenSensorDB");

	public AlertService(){
		morphia.mapPackage("com.egen.sensor.sensorArtifact.model");
	}
	
	/**
	 * @author Rajeshkumar
	 * Description: fetches alert data for given id
	 * @param id
	 * @return alert
	 */
	public Alert getAlert(long id){
		Query<Alert> query = datastore.createQuery(Alert.class).filter("id=", id);
		return query.get(); 
	}
	
	/**
	 * @author Rajeshkumar
	 * Description: Adds alert and stores it in MongoDB under collection alerts.
	 * @param alert
	 * @return alerts
	 */
	public Alert addAlert(Alert alert) {
		Query<Alert> query = datastore.createQuery(Alert.class);
		long size = query.countAll();
		alert.setId(size+1);
		datastore.save(alert);
		return alert;
	}

	/**
	 * @author Rajeshkumar
	 * Description: Provides list of Alert Data between provided timestamps. Swaps timestamps if unordered times.
	 * @param timestamp1
	 * @param timestamp2
	 * @return List<Alert>
	 */
	public List<Alert> getAlertsBetweenTime(long timestamp1, long timestamp2) {
		DataService dServ = new DataService();
		List<Metric> datas = dServ.getDataBetweenTime(timestamp1, timestamp2);
		List<Alert> alerts = new ArrayList<Alert>();
		//if data between timestamps is not empty
		if(!datas.isEmpty()){
			for(Metric data : datas){
				Query<Alert> query = datastore.createQuery(Alert.class).filter("metricId =",data.getId());
				Alert alert = query.get();
				if(null!= alert){
					alerts.add(alert);
				}	
			}
		}
		return alerts;
	}

	/**
	 * @author Rajeshkumar
	 * Description: fetches the entire alert data
	 * @param 
	 * @return List<Alert>
	 */
	public List<Alert> getAllAlerts() {
		Query<Alert> query = datastore.createQuery(Alert.class);
		List<Alert> alerts = query.asList();
		return alerts;
	}
}
