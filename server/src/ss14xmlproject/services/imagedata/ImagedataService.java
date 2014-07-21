package ss14xmlproject.services.imagedata;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.basex.examples.api.BaseXClient;

import ss14xmlproject.SoapService;
import ss14xmlproject.services.exceptions.AmbiguousFlickerIdException;
import ss14xmlproject.services.exceptions.NoSuchFlickerIdException;
import ss14xmlproject.services.exceptions.NoSuchImageException;
import ss14xmlproject.services.exceptions.XMLDatabaseException;
import ss14xmlproject.xquery.GetImage;
import ss14xmlproject.xquery.GetImageInfo;
import ss14xmlproject.xquery.result.ImageInfoResult;
import ss14xmlproject.xquery.result.ImageResult;

@WebService(name = "ImagedataService", serviceName = "ImagedataService", portName = "ImagedataServicePort", targetNamespace = "http://schema.df-root.com/imagedata")
public class ImagedataService {

	private BaseXClient basex;

	public ImagedataService() {
	}

	@WebMethod(exclude = true)
	public void setBaseXSession(BaseXClient session) {
		this.basex = session;
	}
	
	@WebMethod
	public @WebResult(name="ImageInfo") ImageInfo getImageInfo(@WebParam(name = "flickerId") String flickerId) throws NoSuchFlickerIdException, AmbiguousFlickerIdException, IOException {
		GetImageInfo imageInfo = new GetImageInfo(flickerId);
		List<ImageInfoResult> info = imageInfo.query(basex, ImageInfoResult.class);
		if (info.size() == 0) {
			throw new NoSuchFlickerIdException();
		}
		if (info.size() > 1) {
			throw new AmbiguousFlickerIdException();
		}
		
		return new ImageInfo(info.get(0).getFilename(), info.get(0).getSections());
	}
	
	@WebMethod
	public @WebResult(name="Image") ImageWrapper getImage(@WebParam(name = "filename") String filename, @WebParam(name = "number") int number) throws NoSuchImageException, XMLDatabaseException {
		List<ImageResult> imageresult;
		try {
			imageresult = new GetImage(filename, number).query(basex, ImageResult.class);
		}
		catch (IOException e) {
			throw new XMLDatabaseException(e);
		}
		if (imageresult.size() == 0) {
			throw new NoSuchImageException();
		}
		ImageResult result = imageresult.get(0);
		try {
			URL url = new URL(SoapService.IMAGE_URL + result.getFlickerid() + ".jpg");
			BufferedImage sourceImage = ImageIO.read(url.openConnection().getInputStream());
			BufferedImage destinationImage = sourceImage.getSubimage(result.getX(), result.getY(), result.getW(), result.getH());
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(destinationImage, "jpg", baos);
			byte[] image = baos.toByteArray();
			baos.close();
			
			return new ImageWrapper(image);
		}
		catch(IOException e) {
			throw new NoSuchImageException(e);
		}
	}
}
