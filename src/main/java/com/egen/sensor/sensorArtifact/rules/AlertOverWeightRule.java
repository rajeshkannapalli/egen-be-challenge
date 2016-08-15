/**
 * 
 */
package com.egen.sensor.sensorArtifact.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.egen.sensor.sensorArtifact.model.Alert;
import com.egen.sensor.sensorArtifact.service.AlertService;

/**
 * @author Rajeshkumar
 *
 */
@Rule(name = "AlertRule1", description = "Alert OverWeight")
public class AlertOverWeightRule {

	/**
	 * The user input which represents the data that the rule will operate on.
	 */
	private int input;
	private long metricId;
	private static int baseValue;
	private static float maxValue;
	private AlertService alertServ = new AlertService();

	/**
	 * @author Rajeshkumar
	 * Description: Checks if input condition is true for given rule
	 * @return boolean
	 */
	@Condition
	public boolean checkInput() {
		// The rule should be applied only if true
		return input>=maxValue;
	}
	
	/**
	 * @author Rajeshkumar
	 * Description: Action to be performed if condition criteria meets
	 */
	@Action
	public void addOverWeightAlert() throws Exception {
		// When rule conditions are satisfied,
		// add alert as overweight
		Alert alert = new Alert(metricId,"OverWeight");
		alertServ.addAlert(alert);
		System.out.println("Hello, the sensed weight is OverWeight w.r.t baseValue -> "+baseValue);
	}


	/**
	 * @author Rajeshkumar
	 * Description: Sets the baseValue 
	 * @param: baseValue
	 */
	public void setBaseValue(int baseValue) {
		this.baseValue = baseValue;
		maxValue = (float) (baseValue + 0.1 * baseValue);
	}
	
	/**
	 * @author Rajeshkumar
	 * Description: Sets the inputs value and id of metric data for join  
	 * @param: value
	 * @param: id
	 */
	public void setInput(int value, long id){
		input = value;
		metricId = id;
	}

}
