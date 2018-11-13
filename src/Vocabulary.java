import java.io.*;
import java.util.*;


/**
 * Created by toan on 08/02/17.
 */

// Create a class Vocabulary

public class Vocabulary {

    // Create variables with types

    protected String nameOfCSV;
    protected Hashtable<String, Attribute> attributesList;
    protected int nbTerms;
    protected Hashtable<Integer, FuzzySet> vocIndexFS;
    protected Hashtable<Integer, Attribute> vocIndexAtt;

    /**
     * @param nameOfCsv
     */
    public Vocabulary(String nameOfCsv) {
        this.nameOfCSV = nameOfCsv;
        this.attributesList = new Hashtable<String, Attribute>();
        this.vocIndexFS = new Hashtable<Integer, FuzzySet>();
        this.vocIndexAtt = new Hashtable<Integer, Attribute>();
        this.nbTerms = 0;
        try{
            this.loadFile();
        }catch(IOException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param filter
     * @return
     */
    public FuzzySet getFSById(Integer filter) {
        return this.vocIndexFS.get(filter);
    }

    /**
     * @param filter
     * @return
     */
    public Attribute getAttributeByFSId(Integer filter) {
        return this.vocIndexAtt.get(filter);
    }

    /**
     * @return
     */
    public int getNbTerms() {
        return this.nbTerms;
    }

    /**
     * @return
     */
    public Hashtable<String, Attribute> getAttributesList() {
        return this.attributesList;
    }

    /**
     * @param n
     * @return
     */
    public Attribute getAttributeByName(String n) {
        return this.attributesList.get(n);
    }

    /**
     * @param nameOfCSV
     */
    public void setNameOfCSV(String nameOfCSV) {
        this.nameOfCSV = nameOfCSV;
    }

    /**
     * @param attributesList
     */
    public void setAttributesList(Hashtable<String, Attribute> attributesList) {
        this.attributesList = attributesList;
    }

    /**
     * @return
     */
    public String getNameOfCSV() {
        return this.nameOfCSV;
    }

    public void printVocabulary() {
        for (Enumeration<String> e = this.attributesList.keys(); e.hasMoreElements(); ) {
            Attribute a = this.attributesList.get(e.nextElement());
            a.printAttribute();
        }
    }

    /**
     * @param attName
     * @param att
     */
    public void addAttribute(String attName, Attribute att) {
        this.attributesList.put(attName, att);
    }

    // Create the method to read the Vocabulary

    /**
     * @throws IOException
     */
    public void loadFile() throws IOException {
        String rawline, type, nameOfAttribute, label, rawDescription;
        double minSupport, minCore, maxSupport, maxCore;
        int id = 0,  idA = 0;
        String[] line;
        FuzzySet s;
        Attribute curAtt = null;
        //attname,label,minSupport,minCore,maxCore,maxSupport
        //attname,label,categories

        try {
            BufferedReader csvFile = new BufferedReader(new FileReader(this.nameOfCSV));
            rawline = csvFile.readLine();
            while (rawline != null) {
                line = rawline.trim().split(",");

                if (!line[0].equals("") && !line[0].startsWith("#")) {
                    id++;
                    type = "";
                    s = null;
                    System.out.println(rawline);
                    nameOfAttribute = line[0];
                    label = line[1];

                    if (line.length == 6) {
                        type = "Numerical";
                        minSupport = Double.parseDouble(line[2]);
                        minCore = Double.parseDouble(line[3]);
                        maxCore = Double.parseDouble(line[4]);
                        maxSupport = Double.parseDouble(line[5]);
                        s = new NumericalFuzzySet(id, label, minSupport, minCore, maxCore, maxSupport);

                    } else {
                        if (line.length == 3) {
                            type = "Categorical";
                            rawDescription = line[2];
                            s = new CategoricalFuzzySet(id, label, rawDescription);
                        }
                    }

                    if ((line.length == 6 || line.length == 3)) {
                        if (!this.attributesList.containsKey(nameOfAttribute)) {
                            curAtt = new Attribute(nameOfAttribute, type, idA);
                            idA++;
                            this.addAttribute(nameOfAttribute, curAtt);
                        }
                        this.vocIndexFS.put(s.getId(), s);
                        this.vocIndexAtt.put(s.getId(), curAtt);
                        this.attributesList.get(nameOfAttribute).getPartition().addModalities(s);
                        this.nbTerms += 1;
                    }
                }
                rawline = csvFile.readLine();
            }
            csvFile.close();
        } catch (FileNotFoundException e) {
            System.err.println("File " + this.nameOfCSV + "does not exist or is not readable");
            System.err.println("Error message " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e1) {
            System.err.println("Error reading the file " + this.nameOfCSV);
            System.err.println("Error message " + e1.getMessage());
            e1.printStackTrace();
        }
    }


    /**
     * @return
     */
    public String toString() {
        String r = "";
        for (String s : this.attributesList.keySet()) {
            r += s + " " + this.attributesList.get(s).getPartition() + "\n";
        }
        return r;
    }


}
