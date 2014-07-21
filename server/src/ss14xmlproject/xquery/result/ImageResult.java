package ss14xmlproject.xquery.result;

public class ImageResult implements Result {

	private int x;
	private int y;
	private int w;
	private int h;
	private String flickerid;

	@Override
	public void initialize(String data) {
		String[] parts = data.split(";", 5);
		this.flickerid = parts[0];
		this.x = Integer.parseInt(parts[1]);
		this.y = Integer.parseInt(parts[2]);
		this.w = Integer.parseInt(parts[3]);
		this.h = Integer.parseInt(parts[4]);
	}

	public String getFlickerid() {
		return flickerid;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
}
