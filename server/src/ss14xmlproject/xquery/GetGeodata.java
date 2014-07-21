package ss14xmlproject.xquery;

public class GetGeodata extends XQuery {
	
	public GetGeodata() {
		super(
				"for $foto in collection('ss14xml/geodata')/Foto " +
				"	let $longitude := $foto/längengrad/text()" +
				"	let $latitude := $foto/breitengrad/text()" +
				"	let $radius := $foto/Radius/text()" +
				"	let $flickerid := $foto/flickerid/text()" +
				"	return $flickerid||';'||$longitude||';'||$latitude||';'||$radius"
		);
	}
}
