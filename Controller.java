//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//
// outlet that the GUI uses to retrieve stored fields. The barrier between graphics and logic.

import java.util.*;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;

public class Controller {
    AnimeDao animeDao;
    FieldStorageDao fieldStorageDao;
    StatisticsAggregator statisticsAggregator;

    int state = 0;
    int order = 0; // can be 0 (up) or 1 (down)
    int sort = 0; // can be 0 through 6
    Boolean[] hidden = {false};
    boolean[] filter = new boolean[7]; // defaults are false.
    String[] filterField = {"Select One:","Select One:","Select One:","Select One:","Select One:","Select One:","Select One:"}; // 7 long
    String[] preApplyFilterField = {"Select One:","Select One:","Select One:","Select One:","Select One:","Select One:","Select One:"};

    public Controller(){ // default constructor
        animeDao = new AnimeDao(filter, filterField, hidden);
        fieldStorageDao = new FieldStorageDao();
    }

    public int getState(){
        return state;
    }
    public void setState(int state){
        this.state = state;
    }

    // AnimeDao related
    public String createAnime(){
        return animeDao.create();
    }

    public String deleteAnime(String reference){
        return animeDao.delete(reference);
    }

    public ArrayList<String> getListOfDescriptors(String inqiry){
        return animeDao.getListOfDescriptors(inqiry);
    }

    public String get(String reference, String key){
        return animeDao.getValue(reference, key);
    }

    public Color getAnimeColor(String reference){
        if (animeDao.getValue(reference, "customColor").equals("false")) return animeDao.getColor(reference);
        else return fieldStorageDao.getList();
    }

    public String getDateAsString(String reference, String key){
        Date date;
        try{
            date = new SimpleDateFormat("yyyy-MM-dd").parse(get(reference,key));
        }
        catch (Exception E) {
            date = new Date();
            System.out.println("parseing of date string failed, assuming the field is empty.");
            return "?";
        }
        
        DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");  
        return dateFormat.format(date); 
    }

    public String set(String reference, String key, String value){
        return animeDao.update(reference, key, value);
    }
    public String setAnimeColor(String reference, Color color){
        return animeDao.updateColor(reference,color);
    }

    public ArrayList<String> getReferenceList(){
        return animeDao.returnListOfReferences();
    }

    public ArrayList<String> getFilteredReferenceList(){
        return animeDao.returnListOfFilteredReferences();
    }

    public ArrayList<String> getSearchedReferenceList(String inquiry){
        return animeDao.returnListOfSearchedReferences(inquiry);
    }
    
    public ArrayList<String> getListOfStudios(){
        return animeDao.returnListOfStudios();
    }

    // FieldStorageDao related
    public Color getFieldColor(String reference){
        switch (reference){
            case "buttons": return fieldStorageDao.getButtons();
            case "background1": return fieldStorageDao.getBackground1();
            case "background2": return fieldStorageDao.getBackground2();
            case "background3": return fieldStorageDao.getBackground3();
            case "list": return fieldStorageDao.getList();
            case "navigation": return fieldStorageDao.getNavigation();
            case "text": return fieldStorageDao.getText();
            case "buttonBorder": return fieldStorageDao.getButtonBorder();
            default: return Color.BLACK;
         }
    }
    public String getFieldText(String reference){
        switch (reference){
            case "mainScreenImage": return fieldStorageDao.getMainScreenImage();
            case "favorite1": return fieldStorageDao.getFavorite1();
            case "favorite2": return fieldStorageDao.getFavorite2();
            case "favorite3": return fieldStorageDao.getFavorite3();
            case "notepadLeft": return fieldStorageDao.getNotepadLeft();
            case "notepadRight": return fieldStorageDao.getNotepadRight();
            default: return "No match for reference was exists";
         }
    }

    public String setFieldColor(String reference, Color color){
        switch (reference){
            case "buttons": fieldStorageDao.setButtons(color); break;
            case "background1": fieldStorageDao.setBackground1(color); break;
            case "background2": fieldStorageDao.setBackground2(color); break;
            case "background3": fieldStorageDao.setBackground3(color); break;
            case "list": fieldStorageDao.setList(color); break;
            case "navigation": fieldStorageDao.setNavigation(color); break;
            case "text": fieldStorageDao.setText(color); break;
            case "buttonBorder": fieldStorageDao.setButtonBorder(color); break;
            default: return "No field was set";   
         }
         return "A field was set";
    }
    public String setFieldText(String reference, String text){
        switch (reference){
            case "mainScreenImage": fieldStorageDao.setMainScreenImage(text); break;
            case "favorite1": fieldStorageDao.setFavorite1(text); break;
            case "favorite2": fieldStorageDao.setFavorite2(text); break;
            case "favorite3": fieldStorageDao.setFavorite3(text); break;
            case "notepadLeft": fieldStorageDao.setNotepadLeft(text); break;
            case "notepadRight": fieldStorageDao.setNotepadRight(text); break;
            default: return "No field was set";
         }
         return "A field was set";
    }

