// this class handles all the storage and retrieval of colors and other non anime object related items

import java.util.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.awt.Color;

public class FieldStorageDao {
    Color buttons, background1, background2, background3, list, navigation, text, buttonBorder; // buttonBorder is also be used to panels
    Color defaultButtons, defaultBackground1, defaultBackground2, defaultBackground3, defaultList, defaultNavigation, defaultText, defaultButtonBorder; 

    String mainScreenImage, favorite1, favorite2, favorite3, notepadLeft, notepadRight;
    String defaultMainScreenImage, defaultFavorite1, defaultFavorite2, defaultFavorite3, defaultNotepadLeft, defaultNotepadRight;

    String prePath = "UI Values and Text";

    public FieldStorageDao() { // constructor
        setDefaults();
        pull();
    }

    // GETTERS
    public Color getButtons(){
        return buttons;
    }
    public Color getBackground1(){
        return background1;
    }
    public Color getBackground2(){
        return background2;
    }
    public Color getBackground3(){
        return background3;
    }
    public Color getList(){
        return list;
    }
    public Color getNavigation(){
        return navigation;
    }
    public Color getText(){
        return text;
    }
    public Color getButtonBorder(){
        return buttonBorder;
    }

    public String getMainScreenImage(){
        return mainScreenImage;
    }
    public String getFavorite1(){
        return favorite1;
    }
    public String getFavorite2(){
        return favorite2;
    }
    public String getFavorite3(){
        return favorite3;
    }
    public String getNotepadLeft(){
        return notepadLeft;
    }
    public String getNotepadRight(){
        return notepadRight;
    }

    // SETTERS
    public void setButtons(Color color){
        buttons = color;
        storeJSON(colorIntoJSON(color),prePath + "/" +"buttons.json");
    }
    public void setBackground1(Color color){
        background1 = color;
        storeJSON(colorIntoJSON(color),prePath + "/" +"background1.json");
    }
    public void setBackground2(Color color){
        background2 = color;
        storeJSON(colorIntoJSON(color),prePath + "/" +"background2.json");
    }
    public void setBackground3(Color color){
        background3 = color;
        storeJSON(colorIntoJSON(color),prePath + "/" +"background3.json");
    }
    public void setList(Color color){
        list = color;
        storeJSON(colorIntoJSON(color),prePath + "/" +"list.json");
    }
    public void setNavigation(Color color){
        navigation = color;
        storeJSON(colorIntoJSON(color),prePath + "/" +"navigation.json");
    }
    public void setText(Color color){
        text = color;
        storeJSON(colorIntoJSON(color),prePath + "/" +"text.json");
    }
    public void setButtonBorder(Color color){
        buttonBorder = color;
        storeJSON(colorIntoJSON(color),prePath + "/" +"buttonBorder.json");
    }

    public void setMainScreenImage(String string){
        mainScreenImage = string;
        storeJSON(stringIntoJSON(string),prePath +"/" +"mainScreenImage.json");
    }
    public void setFavorite1(String string){
        favorite1 = string;
        storeJSON(stringIntoJSON(string),prePath +"/" +"favorite1.json");
    }
    public void setFavorite2(String string){
        favorite2 = string;
        storeJSON(stringIntoJSON(string),prePath +"/" +"favorite2.json");
    }
    public void setFavorite3(String string){
        favorite3 = string;
        storeJSON(stringIntoJSON(string),prePath +"/" +"favorite3.json");
    }
    public void setNotepadLeft(String string){
        notepadLeft = string;
        storeJSON(stringIntoJSON(string),prePath +"/" +"notepadLeft.json");
    }
    public void setNotepadRight(String string){
        notepadRight = string;
        storeJSON(stringIntoJSON(string),prePath +"/" +"notepadRight.json");
    }
    
    public void setDefaults(){ // sets the default values for default options (hard coded, though these should be stored in a file somewhere. Unlike everything else these files wouldn't be editable by the program, thus harder to manage. I'll "fix" this if the time comes.)
        defaultButtons = new Color(55, 55, 55);
        defaultBackground1 = new Color(50, 50, 50);
        defaultBackground2 = new Color(65, 65, 65);
        defaultBackground3 = new Color(55, 55, 55);
        defaultList = new Color(60, 60 ,60);
        defaultNavigation = new Color(40, 40, 40, 215);
        defaultText = new Color(255, 255, 255, 255);
        defaultButtonBorder = new Color(0, 0, 255);

        defaultMainScreenImage = "Images/UI/Background.png";
        defaultFavorite1 = "";
        defaultFavorite2 = "";
        defaultFavorite3 = "";
        defaultNotepadLeft = "";
        defaultNotepadRight = "";
    }

    public void loadPreset1(){ // dark
        setButtons(new Color(55, 55, 55));
        setBackground1(new Color(50, 50, 50));
        setBackground2(new Color(65, 65, 65));
        setBackground3(new Color(55, 55, 55));
        setList(new Color(60, 60 ,60));
        setNavigation(new Color(40, 40, 40, 215));
        setText(new Color(255, 255, 255, 255));
        setButtonBorder(new Color(0, 0, 255));
    }

