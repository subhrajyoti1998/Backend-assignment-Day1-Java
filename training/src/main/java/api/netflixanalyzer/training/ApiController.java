package api.netflixanalyzer.training;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	public List<NetflixShowDetails> data = NetflixDataLoader.loadDataFromCSV("C:/Users/Subhro/Desktop/netflix_data.csv");
	
	@GetMapping(value = "tvshows", params = "count")
	public List<NetflixShowDetails> getCount(@RequestParam(value = "count",
															required = false,
															defaultValue = "0") Long count) {
		return data.stream()
				.filter(r -> r != null && r.getShowType() != null
									   && r.getShowType().equalsIgnoreCase("TV Show"))
				.limit(count.intValue())
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = "tvshows", params = "country")
	public List<NetflixShowDetails> getHorrorTVShows(@RequestParam(value = "country",
															required = false,
															defaultValue = "") String country) {
		return data.stream()
				.filter(r -> r != null && r.getShowType() != null && r.getCountry() != null
									   && r.getShowType().equalsIgnoreCase("TV Show")
									   && r.getCountry().toLowerCase().contains(country.toLowerCase()))
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = "tvshows", params = {"startDate", "endDate"})
	public List<NetflixShowDetails> getTVShowsWithinADateRange(@RequestParam Map<String, String> requests) {
		
		try {
			Date lowerDate = new SimpleDateFormat("dd-mm-yyyy").parse(requests.get("startDate"));
			Date higherDate = new SimpleDateFormat("dd-mm-yyyy").parse(requests.get("endDate"));
			
			return data.stream()
					.filter(r -> r != null && r.getShowType() != null && r.getCountry() != null
										   && r.getShowType().equalsIgnoreCase("TV Show")
										   && (r.getDateAdded().equals(lowerDate) || r.getDateAdded().after(lowerDate))
										   && (r.getDateAdded().equals(higherDate) || r.getDateAdded().before(higherDate)))
					.collect(Collectors.toList());
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	@GetMapping("movies")
	public List<NetflixShowDetails> getHorrorMovies(@RequestParam(value = "movieType",
															required = false,
															defaultValue = "") String movieType) {
		return data.stream()
				.filter(r -> r != null && r.getShowType() != null && r.getListedIn() != null
									   && r.getShowType().equalsIgnoreCase("Movie")
									   && r.getListedIn().toLowerCase().contains(movieType.toLowerCase()))
				.collect(Collectors.toList());
	}
}