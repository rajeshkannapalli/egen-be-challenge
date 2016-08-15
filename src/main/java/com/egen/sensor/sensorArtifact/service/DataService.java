/**
 * 
 */
package com.egen.sensor.sensorArtifact.service;

import java.util.List;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.egen.sensor.sensorArtifact.model.Metric;
import com.egen.sensor.sensorArtifact.rules.AlertOverWeightRule;
import com.egen.sensor.sensorArtifact.rules.AlertUnderWeightRule;
import com.mongodb.MongoClient;

/**
 * @author Rajeshkumar
 *
 */
public class DataService {
	private static final Morphia morphia = new Morphia();
	private static final Datastore datastore = morphia.createDatastore(new MongoClient(), "egenSensorDB");
	private static int baseValue;
	private static final AlertOverWeightRule ruleOW = new AlertOverWeightRule();
	private static final AlertUnderWeightRule ruleUW = new AlertUnderWeightRule();
	private static  RulesEngine rulesEngine = null;
	
	
	
	
	public DataService(){
		morphia.mapPackage("com.egen.sensor.sensorArtifact.model");
		rulesEngine = RulesEngineBuilder.aNewRulesEngine().build();
	}
	/**
	 * @author Rajeshkumar
	 * Description: fetches the entire metric data
	 * @param 
	 * @return List<Metric>
	 */
	public List<Metric> getAllData(){
		Query<Metric> query = datastore.createQuery(Metric.class);
		List<Metric> datas = query.asList();
		return datas;
	}
	
	/**
	 * @author Rajeshkumar
	 * Description: fetches metric data for given id
	 * @param id
	 * @return metric
	 */
	public Metric getData(long id){
		Query<Metric> query = datastore.createQuery(Metric.class).filter("id", id);
		return query.get(); 
	}
	/**
	 * @author Rajeshkumar
	 * Description: Adds Metric Data and stores it in MongoDB under collection metrics. Evaluates rules for alerts and stores alerts in collection alerts.
	 * @param data
	 * @return data
	 */
	public Metric addData(Metric data) {
		//Fetch data from DB to get and add 
		Query<Metric> query = datastore.createQuery(Metric.class);
		long size = query.countAll();
		data.setId(size+1);
		datastore.save(data);
		//Assumption: If data size is non zero basevalue already stored in class
		if(size != 0){
			//Check Rule for alert if less -> underweight check else overweight check
			if(data.getValue()>baseValue){
				rulesEngine.registerRule(ruleOW);
				ruleOW.setInput(data.getValue(), data.getId());
			}else{
				rulesEngine.registerRule(ruleUW);
				ruleUW.setInput(data.getValue(), data.getId());
			}
			rulesEngine.fireRules();
		}else{
			baseValue = data.getValue();
			ruleUW.setBaseValue(baseValue);
			ruleOW.setBaseValue(baseValue);
		}
		return data;
	}
	/**
	 * @author Rajeshkumar
	 * Description: Provides list of Metric Data between provided timestamps. Swaps timestamps if unordered times.
	 * @param timestamp1
	 * @param timestamp2
	 * @return List<Metric>
	 */
	public List<Metric> getDataBetweenTime(long timestamp1, long timestamp2) {
		if(timestamp1>timestamp2){
			long temp = timestamp1;
			timestamp1 = timestamp2;
			timestamp2 = temp;
		}
		Query<Metric> query = datastore.createQuery(Metric.class).filter("timeStamp >=", timestamp1).filter("timeStamp <=", timestamp2);
		List<Metric> datas = query.asList();
		return datas;
	}
}
