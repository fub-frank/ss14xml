package ss14xmlproject.services.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.basex.examples.api.BaseXClient;

import ss14xmlproject.services.exceptions.NoSuchFlickerIdException;
import ss14xmlproject.services.exceptions.XMLDatabaseException;
import ss14xmlproject.services.metadata.response.MetadataResponse;
import ss14xmlproject.sparql.QueryForArchitectWork;
import ss14xmlproject.sparql.QueryForLocation;
import ss14xmlproject.sparql.QueryForPerson;
import ss14xmlproject.xquery.GetMetadata;
import ss14xmlproject.xquery.result.MetadataResult;

@WebService(name = "MetadataService", serviceName = "MetadataService", portName = "MetadataServicePort", targetNamespace = "http://schema.df-root.com/metadata")
public class MetadataService {

	private BaseXClient basex;

	public MetadataService() {
	}

	@WebMethod(exclude = true)
	public void setBaseXSession(BaseXClient session) {
		this.basex = session;
	}
	
	@WebMethod
	public @WebResult(name="Metadata") MetadataResponse getMetadata(@WebParam(name = "flickerId") String flickerId) throws XMLDatabaseException, NoSuchFlickerIdException {
		
		try {
			ComplexMetaData complexMetaData = new ComplexMetaData();
			
			List<MetadataResult> results = new GetMetadata(flickerId).query(this.basex, MetadataResult.class);
			if (results.size() == 0) {
				throw new NoSuchFlickerIdException();
			}
			
			complexMetaData.insertMetadata(results.get(0));
			
			// Get additional data on the photographers
			
			for(Name photographer : complexMetaData.getPhotographerNames()) {
				QueryForPerson personInfo = new QueryForPerson(photographer.getFirstName(), photographer.getLastName());
				complexMetaData.addAdditionalPersonInfo(photographer, personInfo.getAdditionalPersonInfo());
			}
			
			// Get additional data on the architects
			for(Name architect : complexMetaData.getArchitectNames()) {
				QueryForPerson personInfo = new QueryForPerson(architect.getFirstName(), architect.getLastName());
				complexMetaData.addAdditionalPersonInfo(architect, personInfo.getAdditionalPersonInfo());

				QueryForArchitectWork architectWork = new QueryForArchitectWork(architect.getFirstName(), architect.getLastName());
				complexMetaData.addAdditionalArchitectWork(architect, architectWork.getAdditionalWork());
			}
			
			// Get additional data on the location
			List<Place> places = complexMetaData.getPlaces();
			for(Place place : places) {
				QueryForLocation locationInfo = new QueryForLocation(place);
				complexMetaData.addAdditionalPlaceInfo(place, locationInfo.getAdditionalPlaceInfo());
			}
			
			return complexMetaData.convertToResponse();
			
		} catch (IOException e) {
			throw new XMLDatabaseException(e);
		}
	}
}
