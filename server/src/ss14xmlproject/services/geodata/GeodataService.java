package ss14xmlproject.services.geodata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.basex.examples.api.BaseXClient;

import ss14xmlproject.services.exceptions.XMLDatabaseException;
import ss14xmlproject.xquery.GetGeodata;
import ss14xmlproject.xquery.XQuery;
import ss14xmlproject.xquery.result.GeodataResult;

@WebService(name = "GeodataService", serviceName = "GeodataService", portName = "GeodataServicePort", targetNamespace = "http://schema.df-root.com/geodata")
public class GeodataService {
	private BaseXClient basex;

	public GeodataService() {

	}

	@WebMethod(exclude = true)
	public void setBaseXSession(BaseXClient session) {
		this.basex = session;
	}

	@WebMethod
	public @WebResult(name = "Geofence")
	List<Geofence> getGeoFences() throws XMLDatabaseException {
		List<Geofence> fences = new ArrayList<Geofence>(200);

		try {
			XQuery geodataQuery = new GetGeodata();
			for (GeodataResult result : geodataQuery.query(this.basex,
					GeodataResult.class)) {
				fences.add(new Geofence(result.getFlickerId(), result
						.getLongitude(), result.getLatitude(), result
						.getRadius()));
			}
			return fences;
		} catch (IOException e) {
			throw new XMLDatabaseException(e);
		}
	}
}
