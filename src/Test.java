

import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by toan on 16/02/17.
 */
public class Test {
    /**
     *
     * @param args
     * @throws FuzzyVocabularyFormatException
     * @throws IOException
     */

    public static void main(String[] args){
        Vocabulary v = new Vocabulary("Data/FlightsVoc2.txt");
//        v.printVocabulary();
        FlightDatasetLoader fdl = new FlightDatasetLoader("Data/testData");
        Resume resume = new Resume(v);
        Selection select = new Selection();
//        select.addCondition();

        System.out.println();
        try{
            fdl.loadFile();
            for(Item i : fdl.getItems()){
               resume.addItem(i);
            }

        }catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.print("Resume : ");
        resume.niceDisplay();
    }
}
