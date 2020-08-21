// The purpose of this class is to act as the "Anime Object" that is stored as a json file on the user's hard drive. 
// Every time a change is made to one of these objects, a change is made inside the hard drive file by the setter method.

import org.json.simple.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.Color;


public class AnimeObject implements Comparable<Object>{ 
    // declared members
    String animeName;
    String numberOfEpisodesWatched;
    String numberOfEpisodesTotal;
    String languageWatchedIn;
    String ageRating;
    String yearReleased;
    String seasonReleased;
    String watchingStartDate;
    String watchingEndDate;
    String averageEpisodeLength; // in minutes
    String referenceNumber; // reference number used to differentiate between anime json files
    String notepadText; 
    String imageLocation; // file location of associated image
    Color color;
    String customColor; // "boolean" that tells the controller color is selected as active
    String mainGenre;
    String subGenre;
    String hidden;
    String animationStudio;

    int sort;

    public AnimeObject(){ // default constructor
        animeName = "New Anime";
        numberOfEpisodesWatched = "0";
        numberOfEpisodesTotal = "0";
        languageWatchedIn = "?";
        ageRating = "?";
        yearReleased = "1900";
        seasonReleased = "Spring";
        watchingStartDate = "?";
        watchingEndDate = "?";
        averageEpisodeLength = "20";
        referenceNumber = determineNRN();
        notepadText = "";
        imageLocation = "Images/UI/Default.png"; 
        color = new Color(255,255,255,255);
        customColor = "true";
        mainGenre = "?";
        subGenre = "?";
        hidden = "false";
        animationStudio = "?";
        refreshJSON(); // creates the json for this object

        sort = 0;
    }

    public AnimeObject(JSONObject animeObject, int sort){ // full constructor
        animeName = (String)animeObject.get("animeName");
        numberOfEpisodesWatched = (String)animeObject.get("numberOfEpisodesWatched");
        numberOfEpisodesTotal = (String)animeObject.get("numberOfEpisodesTotal");
        languageWatchedIn = (String)animeObject.get("languageWatchedIn");
        ageRating = (String)animeObject.get("ageRating");
        yearReleased = (String)animeObject.get("yearReleased");
        seasonReleased = (String)animeObject.get("seasonReleased");
        watchingStartDate = (String)animeObject.get("watchingStartDate");
        watchingEndDate = (String)animeObject.get("watchingEndDate");
        averageEpisodeLength = (String)animeObject.get("averageEpisodeLength");
        referenceNumber = (String)animeObject.get("referenceNumber");
        notepadText = (String)animeObject.get("notepadText");
        imageLocation = (String)animeObject.get("imageLocation");
        color = constructColorFromJSON(animeObject); // because color is an object not supported by this JSON library
        customColor = (String)animeObject.get("customColor");
        mainGenre = (String)animeObject.get("mainGenre");
        subGenre = (String)animeObject.get("subGenre");
        hidden = (String)animeObject.get("hidden");
        animationStudio = (String)animeObject.get("animationStudio");

        this.sort = sort;
    }
    // Getters

    public String getAnimeName(){
        return animeName;
    }
    public String getNumberOfEpisodesWatched(){
        return numberOfEpisodesWatched;
    }
    public String getNumberOfEpisodesTotal(){
        return numberOfEpisodesTotal;
    }
    public String getLanguageWatchedIn(){
        return languageWatchedIn;
    }
    public String getAgeRating(){
        return ageRating;
    }
    public String getYearReleased(){
        return yearReleased;
    }
    public String getSeasonReleased(){
        return seasonReleased;
    }
    public String getWatchingStartDate(){
        return watchingStartDate;
    }
    public String getWatchingEndDate(){
        return watchingEndDate;
    }
    public String getAverageEpisodeLength(){
        return averageEpisodeLength;
    }
    public String getReferenceNumber(){
        return referenceNumber;
    }
    public String getNotepadText(){
        return notepadText;
    }
    public String getImageLocation(){
        return imageLocation;
    }
    public Color getColor(){
        return color;
    }
    public String getCustomColor(){
        return customColor;
    }
    public String getMainGenre(){
        return mainGenre;
    }
    public String getSubGenre(){
        return subGenre;
    }
    public String getHidden(){
        return hidden;
    }
    public String getAnimationStudio(){
        return animationStudio;
    }
    public Double getProgress(){
        if (Integer.parseInt(numberOfEpisodesTotal)== 0) return 0.0;
        return Double.parseDouble(numberOfEpisodesWatched)/Double.parseDouble(numberOfEpisodesTotal)*100;
    }


