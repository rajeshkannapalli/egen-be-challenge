/**
 * 
 */
package com.egen.sensor.sensorArtifact.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.egen.sensor.sensorArtifact.model.Alert;
import com.egen.sensor.sensorArtifact.service.AlertService;

/**
 * @author Rajeshkumar
 *
 */
@Path("/alerts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlertResource {
	AlertService aServ = new AlertService();
	/**
	 * @author Rajeshkumar
	 * Description: GET functionality to fetch list of alerts
	 * @param 
	 * @return List<Alert>
	 */
	@GET
	public List<Alert> getAlerts(@QueryParam("start") long timestamp1,
							@QueryParam("end") long timestamp2){
		//Check if both the queried over time
		if(timestamp1>0 && timestamp2>0){
			return aServ.getAlertsBetweenTime(timestamp1,timestamp2);
		}
		//If not queried over time 
		return aServ.getAllAlerts();
	}
}

