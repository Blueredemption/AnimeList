// the sole purpose of this class is to provide a String that also has a counter attached to it
public class SortableCountString implements Comparable<Object>{
    String string;
    Integer counter;

    public SortableCountString(String string){
        this.string = string;
        this.counter = 1; // SortableCountStrings objects always start at 1
    }

    public String getString(){
        return string;
    }
    public int getCounter(){
        return counter;
    }

    public void setString(String string){
        this.string = string;
    }
    public void setCounter(Integer counter){
        this.counter = counter;
    }

    public void increase(){ 
        counter = counter + 1; // ++ doesn't work on Integer objects because it is immutable
    }

    // these methods are important for sorting and indexOf calls on arrayLists
    @Override
    public int compareTo(final Object o){
        return -counter.compareTo(((SortableCountString)o).getCounter());
    }

    @Override
    public boolean equals(final Object o){
        return string.equals(((SortableCountString)o).getString());
    }

    @Override
    public int hashCode() { 
        return string.hashCode();
    }
}