package ss14xmlproject.xquery;

public class GetMetadata extends XQuery {

	public GetMetadata(String flickerId) {
		super(
			"for $item in doc('ss14xml/metadata')/data/item where $item/Flicker_Id/text() = '"+flickerId+"' return $item"
		);
	}

}
