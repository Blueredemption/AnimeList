import java.util.ArrayList;

public class StatisticsAggregator {
    ArrayList<String> fullList;
    ArrayList<String> filteredList;
    AnimeDao animeDao;

    public StatisticsAggregator(ArrayList<String> fullList, ArrayList<String> filteredList, AnimeDao animeDao){ // constuctor
        this.fullList = fullList;
        this.filteredList = filteredList;
        this.animeDao = animeDao; 

        // generate method
    }

    // a ton of getters

    // methods that call methods to generate specific parts

    // methods that are those specific parts
}