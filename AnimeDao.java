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

    public AnimeDao(){ // constructor
        refresh();
    }

    
    public String create(){
        AnimeObject anime = new AnimeObject();
        refresh();
        return anime.getReferenceNumber();
    }
    
    public void refresh(){ // refreshes the list of anime objects
        String[] pathnames;
        String prePath = "Anime Objects";
        File f = new File(prePath);
        list = new ArrayList<AnimeObject>();
        pathnames = f.list();
        for (String pathname : pathnames) {
            JSONObject temp = getJSON(prePath +"/" +pathname);
            AnimeObject anime = new AnimeObject(temp);
            list.add(anime);
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
                    case "watchingStartDate": anime.setWatchingStartDate(value); break;
                    case "watchingEndDate": anime.setWatchingEndDate(value); break;
                    case "averageEpisodeLength": anime.setAverageEpisodeLength(value); break;
                    case "notepadText": anime.setNotepadText(value); break;
                    case "imageLocation": anime.setImageLocation(value); break;
                    case "customColor": anime.setCustomColor(value); break;
                    case "mainGenre": anime.setMainGenre(value); break;
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
                    case "watchingStartDate": return anime.getWatchingStartDate();
                    case "watchingEndDate": return anime.getWatchingEndDate();
                    case "averageEpisodeLength": return anime.getAverageEpisodeLength();
                    case "notepadText": return anime.getNotepadText();
                    case "imageLocation": return anime.getImageLocation();
                    case "customColor": return anime.getCustomColor();
                    case "mainGenre": return anime.getMainGenre();
                    case "animationStudio": return anime.getAnimationStudio();
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
    
    public ArrayList<String> returnListOfReferences(){ 
        ArrayList<String> references = new ArrayList<String>();
        for (AnimeObject anime : list) {
            references.add(anime.getReferenceNumber());
        }
        return references;
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