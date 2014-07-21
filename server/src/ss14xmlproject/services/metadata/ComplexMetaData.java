package ss14xmlproject.services.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ss14xmlproject.services.metadata.response.AdditionalArchitectInfo;
import ss14xmlproject.services.metadata.response.AdditionalArchitectWorkInfo;
import ss14xmlproject.services.metadata.response.AdditionalLocationInfo;
import ss14xmlproject.services.metadata.response.AdditionalPhotographerInfo;
import ss14xmlproject.services.metadata.response.Architect;
import ss14xmlproject.services.metadata.response.ContentContainer;
import ss14xmlproject.services.metadata.response.Location;
import ss14xmlproject.services.metadata.response.MetadataResponse;
import ss14xmlproject.services.metadata.response.Photographer;
import ss14xmlproject.services.metadata.response.Work;
import ss14xmlproject.sparql.AdditionalPersonInfo;
import ss14xmlproject.sparql.AdditionalPlaceInfo;
import ss14xmlproject.sparql.AdditionalWorkInfo;
import ss14xmlproject.sparql.QueryForArchitectWork;
import ss14xmlproject.xquery.result.MetadataResult;

public class ComplexMetaData {

	private List<String> content;
	private List<Name> architectNames;
	private String credit;
	private String date;
	private String ownership;
	private List<Name> photographerNames;
	private List<Place> places;
	private String systematics;
	private String title;
	private String year;
	private HashMap<Name, AdditionalPersonInfo> additionalPersonInfo;
	private HashMap<Name, List<AdditionalWorkInfo>> additionalWorkInfo;
	private HashMap<Place, AdditionalPlaceInfo> additionalPlaceInfo;
	
	public ComplexMetaData() {
		additionalPersonInfo = new HashMap<Name, AdditionalPersonInfo>();
		additionalWorkInfo = new HashMap<Name, List<AdditionalWorkInfo>>();
		additionalPlaceInfo = new HashMap<Place, AdditionalPlaceInfo>();
	}

	public void insertMetadata(MetadataResult result) {
		content = parseContent(result.getContent());
		architectNames = parseName(result.getCreator());
		credit = result.getCredit();
		date = result.getDate();
		ownership = result.getOwnership();
		photographerNames = parseName(result.getPhotographer());
		places = parsePlace(result.getPlace());
		systematics = result.getSystematics();
		title = result.getTitle();
		year = result.getYear();
	}

	private List<Place> parsePlace(String place) {
		List <Place> result = new LinkedList<>();
		String[] placeParts = place.split(",");
		String city = placeParts[0];
		String suburb = placeParts[1];
		String street = placeParts[2];
		String[] streetparts = street.split("/");
		for (String streetpart : streetparts) {
			result.add(new Place(city.trim(), suburb.trim(), streetpart.trim()));
		}
		return result;
	}

	private List<Name> parseName(String nametext) {
		String[] names = nametext.split("\n");
		List<Name> result = new ArrayList<Name>(names.length);
		for (String name : names) {
			int bracketOpen = name.indexOf('(');
			if (bracketOpen > -1) {
				name = name.substring(0, bracketOpen);
			}
			int comma = name.indexOf(',');
			if (comma > -1) {
				name = name.substring(0, comma);
			}
			String[] firstNameLastName = name.split(" ", 2);
			if (firstNameLastName.length >= 2) {
				result.add(new Name(firstNameLastName[0].trim(), firstNameLastName[1].trim()));
			}
			else {
				result.add(new Name(firstNameLastName[0].trim(), "Unbekannt"));
			}
		}
		return result;
	}

	private List<String> parseContent(String content) {
		String[] keywords = content.split("\n");
		List<String> result = new ArrayList<String>(keywords.length);
		for (String keyword : keywords) {
			result.add(keyword.trim());
		}
		return result;
	}

	public List<Name> getArchitectNames() {
		return architectNames;
	}

	public String getCredit() {
		return credit;
	}

	public String getDate() {
		return date;
	}

	public String getOwnership() {
		return ownership;
	}

