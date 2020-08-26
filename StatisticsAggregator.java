import java.util.ArrayList;

public class StatisticsAggregator {
    ArrayList<String> filteredList;
    AnimeDao animeDao;

    // calculation values
    int totalMinutes;

    // gettable values
    String totalNumberOfAnimeWatched;
    String totalNumberOfEpisodesWatched;
    String days;
    String hours;
    String minutes;
    Object[] languageByAnimeCount;
    Object[] languageByEpisodeCount;

    public StatisticsAggregator(AnimeDao animeDao){ // constuctor
        this.filteredList = animeDao.returnListOfFilteredReferences();
        this.animeDao = animeDao; 
    }

    // a ton of getters (there are no setters for this, as all these values are permanent in instance)
    public String getTotalNumberOfAnimeWatched(){
        return totalNumberOfAnimeWatched;
    }
    public String getTotalNumberOfEpisodesWatched(){
        return totalNumberOfEpisodesWatched;
    }
    public String getDays(){
        return days;
    }
    public String getHours(){
        return hours;
    }
    public String getMinutes(){
        return minutes;
    }
    public Object[] getLanguageByAnimeCount(){
        return languageByAnimeCount;
    }
    public Object[] getLanguageByEpisodeCount(){
        return languageByEpisodeCount;
    }

    // methods that call methods to generate specific parts
    public void generateGeneralStatistics(){
        totalMinutes = generateTotalMinutes();

        totalNumberOfAnimeWatched = generateTotalNumberOfAnimeWatched();
        totalNumberOfEpisodesWatched = generateTotalNumberOfEpisodesWatched();
        days = generateDays();
        hours = generateHours();
        minutes = generateMinutes();
    }
    public void generateFilteredStatistics(){
        languageByAnimeCount = generateLanguageByAnimeCount();
        languageByEpisodeCount = generateLanguageByEpisodeCount();
    }
    public void generateGraphRelated(){

    }

    // methods that are those specific parts

    // generateGeneralStatistics()
    public String generateTotalNumberOfAnimeWatched(){
        return filteredList.size() +"";
    }
    public String generateTotalNumberOfEpisodesWatched(){
        int sumOfEpisodesWatched = 0;
        for (String reference : filteredList) {
            sumOfEpisodesWatched += Integer.parseInt(animeDao.getValue(reference,"numberOfEpisodesWatched"));
        }
        return sumOfEpisodesWatched +"";
    }
    public String generateDays(){
        return totalMinutes/1440 +"";
    }
    public String generateHours(){
        return (totalMinutes%1440)/60 +"";
    }
    public String generateMinutes(){
        return (totalMinutes%1440)%60 +"";
    }

    // generateFilteredStatistics()
    public Object[] generateLanguageByAnimeCount(){
        int length = 3;
        String[] titles = {"Subbed", "Dubbed", "? / Other"};
        Object[] returnArray = new Object[length];
        for (int i = 0; i < length; i++){
            Object[] innerArray = new Object[3]; // 0-> references String array, 1 -> entry title String array, 2 -> title String

            innerArray[0] = getFilteredReferences(titles[i]);

            innerArray[1] = getNameArray((String[])innerArray[0]);

            innerArray[2] = titles[i] +": (" +((String[])innerArray[0]).length +" Anime)";

            returnArray[i] = innerArray;
        }
        return returnArray;
    }

    public Object[] generateLanguageByEpisodeCount(){
        int preSort = animeDao.getSort();
        int preOrder = animeDao.getOrder();
        animeDao.setSort(7); // setting the sort to sort by episodes watched
        animeDao.setOrder(1); // ordering so high episode watched numbers are first
        int length = 3;
        String[] titles = {"Subbed", "Dubbed", "? / Other"};
        Object[] returnArray = new Object[length];
        for (int i = 0; i < length; i++){
            Object[] innerArray = new Object[3]; // 0-> references String array, 1 -> entry title String array, 2 -> title String

            innerArray[0] = getFilteredReferences(titles[i]);

            innerArray[1] = getNameArrayWithEpisodesWatched((String[])innerArray[0]);

            innerArray[2] = titles[i] +": (" +countEpisodesWatched((String[])innerArray[0]) +" Episodes)";

            returnArray[i] = innerArray;
        }
        animeDao.setSort(preSort); // return sort to original state since calls are over, as no "changes" are to be made in data collection
        animeDao.setOrder(preOrder); // ^
        return returnArray;
    }






    // calculation and general aggregation methods methods
    public int generateTotalMinutes(){
        int sumOfMinutes = 0;
        for (String reference : filteredList) {
            sumOfMinutes += getShowMinutes(reference);
        }
        return sumOfMinutes;
    }
    public int getShowMinutes(String reference){
        int averageMinutes = Integer.parseInt(animeDao.getValue(reference, "averageEpisodeLength"));
        int episodesWatched = Integer.parseInt(animeDao.getValue(reference, "numberOfEpisodesWatched"));
        return (averageMinutes*episodesWatched);
    }

    public String[] getFilteredReferences(String switchString){
        ArrayList<String> aggregateList = new ArrayList<String>();
        ArrayList<String> newFilteredList = animeDao.returnListOfFilteredReferences();
        switch(switchString){
            case "Subbed": 
                for (String reference : newFilteredList) {
                    if (animeDao.getValue(reference,"languageWatchedIn").equals("Subbed")) aggregateList.add(reference);
                }
                return aggregateList.toArray(new String[0]);
            case "Dubbed": 
                for (String reference : newFilteredList) {
                    if (animeDao.getValue(reference,"languageWatchedIn").equals("Dubbed")) aggregateList.add(reference);
                }
                return aggregateList.toArray(new String[0]);
            case "? / Other": 
                for (String reference : newFilteredList) {
                    if (animeDao.getValue(reference,"languageWatchedIn").equals("Other")||animeDao.getValue(reference,"languageWatchedIn").equals("?")) aggregateList.add(reference);
                }
                return aggregateList.toArray(new String[0]);
            default: return new String[0]; // will never reach here
        }
    }

    public String[] getNameArray(String[] referenceArray){
        ArrayList<String> aggregateList = new ArrayList<String>();
        for (String reference : referenceArray) {
            aggregateList.add(animeDao.getValue(reference,"animeName"));
        }
        if (aggregateList.size()==0) aggregateList.add("");
        return aggregateList.toArray(new String[0]);
    }

    public String[] getNameArrayWithEpisodesWatched(String[] referenceArray){
        ArrayList<String> aggregateList = new ArrayList<String>();
        for (String reference : referenceArray) {
            aggregateList.add("(" +animeDao.getValue(reference,"numberOfEpisodesWatched") +") " +animeDao.getValue(reference,"animeName"));
        }
        if (aggregateList.size()==0) aggregateList.add("");
        return aggregateList.toArray(new String[0]);
    }

    public int countEpisodesWatched(String[] list){ // this counts the episodes watched for the given array string
        int sumOfEpisodesWatched = 0;
        for (String reference : list) {
            int episodesWatched = Integer.parseInt(animeDao.getValue(reference, "numberOfEpisodesWatched"));
            sumOfEpisodesWatched += episodesWatched;
        }
        return sumOfEpisodesWatched;
    }
}
