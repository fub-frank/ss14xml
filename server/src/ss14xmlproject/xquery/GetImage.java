package ss14xmlproject.xquery;

public class GetImage extends XQuery {

	public GetImage(String filename, int section) {
		super(
			"for $picture in collection('ss14xml/imagedata')/Picture " +
			"where $picture/Filename='" + filename + "'" +
			"let $flickerid := $picture/FlickerID/text()" +
			"for $section in $picture/Section " +
			"where $section/@id=" + section + " " +
			"let $x := $section/xValue/text()" +
			"let $y := $section/yValue/text()" +
			"let $w := $section/Width/text()" +
			"let $h := $section/Height/text()" +
			"return $flickerid || ';' || $x || ';' || $y || ';' || $w || ';' || $h"
		);
	}

}
