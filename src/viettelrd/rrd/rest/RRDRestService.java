package viettelrd.rrd.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import viettelrd.rrd.services.RRDService;
import javax.ws.rs.core.MediaType;

@Path("/viettel")
@Produces("application/json;charset=UTF-8")
public class RRDRestService {
	// Rest service for getting the list of datasources
	@GET
	@Path("/rrd/ds")
	public Response getDataSources() throws IOException {
		JSONArray json = null;
		RRDService rrdService = new RRDService();
		String xmlPath = System.getProperty("catalina.base") + "/webapps/ROOT/resources/rrd.xml";
		rrdService.setRrdFile("xml:/" + xmlPath);
		try {
			List<String> arrs = rrdService.getDataSources();			
			if(arrs.size() > 0) {
		      json = new JSONArray(); 
		      for (String string : arrs) {
		    	  json.put(string);
			  }
		      return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}
	
	// Rest service for create graph image for a datasource
	@GET
	@Path("/rrd/graph/{datasource}")
	public Response loadGraph(@PathParam("datasource") String datasource) {
      RRDService rrdService = new RRDService();
      String xmlPath = System.getProperty("catalina.base") + "/webapps/ROOT/resources/rrd.xml";
      rrdService.setRrdFile("xml:/" + xmlPath);
      JSONObject respone = null;
      try {
	      String imgPath = rrdService.loadGraph(datasource);
	      if(imgPath != null && imgPath.length() > 0) {
	        respone = new JSONObject();
	    	respone.put("graph", imgPath);
	        return Response.ok(respone.toString(), MediaType.APPLICATION_JSON).build();
	      }
      } catch(IOException e) {
    	e.printStackTrace();
      } catch (JSONException e) {
			e.printStackTrace();
	  }
      return Response.ok().build();
	}
}
