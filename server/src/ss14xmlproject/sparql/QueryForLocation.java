package ss14xmlproject.sparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import ss14xmlproject.services.metadata.Place;

public class QueryForLocation {
	private AdditionalPlaceInfo additionalPlaceInfo;

	public QueryForLocation(Place place) {
		String queryString = buildQuery(place);
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution result = results.next();
				String _comment = result.get("c").asLiteral().getString();
				String _thumbnail = result.get("t").asResource().getURI();
				Integer _wikiId = result.get("wikiId").asLiteral().getInt();
				Double _latitude = result.get("latitude").asLiteral().getDouble();
				Double _longitude = result.get("longitude").asLiteral().getDouble();
				
				this.additionalPlaceInfo = new AdditionalPlaceInfo(_comment, _thumbnail, _wikiId, _latitude, _longitude);
			}
		} finally {
			qexec.close();
		}
	}
	
	public AdditionalPlaceInfo getAdditionalPlaceInfo() {
		return this.additionalPlaceInfo;
	}
	
	private String buildQuery(Place place) {
		StringBuilder query = new StringBuilder();
		query.append("PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> ").append("\n");
		query.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>   ").append("\n");
		query.append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>     ").append("\n");
		query.append("                                                       ").append("\n");
		query.append("SELECT * WHERE {                                       ").append("\n");
		query.append("    ?p rdfs:label '"+place.getStreetpart()+"'@en .     ").append("\n");
		query.append("    ?p rdfs:comment ?c .                               ").append("\n");
		query.append("    FILTER(LANGMATCHES(LANG(?c), 'de')) .              ").append("\n");
		query.append("    ?p dbpedia-owl:thumbnail ?t .                      ").append("\n");
		query.append("    ?p dbpedia-owl:wikiPageID ?wikiId .                ").append("\n");
		query.append("    ?p geo:lat ?latitude .                             ").append("\n");
		query.append("    ?p geo:long ?longitude                             ").append("\n");
		query.append("}                                                      ").append("\n");
		return query.toString();
	}
}
