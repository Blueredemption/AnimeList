//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//

import java.util.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.awt.Color;

public class AnimeDao {
    ArrayList<AnimeObject> list;
    int sort;
    int order;
 
    ArrayList<String> seasons, languages, contentRatings, genres;
    

    public AnimeDao(){ // constructor
        refresh();
        generateLists();
        sort = 0;
        order = 0;
    }

    public String create(){
        AnimeObject anime = new AnimeObject();
        refresh();
        return anime.getReferenceNumber();
    }

    public void generateLists(){
        seasons = new ArrayList<String>(Arrays.asList("Spring","Summer","Fall","Winter"));
        languages = new ArrayList<String>(Arrays.asList("Subbed","Dubbed", "Other"));
        contentRatings = new ArrayList<String>(Arrays.asList("G","PG","PG-13","R-17+","R+"));
        genres = new ArrayList<String>(Arrays.asList("Action","Adventure","Comedy","Dementia","Demons","Drama","Ecchi","Fantasy","Hentai",
                                                     "Historical","Horror","Harem","Isekai","Josei","Magic","Martial Arts","Mecha","Military","Music",
                                                     "Mystery","Parody","Police","Psychological","Romance","Samurai","School","Sci-Fi",
                                                     "Seinen","Shoujo","Shoujo Ai","Shounen","Shounen Ai","Slice of Life","Space","Sports",
                                                     "Super Power","Supernatural","Thriller","Vampire","Yaoi","Yuri"));
    }
    
    public void refresh(){ // refreshes the list of anime objects
        String[] pathnames;
        String prePath = "Anime Objects";
        File f = new File(prePath);
        list = new ArrayList<AnimeObject>();
        pathnames = f.list();
        if (pathnames == null){ // if pathnames is null then there are no anime objects to iterate through, thus create one and try again.
            create();
            refresh();
        }
        else{
            for (String pathname : pathnames) {
                JSONObject temp = getJSON(prePath +"/" +pathname);
                AnimeObject anime = new AnimeObject(temp, sort);
                list.add(anime);
            }
        }
    }

    public String updateColor(String reference, Color color){
        for (AnimeObject anime : list) {
            if (anime.getReferenceNumber().equals(reference)) {
                anime.setColor(color);
                return "update successful";
            }
        }
        return "update unsuccessful: key not found";
    }

    public String update(String reference, String key, String value){
        for (AnimeObject anime : list) {
            if (anime.getReferenceNumber().equals(reference)){
                switch (key){
                    case "animeName": anime.setAnimeName(value); break;
                    case "numberOfEpisodesWatched": anime.setNumberOfEpisodesWatched(value); break;
                    case "numberOfEpisodesTotal": anime.setNumberOfEpisodesTotal(value); break;
                    case "languageWatchedIn": anime.setLanguageWatchedIn(value); break;
                    case "ageRating": anime.setAgeRating(value); break;
                    case "yearReleased": anime.setYearReleased(value); break;
                    case "seasonReleased": anime.setSeasonReleased(value); break;
                    case "watchingStartDate": anime.setWatchingStartDate(value); break;
                    case "watchingEndDate": anime.setWatchingEndDate(value); break;
                    case "averageEpisodeLength": anime.setAverageEpisodeLength(value); break;
                    case "notepadText": anime.setNotepadText(value); break;
                    case "imageLocation": anime.setImageLocation(value); break;
                    case "customColor": anime.setCustomColor(value); break;
                    case "mainGenre": anime.setMainGenre(value); break;
                    case "subGenre": anime.setSubGenre(value); break;
                    case "hidden": anime.setHidden(value); break;
                    case "animationStudio": anime.setAnimationStudio(value); break;
                    default: return "update unsuccessful: unknown key"; 
                 }
                 return "update successful"; 
            }
        }
        return "update unsuccessful: unknown reference";
    }

    public String delete(String reference){
        String prePath = "Anime Objects";
        File File = new File(prePath +"/" +reference +".json"); 
        if (File.delete()) { 
            refresh();
            return "Deleted the file: " + File.getName();
        } 
        else {
            return "Failed to delete the file.";
        } 
    }

