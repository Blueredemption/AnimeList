import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JPanel;

public class StatisticsAggregator {
    ArrayList<String> filteredList;
    AnimeDao animeDao;
    Controller controller;

    // calculation values
    int totalMinutes;

    // gettable values
    String totalNumberOfAnimeWatched;
    String totalNumberOfEpisodesWatched;
    String days;
    String hours;
    String minutes;
    Object[] languageByAnimeCount, languageByEpisodeCount;
    Object[] seasons, year, startedYear, mainGenre, subGenre, studio, rating;
    JPanel graph;
    String scrollText;


    public StatisticsAggregator(AnimeDao animeDao, Controller controller){ // constuctor
        this.filteredList = animeDao.returnListOfFilteredReferences();
        this.animeDao = animeDao; 
        this.controller = controller;
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
    public Object[] getBasedOnKey(String key){
        
        switch(key){
            case "seasonReleased": return seasons;
            case "yearReleased": return year;
            case "watchingStartDate": return startedYear;
            case "mainGenre": return mainGenre;
            case "subGenre": return subGenre;
            case "animationStudio": return studio;
            case "ageRating": return rating;

            default:
                System.out.println("Invalid key in getBasedOnKey in StatisticsAggregator");
                return null; // will never reach here
        }
    }
    public JPanel getGraph(){
        return graph;
    }
    public String getScrollText(){
        return scrollText;
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
        seasons = generateFieldBoxes("seasonReleased");
        year = generateFieldBoxes("yearReleased");
        startedYear = generateFieldBoxes("watchingStartDate");
        mainGenre = generateFieldBoxes("mainGenre");
        subGenre = generateFieldBoxes("subGenre");
        studio = generateFieldBoxes("animationStudio");
        rating = generateFieldBoxes("ageRating");
        scrollText = generateScrollText();
    }
    public void generateGraphRelated(){
        graph = generateGraph();
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

    public Object[] generateFieldBoxes(String key){ // generic verson of the two methods above, much less hard code for use by variable size JComboBox lists
        ArrayList<SortableCountString> titles = generateTitlesList(key); // the length of this is the number of JComboBoxes in the UI, in the given section
        Object[] returnArray = new Object[titles.size()];
        for (int i = 0; i < titles.size(); i++){
            Object[] innerArray = new Object[3]; // 0-> references String array, 1 -> entry title String array, 2 -> title String

            innerArray[0] = getFilteredReferences(titles.get(i).getString(),key);

            innerArray[1] = getNameArray((String[])innerArray[0]);

            innerArray[2] = titles.get(i).getString() +": (" +((String[])innerArray[0]).length +" Anime)";

            returnArray[i] = innerArray;
        }
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

    public String[] getFilteredReferences(String varient, String key /* attribute */){ // varient is to subbed as key is to language
        ArrayList<String> aggregateList = new ArrayList<String>();
        ArrayList<String> newFilteredList = animeDao.returnListOfFilteredReferences();
        for (String reference : newFilteredList) {
            if (key.equals("watchingStartDate")){
                if (getYearAsString(reference,key).equals(varient)) aggregateList.add(reference);
            }
            else{
                if (animeDao.getValue(reference,key).equals(varient)) aggregateList.add(reference);
            }    
        }
        return aggregateList.toArray(new String[0]);
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

    public int countEpisodesWatched(String[] list){ // this counts the episodes watched for the given reference array
        int sumOfEpisodesWatched = 0;
        for (String reference : list) {
            int episodesWatched = Integer.parseInt(animeDao.getValue(reference, "numberOfEpisodesWatched"));
            sumOfEpisodesWatched += episodesWatched;
        }
        return sumOfEpisodesWatched;
    }

    public ArrayList<SortableCountString> generateTitlesList(String key){
        ArrayList<SortableCountString> SCSs = new ArrayList<SortableCountString>();
        ArrayList<String> references = animeDao.returnListOfFilteredReferences();
        for (String reference : references) {
            if (key.equals("watchingStartDate")){ // since watchingStartDate isn't the same as year started it has a special iteration
                if (SCSs.indexOf((new SortableCountString(getYearAsString(reference,key))))==-1){ // key is not in there, add it
                    SCSs.add(new SortableCountString(getYearAsString(reference,key)));
                }
                else{ // key is in there, increase counter corresponding to that key
                    int index = SCSs.indexOf((new SortableCountString(getYearAsString(reference,key))));
                    SortableCountString value = SCSs.get(index);
                    value.increase();
                }
            }
            else{
                if (SCSs.indexOf((new SortableCountString(animeDao.getValue(reference,key))))==-1){ // key is not in there, add it
                    SCSs.add(new SortableCountString(animeDao.getValue(reference,key)));
                }
                else{ // key is in there, increase counter corresponding to that key
                    int index = SCSs.indexOf((new SortableCountString(animeDao.getValue(reference,key))));
                    SortableCountString value = SCSs.get(index);
                    value.increase();
                }
            }
        } 
        // now that we have generated the list we need it sorted
        Collections.sort(SCSs, new scsCollectionsComparator());
        return SCSs;
    }

    public String getYearAsString(String reference, String key){
        Date date;
        try{
            date = new SimpleDateFormat("yyyy-MM-dd").parse(animeDao.getValue(reference,key));
        }
        catch (Exception E) {
            date = new Date();
            //System.out.println("parseing of date string failed, assuming the field is empty.");
            return "?";
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy");  
        return dateFormat.format(date); 
    }
    
    // graph related methods
    public JPanel generateGraph(){
        ArrayList<Integer> releaseYearList = new ArrayList<Integer>();
        ArrayList<Integer> releaseYearCounter = new ArrayList<Integer>();
        ArrayList<Integer> startYearList = new ArrayList<Integer>();
        ArrayList<Integer> startYearCounter = new ArrayList<Integer>();

        int oldestReleaseYear = getOldestReleaseYear();
        int oldestStartYear =getOldestStartYear();

        for(int i = oldestReleaseYear; i <= Calendar.getInstance().get(Calendar.YEAR); i++){
            releaseYearList.add(i);
            releaseYearCounter.add(0);
        }

        for(int i = oldestStartYear; i <= Calendar.getInstance().get(Calendar.YEAR); i++){
            startYearList.add(i);
            startYearCounter.add(0);
        }

        ArrayList<String> references = animeDao.returnListOfFilteredReferences();

        for (String reference : references) {
            int index = releaseYearList.indexOf(Integer.parseInt(animeDao.getValue(reference,"yearReleased")));
            if (index != -1){
                releaseYearCounter.set(index,releaseYearCounter.get(index) + 1);
            }

            if (animeDao.getValue(reference, "watchingStartDate").equals("?")) continue;
            index = startYearList.indexOf(Integer.parseInt(getYearAsString(reference,"watchingStartDate")));
            if (index != -1){
                startYearCounter.set(index,startYearCounter.get(index) + 1);
            }
        }
        return new StatisticsChart(releaseYearList, releaseYearCounter, startYearList, startYearCounter, controller);
    }

    public int getOldestReleaseYear(){
        int oldestReleaseYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> references = animeDao.returnListOfFilteredReferences();
        for (String reference : references) {
            int year = Integer.parseInt(animeDao.getValue(reference,"yearReleased"));
            if (year < oldestReleaseYear) oldestReleaseYear = year; 
        }
        return oldestReleaseYear;
    }

    public int getOldestStartYear(){
        int oldestStartYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> references = animeDao.returnListOfFilteredReferences();
        for (String reference : references) {
            if (animeDao.getValue(reference, "watchingStartDate").equals("?")) continue;
            int year = Integer.parseInt(getYearAsString(reference,"watchingStartDate"));
            if (year < oldestStartYear) oldestStartYear = year; 
        }
        return oldestStartYear;
    }

    // scrolltext related methods
    public String generateScrollText(){
        String scrollString = "";
        String string = "\u2002\u2002\u2002\u2002\u2002\u2002\u2002\u2002\u2002\u2002\u2002\u2002";
        scrollString = scrollString +string +generateLongestAnime();
        scrollString = scrollString +string +generateShortestAnime();
        scrollString = scrollString +string +generateNewestAnime();
        scrollString = scrollString +string +generateOldestAnime();
        scrollString = scrollString +string +generateFirstAnime();
        scrollString = scrollString +string +generateLastAnime();
        scrollString = scrollString +string +generateLongestSpan();
        scrollString = scrollString +string +generateShortestSpan();

        return scrollString;
    }

    public String generateLongestAnime(){
        ArrayList<String> references = animeDao.returnListOfFilteredReferences();
        String currentReference = "";
        int currentLength = 0; 

        for (String reference : references) {
            int tempLength = Integer.parseInt(animeDao.getValue(reference,"numberOfEpisodesWatched"));
            if (tempLength > currentLength){
                currentReference = reference;
                currentLength = tempLength;
            }
        }
        return "Longest Anime Watched: " +animeDao.getValue(currentReference,"animeName") +" (" +currentLength +" Episodes)";
    }

    public String generateShortestAnime(){
        ArrayList<String> references = animeDao.returnListOfFilteredReferences();
        String currentReference = "";
        int currentLength = 999999999; 

        for (String reference : references) {
            int tempLength = Integer.parseInt(animeDao.getValue(reference,"numberOfEpisodesTotal"));
            if (tempLength < currentLength){
                currentReference = reference;
                currentLength = tempLength;
            }
        }
        return "Shortest Anime Watched: " +animeDao.getValue(currentReference,"animeName") +" (" +currentLength +" Episodes)";
    }

    public String generateNewestAnime(){
        int preSort = animeDao.getSort();
        int preOrder = animeDao.getOrder();
        animeDao.setSort(4); // setting the sort to sort by year released
        animeDao.setOrder(1); 

        ArrayList<String> references = animeDao.returnListOfFilteredReferences();

        String reference;
        if (references.size() > 0) reference = references.get(0);
        else reference = "";

        animeDao.setSort(preSort); // return sort to original state since calls are over, as no "changes" are to be made in data collection
        animeDao.setOrder(preOrder); // ^

        return "Newest Anime Watched: "  +animeDao.getValue(reference,"animeName") +" (" +animeDao.getValue(reference,"seasonReleased") +" " +animeDao.getValue(reference,"yearReleased") +")";
    }

    public String generateOldestAnime(){
        int preSort = animeDao.getSort();
        int preOrder = animeDao.getOrder();
        animeDao.setSort(4); // setting the sort to sort by year released
        animeDao.setOrder(0); 

        ArrayList<String> references = animeDao.returnListOfFilteredReferences();
        
        String reference;
        if (references.size() > 0) reference = references.get(0);
        else reference = "";

        animeDao.setSort(preSort); // return sort to original state since calls are over, as no "changes" are to be made in data collection
        animeDao.setOrder(preOrder); // ^

        return "Oldest Anime Watched: "  +animeDao.getValue(reference,"animeName") +" (" +animeDao.getValue(reference,"seasonReleased") +" " +animeDao.getValue(reference,"yearReleased") +")";
    }

    public String generateFirstAnime(){
        int preSort = animeDao.getSort();
        int preOrder = animeDao.getOrder();
        animeDao.setSort(0); // setting the sort to sort by year released
        animeDao.setOrder(1); 

        ArrayList<String> references = animeDao.returnListOfFilteredReferences();
        
        String reference;
        if (references.size() > 0) reference = references.get(0);
        else reference = "";

        animeDao.setSort(preSort); // return sort to original state since calls are over, as no "changes" are to be made in data collection
        animeDao.setOrder(preOrder); // ^

        return "First Anime Watched: "  +animeDao.getValue(reference,"animeName") +" (" +controller.getDateAsString(reference,"watchingStartDate") +")";
    }

    public String generateLastAnime(){
        int preSort = animeDao.getSort();
        int preOrder = animeDao.getOrder();
        animeDao.setSort(0); // setting the sort to sort by year released
        animeDao.setOrder(0); 

        ArrayList<String> references = animeDao.returnListOfFilteredReferences();
        
        String reference;
        if (references.size() > 0) reference = references.get(0);
        else reference = "";

        animeDao.setSort(preSort); // return sort to original state since calls are over, as no "changes" are to be made in data collection
        animeDao.setOrder(preOrder); // ^

        return "Most Recent Anime Watched: "  +animeDao.getValue(reference,"animeName") +" (" +controller.getDateAsString(reference,"watchingStartDate") +")";
    }

    public String generateLongestSpan(){
        ArrayList<String> references = animeDao.returnListOfFilteredReferences();
        String currentReference = "";
        long currentLength = 0; 

        for (String reference : references) {
            long tempLength = controller.getSpanNumber(reference);
            if (tempLength > currentLength){
                currentReference = reference;
                currentLength = tempLength;
            }
        }
        return "Longest Time Taken to Finish: " +animeDao.getValue(currentReference,"animeName") +" (" +controller.getSpan(currentReference)+")";
    }

    public String generateShortestSpan(){
        ArrayList<String> references = animeDao.returnListOfFilteredReferences();
        String currentReference = "";
        long currentLength = 999999999; 

        for (String reference : references) {
            long tempLength = controller.getSpanNumber(reference);
            if ((tempLength < currentLength)&&(tempLength != -1)){
                currentReference = reference;
                currentLength = tempLength;
            }
        }
        return "Shortest Time Taken to Finish: " +animeDao.getValue(currentReference,"animeName") +" (" +controller.getSpan(currentReference)+")";
    }

    // this is to make Collections.sort(list) of these objects work
    public class scsCollectionsComparator implements Comparator<SortableCountString> {

        @Override
        public int compare(SortableCountString o1, SortableCountString o2) {
            return o1.compareTo(o2);
        }
    }
}
