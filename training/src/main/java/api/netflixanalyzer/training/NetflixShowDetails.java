package api.netflixanalyzer.training;
import java.util.Date;

public class NetflixShowDetails {
	private String showId;
	private String showType;
	private String title;
	private String director;
	private String cast;
	private String country;
	private Date dateAdded;
	private String release_year;
	private String rating;
	private String duration;
	private String listedIn;
	private String description;
	
	public NetflixShowDetails(String showId, String showType, String title, String director, String cast,
			String country, Date dateAdded, String release_year, String rating, String duration, String listedIn,
			String description) {
		this.showId = showId;
		this.showType = showType;
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.country = country;
		this.dateAdded = dateAdded;
		this.release_year = release_year;
		this.rating = rating;
		this.duration = duration;
		this.listedIn = listedIn;
		this.description = description;
	}
	
	public String getShowType() {
		return showType;
	}
	
	public String getCountry() {
		return country;
	}
	
	public Date getDateAdded() {
		return dateAdded;
	}
	
	public String getListedIn() {
		return listedIn;
	}
	
	@Override
	public String toString() {
		return "showId=" + showId + ", showType=" + showType + ", title=" + title + ", director="
				+ director + ", cast=" + cast + ", country=" + country + ", dateAdded=" + dateAdded + ", release_year="
				+ release_year + ", rating=" + rating + ", duration=" + duration + ", listedIn=" + listedIn
				+ ", description=" + description;
	}
}