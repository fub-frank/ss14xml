package ss14xmlproject.sparql;

public class AdditionalPersonInfo {

	private String _name;
	private String _abstract;
	private String _birthDate;
	private String _deathDate;
	private String _thumbnail;
	private String _description;

	public AdditionalPersonInfo(String _name, String _abstract,
			String _birthDate, String _deathDate, String _thumbnail,
			String _description) {
		this._name = _name;
		this._abstract = _abstract;
		this._birthDate = _birthDate;
		this._deathDate = _deathDate;
		this._thumbnail = _thumbnail;
		this._description = _description;
	}

	public String get_name() {
		return _name;
	}

	public String get_abstract() {
		return _abstract;
	}

	public String get_birthDate() {
		return _birthDate;
	}

	public String get_deathDate() {
		return _deathDate;
	}

	public String get_thumbnail() {
		return _thumbnail;
	}

	public String get_description() {
		return _description;
	}

	@Override
	public String toString() {
		return "AdditionalPersonInfo [_name=" + _name + ", _abstract="
				+ _abstract + ", _birthDate=" + _birthDate + ", _deathDate="
				+ _deathDate + ", _thumbnail=" + _thumbnail + ", _description="
				+ _description + "]";
	}

}
