import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class NetflixDataAnalyzer {
	public static List<NetflixShowDetails> data = new LinkedList<NetflixShowDetails>();
	
	public static void csvDataLoader() {
		String path = "C:/Users/Subhro/Desktop/netflix_data.csv";
		String line = "";
		boolean firstRecord = true;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				if (firstRecord) {
					firstRecord = false;
					continue;
				}
				
				String values[] = line.split(",(?=([^\"]|\"[^\"]*\")*$)");
				Date dateAdded = null;
				try {
					if (values[6] != null) {
						dateAdded = new SimpleDateFormat("YYYY-MM-DD").parse(values[6]);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				NetflixShowDetails nextEntry = new NetflixShowDetails(values[0], values[1],
												values[2], values[3], values[4], values[5],
												dateAdded, values[7], values[8], values[9],
												values[10], values[11]);
				data.add(nextEntry);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/** This method will return First N Records where show Type is "TV Show" **/
	public List<NetflixShowDetails> getFirstNTVShowRecords(int n) {
		return data.stream()
				.filter(r -> r != null && r.getShowType() != null
									   && r.getShowType().equalsIgnoreCase("TV Show"))
				.limit(n)
				.collect(Collectors.toList());
				//.forEach(e-> System.out.println(e));
	}
	
	
	/** This method will return First N Records where Listed In as "Horror Movies" **/
	public List<NetflixShowDetails> getFirstNHorrorMovies(int n) {
		return data.stream()
				.filter(r -> r != null && r.getListedIn() != null
									   && r.getListedIn().toLowerCase().contains("horror movies"))
				.limit(n)
				.collect(Collectors.toList());
				//.forEach(e-> System.out.println(e));
	}
	
	
	/** This method will return First N Records where show Type is "Movie" and Country is "India" **/
	public List<NetflixShowDetails> getFirstNIndianMovies(int n) {
		return data.stream()
				.filter(r -> r != null && r.getShowType() != null && r.getCountry() != null
									   && r.getShowType().equalsIgnoreCase("Movie")
									   && r.getCountry().equalsIgnoreCase("India"))
				.limit(n)
				.collect(Collectors.toList());
				//.forEach(e-> System.out.println(e));
	}
	
	
	/** This method will return First N Records where show Type is "TV Show" and is with in the given Date range **/
	public List<NetflixShowDetails> getFirstNTVShowRecordsWithinDateRange(int n, Date lowerDate, Date higherDate) {
		//List<NetflixShowDetails> firstNTVShows = this.getFirstNTVShowRecords(n);
		return data.stream()
				.filter(r -> r != null && r.getShowType() != null && r.getDateAdded() != null
						     && r.getShowType().equalsIgnoreCase("TV Show")
							 && (r.getDateAdded().equals(lowerDate) || r.getDateAdded().after(lowerDate))
							 && (r.getDateAdded().equals(higherDate) || r.getDateAdded().before(higherDate)))
				.limit(n)
				.collect(Collectors.toList());
				//.forEach(e-> System.out.println(e));
	}
	
	
	/** This method will return First N Records where Listed In as "Horror Movies"  and is with in the given Date range **/
	public List<NetflixShowDetails> getFirstNHorrorMoviesWithinDateRange(int n, Date lowerDate, Date higherDate) {
		//List<NetflixShowDetails> firstNHorrorMovies = this.getFirstNHorrorMovies(n);
		return data.stream()
				.filter(r -> r.getDateAdded() != null && r.getListedIn() != null
						     && r.getListedIn().toLowerCase().contains("horror movies")
							 && (r.getDateAdded().equals(lowerDate) || r.getDateAdded().after(lowerDate))
							 && (r.getDateAdded().equals(higherDate) || r.getDateAdded().before(higherDate)))
				.limit(n)
				.collect(Collectors.toList());
				//.forEach(e-> System.out.println(e));
	}
	
	
	/** This method will return First N Records where show Type is "Movie" and Country is "India"  and is with in the given Date range **/
	public List<NetflixShowDetails> getFirstNIndianMoviesWithinDateRange(int n, Date lowerDate, Date higherDate) {
		//List<NetflixShowDetails> firstNIndianMovies = this.getFirstNIndianMovies(n);
		return data.stream()
				.filter(r -> r.getDateAdded() != null && r.getShowType() != null
							 && r.getCountry() != null && r.getShowType().equalsIgnoreCase("Movie")
							 && r.getCountry().equalsIgnoreCase("India")
						 	 && (r.getDateAdded().equals(lowerDate) || r.getDateAdded().after(lowerDate))
						 	 && (r.getDateAdded().equals(higherDate) || r.getDateAdded().before(higherDate)))
				.limit(n)
				.collect(Collectors.toList());
				//.forEach(e-> System.out.println(e));
	}
	
	
	public static void main(String[] args) {
		csvDataLoader(); // Load CSV Data into the List
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			long startTime = System.nanoTime(); 
			System.out.println("*************************************************************************************************");
			System.out.println("Welcome. Select any the option from below List -> ");
			System.out.println("1. Find first N Records where show Type as 'TV Show' ");
			System.out.println("2. Find first N Horror Movies ");
			System.out.println("3. Find first N Indian Movies ");
			System.out.println("4. Find first N Records where show Type as 'TV Show' within a Date Range ");
			System.out.println("5. Find first N Horror Movies within a Date Range ");
			System.out.println("6. Find first N Indian Movies within a Date Range ");
			System.out.println("7. Exit from this Section");
			System.out.println("*************************************************************************************************");
			
			System.out.println("\nChoose any one from the above options : ");
			try {
				int option = Integer.parseInt(sc.nextLine());
				if (option == 7) {
					sc.close();
					System.out.println("\n######   Exiting   ######");
					System.exit(0);
				}
				else if (option >= 1 && option <= 6) {
					System.out.println("Enter N : ");
					int n = Integer.parseInt(sc.nextLine());
					NetflixDataAnalyzer obj = new NetflixDataAnalyzer();
					
					if (option >= 4 && option <= 6) {
						System.out.println("Enter the Lower Date in YYYY-MM-DD format : ");
						String date1 = sc.nextLine();
						System.out.println("Enter the Higher Date in YYYY-MM-DD format : ");
						String date2 = sc.nextLine();
						
						Date lowerdate;
						Date higherDate;
						try {
							lowerdate = new SimpleDateFormat("YYYY-MM-DD").parse(date1);
							higherDate = new SimpleDateFormat("YYYY-MM-DD").parse(date2);
							if (lowerdate.after(higherDate)) {
								Date temp = lowerdate;
								lowerdate = higherDate;
								higherDate = temp;
							}
							
							System.out.println("\n############          OUTPUT STARTS         ############\n");
							switch (option) {
								case 4 : obj.getFirstNTVShowRecordsWithinDateRange(n, lowerdate, higherDate)
											.forEach(e -> System.out.println(e));
										 break;
								case 5 : obj.getFirstNHorrorMoviesWithinDateRange(n, lowerdate, higherDate)
											.forEach(e -> System.out.println(e));
										 break;
								case 6 : obj.getFirstNIndianMoviesWithinDateRange(n, lowerdate, higherDate)
											.forEach(e -> System.out.println(e));
									 	 break;
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("\n############          OUTPUT STARTS         ############\n");
						switch (option) {
							case 1 : obj.getFirstNTVShowRecords(n).forEach(e -> System.out.println(e));
									 break;
							case 2 : obj.getFirstNHorrorMovies(n).forEach(e -> System.out.println(e));
									 break;
							case 3 : obj.getFirstNIndianMovies(n).forEach(e -> System.out.println(e));
								 	 break;
						}
					}
					
					long endTime = System.nanoTime();
					double seconds = (double) (endTime - startTime) / 1000000000;
					System.out.println("\n#########         Elapsed Time in Seconds : -> " + seconds + "      ###########");
					System.out.println("\n############          OUTPUT ENDS          ############\n");
				} else {
					System.out.println("\n#########	Choose an appropiate option from the given List		#########");
				}
			} catch (Exception e) {
				System.out.println("\n#########	Choose an appropiate option from the given List		#########");
			}
		}
	}
}