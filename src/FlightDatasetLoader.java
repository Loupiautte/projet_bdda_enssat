
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by 
 */


public class FlightDatasetLoader extends Dataset {



    public FlightDatasetLoader(String file) {
       super(file);
    }

    public void loadFile() throws Exception {
        int counter = 0, nbOfItems = 0;
        Item i;
        BufferedReader CSVFile;
        String row;
        String[] rowCut;
        HashMap<String, Integer> mapping = new HashMap<String, Integer>();
        RewritingVector rw;
        try {
            CSVFile = new BufferedReader(new FileReader(this.getFile()));

            row = CSVFile.readLine();

            while (row != null) {
                
                if (counter == 0) {
                     //String atts[] = "Year,Month,DayofMonth,DayOfWeek,DepTime,CRSDepTime,ArrTime,CRSArrTime,UniqueCarrier,FlightNum,TailNum,ActualElapsedTime,CRSElapsedTime,AirTime,ArrDelay,DepDelay,Origin,Dest,Distance,TaxiIn,TaxiOut,Cancelled,CancellationCode,Diverted,CarrierDelay,WeatherDelay,NASDelay,SecurityDelay,LateAircraftDelay".split(",");
                    int j = 0;
                    row= row.replace("#","");
   
                    this.setAttributeNames(row);

                    for(String att : row.split(",")){
                        mapping.put(att,j);
                        j++;
                    }
   
                } else {
                    rowCut = row.split(",");

                    if (rowCut.length == this.getAttributeNames().size()) {
                        try {
                            i = new ItemFlight();
                            i.cutRawDescription(rowCut,mapping); 
                            this.addItem(i);   
                            nbOfItems = nbOfItems + 1;
                        } catch (Exception e) {
                            System.err.println("Error message " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    
                }
                counter = counter + 1;
                row = CSVFile.readLine();
            }

            CSVFile.close();
        } catch (IOException e1) {
            System.err.println("Error reading the file " + this.getFile());
            System.err.println("Error message " + e1.getMessage());
            e1.printStackTrace();
        }
//	  System.out.println("Number of lines: " +counter+" Number of items created: " +nbOfItems);
    }
}


