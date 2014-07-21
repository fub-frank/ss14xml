package ss14xmlproject.sparql;

import java.util.Iterator;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class QueryForPerson {
	private AdditionalPersonInfo additionalPersonInfo;

	public QueryForPerson(String givenName, String surName) {
		String queryString = buildQueryString(givenName, surName);
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		try {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution result = results.next();
				String _name = result.get("name").asLiteral().getString();
				String _abstract = result.get("abstract").asLiteral().getString();
				String _birthDate = result.get("birthdate").asLiteral().getString();
				String _deathDate = result.get("deathdate").asLiteral().getString();
				String _thumbnail = result.get("thumbnail").asResource().getURI();
				String _description = result.get("description").asLiteral().getString();
				
				this.additionalPersonInfo = new AdditionalPersonInfo(_name, _abstract, _birthDate, _deathDate, _thumbnail, _description);
			}
		} finally {
			qexec.close();
		}
	}
	
	public AdditionalPersonInfo getAdditionalPersonInfo() {
		return this.additionalPersonInfo;
	}
	
	private String buildQueryString(String givenName, String surName) {
		StringBuilder query = new StringBuilder();
		query.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>     ").append("\n");
		query.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>                ").append("\n");
		query.append("PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>       ").append("\n");
		query.append("PREFIX dbprop: <http://dbpedia.org/property/>            ").append("\n");
		query.append("                                                         ").append("\n");
		query.append("SELECT * WHERE {                                         ").append("\n");
		query.append("    ?person a foaf:Person ;                              ").append("\n");
		query.append("            foaf:name ?name ;                            ").append("\n");
		query.append("            dbpedia-owl:abstract ?abstract ;             ").append("\n");
		query.append("            dbpedia-owl:birthDate ?birthdate ;           ").append("\n");
		query.append("            dbpedia-owl:thumbnail ?thumbnail ;           ").append("\n");
		query.append("            dbprop:shortDescription ?description .       ").append("\n");
		query.append("            OPTIONAL {                                   ").append("\n");
		query.append("                ?person dbpedia-owl:deathDate ?deathdate ").append("\n");
		query.append("            }                                            ").append("\n");
		query.append("    FILTER (?name = '"+givenName+" "+surName+"'@en ||    ").append("\n");
		query.append("            ?name = '"+surName+" "+givenName+"'@en) .    ").append("\n");
		query.append("    FILTER (LANGMATCHES(LANG(?abstract), 'de')) .        ").append("\n");
		query.append("    FILTER (LANGMATCHES(LANG(?description), 'en'))       ").append("\n");
		query.append("}                                                        ").append("\n");
		query.append("LIMIT 1                                                  ").append("\n");
		
		return query.toString();
	}
}
