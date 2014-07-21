package ss14xmlproject.sparql;

import java.util.LinkedList;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class QueryForArchitectWork {

	private List<AdditionalWorkInfo> work;


	public QueryForArchitectWork(String firstName, String lastName) {
		work = new LinkedList<AdditionalWorkInfo>();
		String queryString = buildQueryString(firstName + " " + lastName);
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution result = results.next();
				Integer _wikiId = result.get("wikiId").asLiteral().getInt();
				String _thumbnail = result.get("thumbnail").asResource().getURI();
				String _name = result.get("name").asLiteral().getString();
				this.work.add(new AdditionalWorkInfo(_wikiId, _thumbnail, _name));
			}
		} finally {
			qexec.close();
		}
	}
	
	public List<AdditionalWorkInfo> getAdditionalWork() {
		return this.work;
	}
	
	
	private String buildQueryString(String architect) {
		StringBuilder query = new StringBuilder();
		query.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>     ").append("\n");
		query.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>                ").append("\n");
		query.append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>       ").append("\n");
		query.append("PREFIX dbprop: <http://dbpedia.org/property/>            ").append("\n");
		query.append("                                                         ").append("\n");
		query.append(" SELECT DISTINCT * WHERE {                               ").append("\n");
		query.append("    ?person a foaf:Person .                              ").append("\n");
		query.append("    ?person foaf:name '"+architect+"'@en .               ").append("\n");
		query.append("    ?r dbpedia-owl:architect ?person .                   ").append("\n");
		query.append("    ?r dbpedia-owl:wikiPageID ?wikiId .                  ").append("\n");
		query.append("    ?r dbpedia-owl:thumbnail ?thumbnail .                ").append("\n");
		query.append("    ?r dbprop:name ?name .                               ").append("\n");
		query.append("}                                                        ").append("\n");
		return query.toString();
	}
}
