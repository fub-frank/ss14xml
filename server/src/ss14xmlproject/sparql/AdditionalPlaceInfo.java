package ss14xmlproject.sparql;

public class AdditionalPlaceInfo {

	private String comment;
	private String thumbnail;
	private Integer wikiId;
	private Double latitude;
	private Double longitude;

	public AdditionalPlaceInfo(String _comment, String _thumbnail,
			Integer _wikiId, Double _latitude, Double _longitude) {
		this.comment = _comment;
		this.thumbnail = _thumbnail;
		this.wikiId = _wikiId;
		this.latitude = _latitude;
		this.longitude = _longitude;
	}

	public String getComment() {
		return comment;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public Integer getWikiId() {
		return wikiId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "AdditionalPlaceInfo [comment=" + comment + ", thumbnail="
				+ thumbnail + ", wikiId=" + wikiId + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

}
