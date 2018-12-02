

import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by toan on 16/02/17.
 */
public class Main {
    /**
     *
     * @param args
     * @throws FuzzyVocabularyFormatException
     * @throws IOException
     */

    public static void main(String[] args){
        /*
        Partie de réécriture des données
         */
        Vocabulary v = new Vocabulary("Data/FlightsVoc2.txt");
        FlightDatasetLoader fdl = new FlightDatasetLoader("Data/testData");
        try {
            fdl.loadFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        Partie d'exploration des données
        */

        Resume resume1 = new Resume(v);
        Selection select = new Selection();
        select.addCondition(v.getAttributesList().get("ArrDelay").getPartition().getFuzzySet("short").getId(), Operator.INF, 0.5);
        for (Item i : fdl.getItems()) {
            resume1.addItem(i, select);
        }
        System.out.println("\n Résumé : ");
        resume1.niceDisplay();
        System.out.println();

        /*
        Partie d'extraction des connaissances
         */
        Data_tree data_tree = new Data_tree(v, 0, new Selection(),-1 );
        data_tree.extractKnowledge();
        try {
            System.out.println(data_tree.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