    public String getValue(String reference, String key){
        for (AnimeObject anime : list) {
            if (anime.getReferenceNumber().equals(reference)){
                switch (key){
                    case "animeName": return anime.getAnimeName();
                    case "numberOfEpisodesWatched": return anime.getNumberOfEpisodesWatched();
                    case "numberOfEpisodesTotal": return anime.getNumberOfEpisodesTotal();
                    case "languageWatchedIn": return anime.getLanguageWatchedIn();
                    case "ageRating": return anime.getAgeRating();
                    case "yearReleased": return anime.getYearReleased();
                    case "seasonReleased": return anime.getSeasonReleased();
                    case "watchingStartDate": return anime.getWatchingStartDate();
                    case "watchingEndDate": return anime.getWatchingEndDate();
                    case "averageEpisodeLength": return anime.getAverageEpisodeLength();
                    case "notepadText": return anime.getNotepadText();
                    case "imageLocation": return anime.getImageLocation();
                    case "customColor": return anime.getCustomColor();
                    case "mainGenre": return anime.getMainGenre();
                    case "subGenre": return anime.getSubGenre();
                    case "hidden": return anime.getHidden();
                    case "animationStudio": return anime.getAnimationStudio();
                    case "referenceNumber": return anime.getReferenceNumber();
                    case "progress": return anime.getProgress() + "";
                    default: return "retrieval unsuccessful: unknown key"; 
                 } 
            }
        }
        return "retrieval unsuccessful: reference not found";
    }

    public Color getColor(String reference){
        for (AnimeObject anime : list) {
            if (anime.getReferenceNumber().equals(reference)){
                return anime.getColor();
            }
        }
        return Color.BLACK;
    }
    public void setSort(int sort){
        this.sort = sort;
        for (AnimeObject animeObject : list) {
            animeObject.setSort(sort);
        }
    }
    public void setOrder(int order){
        this.order = order;
    }

    public ArrayList<String> returnListOfReferences(){  // no filtering
        Collections.sort(list);
        ArrayList<String> references = new ArrayList<String>();
        for (AnimeObject anime : list) {
            references.add(anime.getReferenceNumber());
        }
        if (order == 0) return references;
        Collections.reverse(references);
        return references; 
    }

    public ArrayList<String> returnListOfFilteredReferences(){ // filtered
        return returnListOfReferences();
    }

    public ArrayList<String> returnListOfSearchedReferences(String inquiry){ // filtered and searched
        return narrowedList(returnListOfFilteredReferences(), inquiry);
    }
    public ArrayList<String> getListOfDescriptors(String inquiry){
        switch(inquiry){
            case "seasons": return seasons;
            case "languages": return languages;
            case "contentRatings": return contentRatings;
            case "genres": return genres;
            default: return null;
        }
    }

    private ArrayList<String> narrowedList(ArrayList<String> references, String inqiry){ 
        ArrayList<String> returnable = new ArrayList<String>();
        for (int i = 0; i < references.size(); i++) {
            String[] split = (getValue(references.get(i),"animeName")).toUpperCase().split(" ");
            if(getValue(references.get(i),"animeName").toUpperCase().startsWith(inqiry.toUpperCase())){
                returnable.add(references.get(i));
                continue; 
            }
            for (String string : split) {
                if (string.toUpperCase().startsWith(inqiry.toUpperCase())) {
                    returnable.add(references.get(i));
                    break;
                }
            }
        }
        return returnable;
    }

    public JSONObject getJSON(String pathname){ // utility method to take a pathname and return its json
        String toBeJson = "";
        try {
            final File inFile = new File(pathname);
            Scanner input = new Scanner(inFile);
            while (input.hasNext()) {
                toBeJson = toBeJson + input.nextLine();
            }
            input.close();
        } 
        catch (final Exception e) {
            System.out.println(e);
        }
        JSONObject json = null;
        try {
            final JSONParser parser = new JSONParser();
            json = (JSONObject)parser.parse(toBeJson);
        } 
        catch (final Exception e) {
            System.out.println(e);
        }
        return json;
    }
}