    // Setters

    public void setAnimeName(String animeName){
        this.animeName = animeName;
        refreshJSON();
    }
    public void setNumberOfEpisodesWatched(String numberOfEpisodesWatched){
        this.numberOfEpisodesWatched = numberOfEpisodesWatched;
        refreshJSON();
    }
    public void setNumberOfEpisodesTotal(String numberOfEpisodesTotal){
        this.numberOfEpisodesTotal = numberOfEpisodesTotal;
        refreshJSON();
    }
    public void setLanguageWatchedIn(String languageWatchedIn){
        this.languageWatchedIn = languageWatchedIn;
        refreshJSON();
    }
    public void setAgeRating(String ageRating){
        this.ageRating = ageRating;
        refreshJSON();
    }
    public void setYearReleased(String yearReleased){
        this.yearReleased = yearReleased;
        refreshJSON();
    }
    public void setSeasonReleased(String seasonReleased){
        this.seasonReleased = seasonReleased;
        refreshJSON();
    }
    public void setWatchingStartDate(String watchingStartDate){
        this.watchingStartDate = watchingStartDate;
        refreshJSON();
    }
    public void setWatchingEndDate(String watchingEndDate){
        this.watchingEndDate = watchingEndDate;
        refreshJSON();
    }
    public void setAverageEpisodeLength(String averageEpisodeLength){
        this.averageEpisodeLength = averageEpisodeLength;
        refreshJSON();
    }
    public void setReferenceNumber(String referenceNumber){
        this.referenceNumber = referenceNumber;
        refreshJSON();
    }
    public void setNotepadText(String notepadText){
        this.notepadText = notepadText;
        refreshJSON();
    }
    public void setImageLocation(String imageLocation){
        this.imageLocation = imageLocation;
        refreshJSON();
    }
    public void setColor(Color color){
        this.color = color;
        setCustomColor("false");
        refreshJSON();
    }
    public void setCustomColor(String customColor){
        this.customColor = customColor;
        refreshJSON();
    }
    public void setMainGenre(String mainGenre){
        this.mainGenre = mainGenre;
        refreshJSON();
    }
    public void setSubGenre(String subGenre){
        this.subGenre = subGenre;
        refreshJSON();
    }
    public void setHidden(String hidden){
        this.hidden = hidden;
        refreshJSON();
    }
    public void setAnimationStudio(String animationStudio){
        this.animationStudio = animationStudio;
        refreshJSON();
    }
    public void setSort(int sort){
        this.sort = sort;
    }