    public void loadLightPreset(){
        fieldStorageDao.loadPreset2();
    }
    public void loadDarkPreset(){
        fieldStorageDao.loadPreset1();
    }

    // Sort related
    public int getOrder(){
        return order;
    }
    public int getSort(){
        return sort;
    }

    public void setOrder(int order){
        this.order = order;
        animeDao.setOrder(order);
    }
    public void setSort(int sort){
        this.sort = sort;
        animeDao.setSort(sort);
    }

    // Filter related
    public String getFilterField(int index){
        return filterField[index];
    }
    public void setFilterField(int index, String string){
        filterField[index] = string;
    }

    public String getPreApplyFilterField(int index){
        return preApplyFilterField[index];
    }
    public void setPreApplyFilterField(int index, String string){
        preApplyFilterField[index] = string;
    }

    public void applyFilters(){
        for (int i = 0; i < 7; i++) {
            filterField[i] = preApplyFilterField[i];
            if (!filterField[i].equals("Select One:")) filter[i] = true;
            else filter[i] = false;
        }
    }

    public void resetFilters(){
        for (int i = 0; i < 7; i++) {
            filterField[i] = preApplyFilterField[i] = "Select One:";
            filter[i] = false;
        }
    }

    public boolean checkForFilterChange(){
        for (int i = 0; i < 7; i++){
            if (!filterField[i].equals(preApplyFilterField[i])){
                return true;
            }
        }
        return false;
    }

    public boolean getHidden(){
        return hidden[0];
    }
    public void setHidden(boolean hidden){
        this.hidden[0] = hidden; 
    }

    //*******************************************// 
    //         Anime Specific Statistics         // 
    //*******************************************//

    private int getOnlyMinutes(String reference){ 
        int averageMinutes = Integer.parseInt(get(reference, "averageEpisodeLength"));
        int episodesWatched = Integer.parseInt(get(reference, "numberOfEpisodesWatched"));

        return (averageMinutes*episodesWatched);
    }
    
    public String getDays(String reference){
        return getOnlyMinutes(reference)/1440 +"";
    }

    public String getHours(String reference){
        return (getOnlyMinutes(reference)%1440)/60 +"";
    }

    public String getMinutes(String reference){
        return (getOnlyMinutes(reference)%1440)%60 +"";
    }

    public String getPercentWhole(String reference){
        double ratio = ((double)getOnlyMinutes(reference))/((double)getTotalMinutes());
        double percent = ((double)((int)((ratio*100)*100.0)))/100.0;
        return percent +"";
    }  

    public String getSpan(String reference){
        Date start;
        Date end;
        try{
            start = new SimpleDateFormat("yyyy-MM-dd").parse(get(reference,"watchingStartDate"));
            end = new SimpleDateFormat("yyyy-MM-dd").parse(get(reference,"watchingEndDate"));
            ChronoUnit.DAYS.between(start.toInstant(),end.toInstant());
        }
        catch (Exception E) {
            System.out.println("parseing of date strings failed, assuming a field is empty.");
            return "Unknown Days";
        }
       
        if (ChronoUnit.DAYS.between(start.toInstant(),end.toInstant()) < 0) return "Timey Wimey";
        else if (ChronoUnit.DAYS.between(start.toInstant(),end.toInstant()) == 0)  return " < 1 Day";
        else if (ChronoUnit.DAYS.between(start.toInstant(),end.toInstant()) == 1) return "1 Day";
        return ChronoUnit.DAYS.between(start.toInstant(),end.toInstant()) +" Days";
    }

    private int getTotalMinutes(){ // excludes hidden but not filtered anime
        ArrayList<String> referenceList = getReferenceList();
        int sum = 0;

        for (String reference : referenceList) {
            if (animeDao.getValue(reference,"hidden").equals("false"))
                sum += getOnlyMinutes(reference);
        }
        return sum;
    }
}