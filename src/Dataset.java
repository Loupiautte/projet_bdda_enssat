
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by 
 */


public abstract class Dataset implements Iterable<Item> {

    private String file;
    private ArrayList<Item> items;
//Array to store the different attributeNames

    private ArrayList<String> attributeNames;


    public Dataset(String file) {
        this.file = file;
        this.items = new ArrayList<Item>();
        this.attributeNames = new ArrayList<String>();
    }


   /* public Dataset filter(Vocabulary v, int filter) {
        Dataset d = this.new Dataset(this.getFile());
        String attConcerned = v.getAttributeByFSId(filter).getNameOfAttribute();
        Double muv = 0.0;
        d.setAttributeNames(this.getAttributeNames());
        d.items = new ArrayList<Item>();
        int test = 0;
        for (Item i : this.items) {
            muv = v.getFSById(filter).getMu(i.getRawValue(attConcerned));
            i.setMuInAnd(muv);
            if (i.getMuIn() > 0.0) {
                d.addItem(i);
            }
        }
        return d;
    }*/

    public int cardinality() {
        return this.items.size();
    } // the length of one line.

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setAttributeNames(ArrayList<String> s) {
        attributeNames = s;
    }

    public ArrayList<String> getAttributeNames() {
        return attributeNames;
    }

    public void setAttributeNames(String line) {
        String[] partOfLine = line.split(",");
        for (int i = 0; i < partOfLine.length; i++) {
            this.attributeNames.add(partOfLine[i]);
        }
    }

    public void addItem(Item i) {
        items.add(i);
    }


    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }

   abstract public void loadFile() throws Exception;

    public Iterator<Item> iterator() {
        return items.iterator();
    }

    public String toString() {
        String r = "";
        for (Item i : this.items) {
            r += "\t-" + i + "\n";
        }
        return r;
    }

}


