package ss14xmlproject.services.metadata;

public class Place {

	private String streetpart;
	private String suburb;
	private String city;

	public Place(String city, String suburb, String streetpart) {
		this.city = city;
		this.suburb = suburb;
		this.streetpart = streetpart;
	}

	public String getStreetpart() {
		return streetpart;
	}

	public String getSuburb() {
		return suburb;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return "Place [streetpart=" + streetpart + ", suburb=" + suburb
				+ ", city=" + city + "]";
	}

}
