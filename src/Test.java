

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
        //Resume resume = new Resume(v);
        Resume resume1 = new Resume(v);
        Selection select = new Selection();
        select.addCondition(v.getAttributesList().get("ArrDelay").getPartition().getFuzzySet("short").getId(), Operator.INF, 0.5);
        System.out.println("------------");
//        System.out.println(v.getAttributesList());
//        System.out.println(select.getValue(v.getAttributesList().get("DepDelay").getId()));


        System.out.println();
        try{
            fdl.loadFile();
            for(Item i : fdl.getItems()){
//               resume.addItem(i, select);
               resume1.addItem(i,select);
            }

        }catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        //System.out.print("Resume : ");
        //resume.niceDisplay();
        System.out.print("Resume1 : ");
        resume1.niceDisplay();
    }
}