	public List<Name> getPhotographerNames() {
		return photographerNames;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public String getSystematics() {
		return systematics;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public void addAdditionalPersonInfo(Name photographer,	AdditionalPersonInfo additionalPersonInfo) {
		if (additionalPersonInfo != null) {
			this.additionalPersonInfo.put(photographer, additionalPersonInfo);
		}
	}

	public void addAdditionalArchitectWork(Name architect,	List<AdditionalWorkInfo> additionalWorkInfo) {
		if(!this.additionalWorkInfo.containsKey(architect)) {
			this.additionalWorkInfo.put(architect, new LinkedList<AdditionalWorkInfo>());
		}
		this.additionalWorkInfo.get(architect).addAll(additionalWorkInfo);
	}

	public List<String> getContent() {
		return content;
	}

	public void addAdditionalPlaceInfo(Place place,	AdditionalPlaceInfo additionalPlaceInfo) {
		if (additionalPlaceInfo != null) {
			this.additionalPlaceInfo.put(place, additionalPlaceInfo);
		}
		
	}

	public MetadataResponse convertToResponse() {
		MetadataResponse response = new MetadataResponse();
		response.setCredit(credit);
		response.setDate(date);
		response.setOwnership(ownership.replaceAll("\n", ". "));
		response.setSystematics(systematics);
		response.setTitle(title);
		response.setYear(year);
		
		for (String contentKeyword : this.content) {
			response.getContents().getContent().add(contentKeyword);
		}
		
		for (Name architectName : this.architectNames) {
			List<Architect> list = response.getArchitects().getArchitect();
			Architect architect = new Architect();
			list.add(architect);

			architect.setLastName(architectName.getLastName());
			architect.setFirstName(architectName.getFirstName());
			
			if (this.additionalPersonInfo.containsKey(architectName)) {
				AdditionalArchitectInfo additionalInfo = new AdditionalArchitectInfo();
				architect.setAdditionalInfo(additionalInfo);

				additionalInfo.setShortDescription(this.additionalPersonInfo.get(architectName).get_abstract());
				additionalInfo.setBirthdate(this.additionalPersonInfo.get(architectName).get_birthDate());
				additionalInfo.setDeathdate(this.additionalPersonInfo.get(architectName).get_deathDate());
				additionalInfo.setDescription(this.additionalPersonInfo.get(architectName).get_description());
				additionalInfo.setThumbnail(this.additionalPersonInfo.get(architectName).get_thumbnail());
			}
			if (this.additionalWorkInfo.containsKey(architectName)) {
				AdditionalArchitectWorkInfo additionalInfo = new AdditionalArchitectWorkInfo();
				architect.setAdditionalWork(additionalInfo);
				for (AdditionalWorkInfo workInfo : this.additionalWorkInfo.get(architectName)) {
					String name = workInfo.get_name();
					String thumbnail = workInfo.get_thumbnail();
					int wikiId = workInfo.get_wikiId();
					if (additionalInfo.getWork() == null) {
						additionalInfo.setWork(new LinkedList<Work>());
					}
					additionalInfo.getWork().add(new Work(name, thumbnail, wikiId));
				}
			}
		}
		
		for (Name photographerName : this.photographerNames) {
			List<Photographer> list = response.getPhotographers().getPhotographer();
			Photographer photographer = new Photographer();
			list.add(photographer);

			photographer.setLastName(photographerName.getLastName());
			photographer.setFirstName(photographerName.getFirstName());
			
			if (this.additionalPersonInfo.containsKey(photographerName)) {
				AdditionalPhotographerInfo additionalInfo = new AdditionalPhotographerInfo();
				photographer.setAdditionalInfo(additionalInfo);

				additionalInfo.setShortDescription(this.additionalPersonInfo.get(photographerName).get_abstract());
				additionalInfo.setBirthdate(this.additionalPersonInfo.get(photographerName).get_birthDate());
				additionalInfo.setDeathdate(this.additionalPersonInfo.get(photographerName).get_deathDate());
				additionalInfo.setDescription(this.additionalPersonInfo.get(photographerName).get_description());
				additionalInfo.setThumbnail(this.additionalPersonInfo.get(photographerName).get_thumbnail());
			}
			
		}
		
		for(Place place : places) {
			List<Location> list = response.getLocations().getLocation();
			String city = place.getCity();
			String street = place.getStreetpart();
			String suburb = place.getSuburb();
			Location location = new Location(city, street, suburb);
			list.add(location);
			
			if (this.additionalPlaceInfo.containsKey(place)) {
				AdditionalLocationInfo additionalInfo = new AdditionalLocationInfo();
				location.setLocationInfo(additionalInfo);

				additionalInfo.setComment(this.additionalPlaceInfo.get(place).getComment());
				additionalInfo.setLatitude(this.additionalPlaceInfo.get(place).getLatitude());
				additionalInfo.setLongitude(this.additionalPlaceInfo.get(place).getLongitude());
				additionalInfo.setThumbnail(this.additionalPlaceInfo.get(place).getThumbnail());
				additionalInfo.setWikiId(this.additionalPlaceInfo.get(place).getWikiId());
			}
		}
		
		return response;
	}

}
