package ss14xmlproject.xquery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.basex.examples.api.BaseXClient;
import org.basex.examples.api.BaseXClient.Query;

import ss14xmlproject.xquery.result.Result;

public class XQuery {

	private String query;
	
	public XQuery(String query) {
		this.query = query;
	}

	public String execute(BaseXClient session) throws IOException {
		return session.execute(this.query);
	}
	
	public <T extends Result> List<T> query(BaseXClient session, Class<T> clazz) throws IOException {
		try {
			List<T> results = new ArrayList<>(200);
			Query query = session.query(this.query);
			while(query.more()) {
				T instance = clazz.newInstance();
				instance.initialize(query.next());
				results.add(instance);
			}
			query.close();
			return results;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IOException(e);
		}
	}
}
