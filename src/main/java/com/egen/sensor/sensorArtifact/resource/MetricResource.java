/**
 * 
 */
package com.egen.sensor.sensorArtifact.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.egen.sensor.sensorArtifact.model.Metric;
import com.egen.sensor.sensorArtifact.service.DataService;

/**
 * @author Rajeshkumar
 *
 */
@Path("/metrics")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MetricResource {
	DataService dServ = new DataService();
	/**
	 * @author Rajeshkumar
	 * Description: GET functionality to fetch list of metrics
	 * @param 
	 * @return List<Metric>
	 */
	@GET
	public List<Metric> getData(@QueryParam("start") long timestamp1,
							@QueryParam("end") long timestamp2){
		//Check if both the queried over time
		if(timestamp1>0 && timestamp2>0){
			return dServ.getDataBetweenTime(timestamp1,timestamp2);
		}
		//If not queried over time
		return dServ.getAllData();
	}
	/**
	 * @author Rajeshkumar
	 * Description: Post functionality to add Metric Data
	 * @param data
	 * @return data
	 */
	@POST
	public Metric addData( Metric data){
		Metric d = dServ.addData(data);
//		System.out.println(d.getId()+" "+d.getTimeStamp()+" "+d.getValue());
		return d;
	}
	
//	@GET
//	@Path("/{metricId}")
//	public Metric getDataForID(@PathParam("metricId") long id){
//		return dServ.getData(id);
//	}
}