    @SuppressWarnings("all") // removing the warning "references to generic type HashMap<K,V> should be parameterized" that is due to the age of the json library I am using.
    public boolean refreshJSON(){// method for rewriting the json files after a change is made in the live object or to create it outright.
        JSONObject animeJSON = new JSONObject(); // creating the json and adding all of the elements
        animeJSON.put("animeName",animeName);
        animeJSON.put("numberOfEpisodesWatched",numberOfEpisodesWatched);
        animeJSON.put("numberOfEpisodesTotal",numberOfEpisodesTotal);
        animeJSON.put("languageWatchedIn",languageWatchedIn);
        animeJSON.put("ageRating",ageRating);
        animeJSON.put("yearReleased",yearReleased);
        animeJSON.put("seasonReleased",seasonReleased);
        animeJSON.put("watchingStartDate",watchingStartDate);
        animeJSON.put("watchingEndDate",watchingEndDate);
        animeJSON.put("averageEpisodeLength",averageEpisodeLength);
        animeJSON.put("referenceNumber",referenceNumber);
        animeJSON.put("notepadText",notepadText);
        animeJSON.put("imageLocation",imageLocation);
        animeJSON.put("customColor",customColor);
        animeJSON.put("mainGenre",mainGenre);
        animeJSON.put("subGenre",subGenre);
        animeJSON.put("hidden",hidden);
        animeJSON.put("animationStudio",animationStudio);

        // because color is an object not supported by this JSON library
        animeJSON.put("r",color.getRed());
        animeJSON.put("g",color.getGreen());
        animeJSON.put("b",color.getBlue());
        animeJSON.put("a",color.getAlpha());


        try { // printing to the json file
            final FileWriter fw = new FileWriter("Anime Objects/" +referenceNumber +".json");
            final PrintWriter pw = new PrintWriter(fw);
            pw.println(animeJSON.toString());
            pw.close();
        } catch (final IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public String determineNRN(){// method that determines the newest reference number for a new anime (for default constructor)
        // gather all of the file names 
        String[] fileNames;
        File file = new File("Anime Objects");
        fileNames = file.list();
        // find the highest number n and return 1+n
        int n = 0;
        if (fileNames.length==0){
            return "" + n;
        }
        else{
            for (int i = 0; i < fileNames.length; i++){
                int m = Integer.parseInt(fileNames[i].substring(0,fileNames[i].length()-5));
                if(m >= n){
                    n = m+1;
                }
            }
            return "" + n;
        }
    }

    public Color constructColorFromJSON(JSONObject json){ // takes a json that stores a color and returns a color object of it
        return new Color(((Long)json.get("r")).intValue(),
                         ((Long)json.get("g")).intValue(),
                         ((Long)json.get("b")).intValue(),
                         ((Long)json.get("a")).intValue());
    }
  
    public Date getDateObject(String dateString){
        Date date;
        try{
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            return date;
        }
        catch (Exception E) {
            return null;
        }
    }

    public Integer getSeasonNumber(){
        switch(seasonReleased){
            case "Spring": return 1;
            case "Summer": return 2;
            case "Fall": return 3;
            case "Winter": return 4;
            default: return 5;
        }
    }

    public int compareTo(final Object o) {
        switch(sort){
            case 0: 
                if ((getDateObject(watchingStartDate)!=null)&&(getDateObject(((AnimeObject)o).getWatchingStartDate())!=null)) {
                    return -getDateObject(watchingStartDate).compareTo(getDateObject(((AnimeObject)o).getWatchingStartDate()));
                }
                else if  ((getDateObject(watchingStartDate)==null)&&(getDateObject(((AnimeObject)o).getWatchingStartDate())==null)) {
                    return -((Integer)Integer.parseInt(referenceNumber)).compareTo((Integer)Integer.parseInt(((AnimeObject)o).getReferenceNumber()));
                }
                else if (getDateObject(watchingStartDate)==null){
                    return 1;
                }
                else { 
                    return -1;
                }
            case 1: return animeName.toUpperCase().compareTo(((AnimeObject)o).getAnimeName().toUpperCase());
            case 2: return ((Integer)Integer.parseInt(numberOfEpisodesTotal)).compareTo((Integer)Integer.parseInt(((AnimeObject)o).getNumberOfEpisodesTotal()));
            case 3: return (getSeasonNumber()*10000 +((Integer)Integer.parseInt(yearReleased)).compareTo(((AnimeObject)o).getSeasonNumber()*10000 +(Integer.parseInt(((AnimeObject)o).getYearReleased()))));
            case 4: return ((Integer)(Integer.parseInt(yearReleased)*100+getSeasonNumber())).compareTo((Integer)(Integer.parseInt(((AnimeObject)o).getYearReleased())*100+((AnimeObject)o).getSeasonNumber())); 
            case 5: return getProgress().compareTo(((AnimeObject)o).getProgress());
            case 6: return ((Integer)color.getRGB()).compareTo((Integer)((AnimeObject)o).getColor().getRGB());
            default: return 0;
        }
    }
}