    public void loadPreset2(){ // light
        setButtons(new Color(255, 255, 255));
        setBackground1(new Color(210, 230, 240));
        setBackground2(new Color(213, 255, 255));
        setBackground3(new Color(255, 255, 255));
        setList(new Color(218, 240, 240));
        setNavigation(new Color(178, 220, 220, 120));
        setText(new Color(0,0,0));
        setButtonBorder(new Color(100, 150, 200));
    }
    
    public void pull(){ // checks to see if the json for each member exists, if it does load it in, otherwise load in and write to drive the default (could make faster, but less dynamic)
        String[] pathnames;
        File f = new File(prePath);
        pathnames = f.list();

        if (isIn(pathnames,"buttons.json")){
                JSONObject json = getJSON(prePath +"/" +"buttons.json");
                buttons = colorJSONtoColor(json);
        }
        else setButtons(defaultButtons);
        if (isIn(pathnames,"background1.json")){
            JSONObject json = getJSON(prePath +"/" +"background1.json");
            background1 = colorJSONtoColor(json);
        }
        else setBackground1(defaultBackground1);
        if (isIn(pathnames,"background2.json")){
            JSONObject json = getJSON(prePath +"/" +"background2.json");
            background2 = colorJSONtoColor(json);
        }
        else setBackground2(defaultBackground2);
        if (isIn(pathnames,"background3.json")){
            JSONObject json = getJSON(prePath +"/" +"background3.json");
            background3 = colorJSONtoColor(json);
        }
        else setBackground3(defaultBackground3);
        if (isIn(pathnames,"list.json")){
            JSONObject json = getJSON(prePath +"/" +"list.json");
            list = colorJSONtoColor(json);
        }
        else setList(defaultList);
        if (isIn(pathnames,"navigation.json")){
            JSONObject json = getJSON(prePath +"/" +"navigation.json");
            navigation = colorJSONtoColor(json);
        }
        else setNavigation(defaultNavigation);
        if (isIn(pathnames,"text.json")){
            JSONObject json = getJSON(prePath +"/" +"text.json");
            text = colorJSONtoColor(json);
        }
        else setText(defaultText);
        if (isIn(pathnames,"buttonBorder.json")){
            JSONObject json = getJSON(prePath +"/" +"buttonBorder.json");
            buttonBorder = colorJSONtoColor(json);
        }
        else setButtonBorder(defaultButtonBorder);

        if (isIn(pathnames,"mainScreenImage.json")){
            JSONObject json = getJSON(prePath +"/" +"mainScreenImage.json");
            mainScreenImage = (String)json.get("string");
        }
        else setMainScreenImage(defaultMainScreenImage);
        if (isIn(pathnames,"favorite1.json")){
            JSONObject json = getJSON(prePath +"/" +"favorite1.json");
            favorite1 = (String)json.get("string");
        }
        else setFavorite1(defaultFavorite1);
        if (isIn(pathnames,"favorite2.json")){
            JSONObject json = getJSON(prePath +"/" +"favorite2.json");
            favorite2 = (String)json.get("string");
        }
        else setFavorite2(defaultFavorite2);
        if (isIn(pathnames,"favorite3.json")){
            JSONObject json = getJSON(prePath +"/" +"favorite3.json");
            favorite3 = (String)json.get("string");
        }
        else setFavorite3(defaultFavorite3);
        if (isIn(pathnames,"notepadLeft.json")){
            JSONObject json = getJSON(prePath +"/" +"notepadLeft.json");
            notepadLeft = (String)json.get("string");
        }
        else setNotepadLeft(defaultNotepadLeft);
        if (isIn(pathnames,"notepadRight.json")){
            JSONObject json = getJSON(prePath +"/" +"notepadRight.json");
            notepadRight = (String)json.get("string");
        }
        else setNotepadLeft(defaultNotepadRight);
    }

    public boolean isIn(String[] strings, String value){ // utility method for pull()
        for (String string : strings) {
            if (value.equals(string)) return true;
        }
        return false;
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

    public boolean storeJSON(JSONObject json, String path){ // printing to the json file
        try { 
            final FileWriter fw = new FileWriter(path);
            final PrintWriter pw = new PrintWriter(fw);
            pw.println(json.toString());
            pw.close();
        } catch (final IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    public Color colorJSONtoColor(JSONObject json){ // takes a json that stores a color and returns a color object of it
        return new Color(((Long)json.get("r")).intValue(),
                         ((Long)json.get("g")).intValue(),
                         ((Long)json.get("b")).intValue(),
                         ((Long)json.get("a")).intValue());
    }

    @SuppressWarnings("all") // removing the warning "references to generic type HashMap<K,V> should be parameterized" that is due to the age of the json library I am using.
    public JSONObject colorIntoJSON(Color color){ // takes a color object and returns a JSONObject of it
        JSONObject json = new JSONObject();
        json.put("r",color.getRed()); 
        json.put("g",color.getGreen());
        json.put("b",color.getBlue());
        json.put("a",color.getAlpha());
        return json;
    }

    @SuppressWarnings("all") // same as above
    public JSONObject stringIntoJSON(String string){ // puts string into a json object and returns it
        JSONObject json = new JSONObject();
        json.put("string",string);
        return json;
    }
}
