package ss14xmlproject.xquery.result;

public class ImageInfoResult implements Result {

	private int sections;
	private String filename;

	@Override
	public void initialize(String data) {
		String[] parts = data.split(";", 2);
		this.sections = Integer.parseInt(parts[0]);
		this.filename = parts[1];
	}

	public int getSections() {
		return sections;
	}

	public String getFilename() {
		return filename;
	}

}
