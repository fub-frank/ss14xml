package ss14xmlproject.sparql;

public class AdditionalWorkInfo {

	private Integer _wikiId;
	private String _thumbnail;
	private String _name;

	public AdditionalWorkInfo(Integer _wikiId2,
			String _thumbnail, String _name) {
		this._wikiId = _wikiId2;
		this._thumbnail = _thumbnail;
		this._name = _name;
	}

	public int get_wikiId() {
		return _wikiId;
	}

	public String get_thumbnail() {
		return _thumbnail;
	}

	public String get_name() {
		return _name;
	}

	@Override
	public String toString() {
		return "AdditionalWorkInfo [_wikiId="
				+ _wikiId + ", _thumbnail=" + _thumbnail + ", _name=" + _name
				+ "]";
	}

}
