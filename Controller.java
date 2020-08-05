//*****************************************//
// Created by Cooper Eisnor                //
//*****************************************//

import java.util.*;
import java.awt.Color;

public class Controller {
    AnimeDao animeDao;
    FieldStorageDao fieldStorageDao;

    int state;

    public Controller(){ // default constructor
        animeDao = new AnimeDao();
        fieldStorageDao = new FieldStorageDao();
        state = 0;
    }

    public int getState(){
        return state;
    }
    public void setState(int state){
        this.state = state;
    }

    // AnimeDao related
    public String get(String reference, String key){
        return animeDao.getValue(reference, key);
    }
    public Color getAnimeColor(String reference){
        return animeDao.getColor(reference);
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
}