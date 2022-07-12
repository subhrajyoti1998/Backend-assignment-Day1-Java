package api.netflixanalyzer.training;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	public List<NetflixShowDetails> data = NetflixDataLoader.loadDataFromCSV("C:/Users/Subhro/Desktop/netflix_data.csv");
	
	@GetMapping(value = "tvshows", params = "count")
	public ResponseEntity<List<NetflixShowDetails>> getCount(@RequestParam(value = "count",
																			required = false,
																			defaultValue = "0") Long count,
															@RequestHeader(name = "X-Auth-Token",
																			required = true) String token) {
		if (token != null) {
			List<NetflixShowDetails> output = data.stream()
												.filter(r -> r != null && r.getShowType() != null
												   && r.getShowType().equalsIgnoreCase("TV Show"))
												.limit(count.intValue())
												.collect(Collectors.toList());
			return new ResponseEntity<>(output, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ArrayList<NetflixShowDetails>(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping(value = "tvshows", params = "country")
	public ResponseEntity<List<NetflixShowDetails>> getHorrorTVShows(@RequestParam(value = "country",
																					required = false,
																					defaultValue = "") String country,
																		@RequestHeader(name = "X-Auth-Token",
																						required = true) String token) {
		if (token != null) {
			List<NetflixShowDetails> output = data.stream()
												.filter(r -> r != null && r.getShowType() != null && r.getCountry() != null
																	   && r.getShowType().equalsIgnoreCase("TV Show")
																	   && r.getCountry().toLowerCase().contains(country.toLowerCase()))
												.collect(Collectors.toList());
			return new ResponseEntity<>(output, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ArrayList<NetflixShowDetails>(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping(value = "tvshows", params = {"startDate", "endDate"})
	public ResponseEntity<List<NetflixShowDetails>> getTVShowsWithinADateRange(@RequestHeader(name = "X-Auth-Token",
																								required = true) String token,
																@RequestParam Map<String, String> requests) {
		if (token != null) {
			List<NetflixShowDetails> output = new LinkedList<NetflixShowDetails>();
			try {
				Date lowerDate = new SimpleDateFormat("dd-mm-yyyy").parse(requests.get("startDate"));
				Date higherDate = new SimpleDateFormat("dd-mm-yyyy").parse(requests.get("endDate"));
				output = data.stream()
							.filter(r -> r != null && r.getShowType() != null && r.getCountry() != null
												   && r.getShowType().equalsIgnoreCase("TV Show")
												   && (r.getDateAdded().equals(lowerDate) || r.getDateAdded().after(lowerDate))
												   && (r.getDateAdded().equals(higherDate) || r.getDateAdded().before(higherDate)))
							.collect(Collectors.toList());
				return new ResponseEntity<>(output, HttpStatus.OK);
			} catch (ParseException e) {
				e.printStackTrace();
				return new ResponseEntity<>(new ArrayList<NetflixShowDetails>(), HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>(new ArrayList<NetflixShowDetails>(), HttpStatus.UNAUTHORIZED);
		}
	}	
	
	@GetMapping("movies")
	public ResponseEntity<List<NetflixShowDetails>> getHorrorMovies(@RequestParam(value = "movieType",
															required = false,
															defaultValue = "") String movieType,
													@RequestHeader(name = "X-Auth-Token",
																	required = true) String token) {
		if (token != null) {
			List<NetflixShowDetails> output = data.stream()
												.filter(r -> r != null && r.getShowType() != null && r.getListedIn() != null
																	   && r.getShowType().equalsIgnoreCase("Movie")
																	   && r.getListedIn().toLowerCase().contains(movieType.toLowerCase()))
												.collect(Collectors.toList());
			return new ResponseEntity<>(output, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ArrayList<NetflixShowDetails>(), HttpStatus.UNAUTHORIZED);
		}
	}
}