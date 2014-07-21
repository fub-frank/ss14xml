package ss14xmlproject.xquery.result;

public class GeodataResult implements Result {

	private String flickerId;
	private Double longitude;
	private Double latitude;
	private Double radius;

	@Override
	public void initialize(String data) {
		String[] parts = data.split(";");
		this.flickerId = parts[0];
		this.longitude = Double.valueOf(parts[1]);
		this.latitude = Double.valueOf(parts[2]);
		this.radius = Double.valueOf(parts[3]);
	}

	public String getFlickerId() {
		return flickerId;
	}

	@Override
	public String toString() {
		return "GeodataResult [flickerId=" + flickerId + ", longitude="
				+ longitude + ", latitude=" + latitude + ", radius=" + radius
				+ "]";
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getRadius() {
		return radius;
	}

}
