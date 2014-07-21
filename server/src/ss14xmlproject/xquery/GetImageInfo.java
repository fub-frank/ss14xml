package ss14xmlproject.xquery;

public class GetImageInfo extends XQuery {

	public GetImageInfo(String flickerId) {
		super(
			"for $picture in collection('ss14xml/imagedata')/Picture " +
			"where $picture/FlickerID=" + flickerId + " " +
			"let $sections := count($picture/Section) " +
			"let $filename := $picture/Filename/text() " +
			"return $sections || ';' || $filename"
		);
	}

}
